package moonfather.not_interested.messaging.client_to_server;

import moonfather.not_interested.mixin.MenuAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// message handling, server-side. receives button click message on the server.
public class ButtonClickHandler
{
    public static void handleClientRequest(final BuggerOffMessage msg, final IPayloadContext context) //void handle(T payload, IPayloadContext context);
    {
        // as of 1.20.6, things are by default handled on main thread
        try
        {
            if (context.player() instanceof ServerPlayer sp) // the client that sent this packet
            {
                sendTheTraderAway(sp);
            }
        }
        catch (Exception e)
        {
            context.disconnect(Component.literal("Networking error in NI mod\n" + e.getMessage()));
        }
    }



    private static void sendTheTraderAway(ServerPlayer player)
    {
        assert player != null;
        if (player.containerMenu instanceof MerchantMenu menu)
        {
            if (((MenuAccessor)menu).getTraderField() instanceof WanderingTrader wt)
            {
                player.closeContainer();
                // multiplayer? let's see if there is someone in range...
                BlockPos target = BlockPos.ZERO;  boolean havePlayerTarget = false;
                for (Player otherPlayer : wt.level().players())
                {
                    if (EntitySelector.NO_SPECTATORS.test(otherPlayer) && EntitySelector.LIVING_ENTITY_STILL_ALIVE.test(otherPlayer))
                    {
                        double distance = otherPlayer.distanceToSqr(player);
                        if (distance > 40.0D && distance < 240)
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
                    int x = wt.blockPosition().getX()+33*(wt.level().getRandom().nextInt(3)-1); // (-1..1) * 33
                    int z = wt.blockPosition().getZ()+33*(wt.level().getRandom().nextInt(3)-1); // (-1..1) * 33
                    int y = wt.level().getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                    target = new BlockPos(x, y, z);
                }
                wt.setDespawnDelay(10 * 20);
                wt.setWanderTarget(target);
                wt.setHomeTo(target, 8);
            }
            //else
            //{
            //    ExampleMod.LOGGER.info("~~NOT found trader");
            //}
        }
    }
}
