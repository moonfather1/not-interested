package moonfather.not_interested.messaging.client_to_server;

import moonfather.not_interested.mixin.MenuAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

// message handling, server-side. receives button click message on the server.
public class ButtonClickHandler
{
    private static final ButtonClickHandler INSTANCE = new ButtonClickHandler();

    public static ButtonClickHandler getInstance() { return INSTANCE; }


    public void handleClientRequest(final BuggerOffMessage msg, final PlayPayloadContext context)
    {
        context.workHandler().submitAsync(
            () -> {
                if (context.player().isPresent() && context.player().get() instanceof ServerPlayer sp) // the client that sent this packet
                {
                    sendTheTraderAway(sp);
                }
            }
        )
        .exceptionally(
            e -> {
                context.packetHandler().disconnect(Component.literal("Networking error in NI mod\n" + e.getMessage()));
                return null;
            }
        );
    }

    private void sendTheTraderAway(ServerPlayer player)
    {
        if (player.containerMenu instanceof MerchantMenu menu)
        {
            assert player != null;
            player.closeContainer();
            if (((MenuAccessor)menu).getTraderField() instanceof WanderingTrader wt)
            {
                // multiplayer? let's see if there is someone in range...
                BlockPos target = BlockPos.ZERO;  boolean havePlayerTarget = false;
                for(Player otherPlayer : wt.level().players()) {
                    if (EntitySelector.NO_SPECTATORS.test(otherPlayer) && EntitySelector.LIVING_ENTITY_STILL_ALIVE.test(otherPlayer))
                    {
                        double distance = otherPlayer.distanceToSqr(player);
                        if (distance > 40.0D && distance < 200)
                        {
                            target = otherPlayer.blockPosition();
                            havePlayerTarget = true;
                            break;
                        }
                    }
                }

                if (! havePlayerTarget)
                {
                    // single player (or others too far or too close) - have him fade away
                    int x = wt.blockPosition().getX()+33*(wt.level().random.nextInt(3)-1); // (-1..1) * 33
                    int z = wt.blockPosition().getZ()+33*(wt.level().random.nextInt(3)-1); // (-1..1) * 33
                    int y = wt.level().getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                    target = new BlockPos(x, y, z);
                }
                wt.setDespawnDelay(10 * 20);
                wt.setWanderTarget(target);
                wt.restrictTo(target, 8);
            }
            //else
            //{
            //    ExampleMod.LOGGER.info("~~NOT found trader");
            //}
        }
    }
}
