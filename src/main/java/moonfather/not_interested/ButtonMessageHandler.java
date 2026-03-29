package moonfather.not_interested;

import moonfather.not_interested.mixin.MenuAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.levelgen.Heightmap;

public class ButtonMessageHandler
{
    // here we respond to a button click. this is on server-side.


    public static void handleMessage2(CustomPacketPayload msg, ServerPlayNetworking.Context context)
    {
        ServerPlayer player = context.player();
        BlockableEventLoop<TickTask> executor = context.server();
        if (! executor.isSameThread())
        {
            executor.execute(() -> sendTheTraderAway(player));
        }
        else
        {
            sendTheTraderAway(player);
        }
    }



    private static void sendTheTraderAway(ServerPlayer player)
    {
        if (player.containerMenu instanceof MerchantMenu menu)
        {
            assert player != null;
            player.closeContainer();
            if (((MenuAccessor)menu).getTraderField() instanceof WanderingTrader wt)
            {
                // multiplayer? let's see if there is someone in range...
                BlockPos target = new BlockPos(0, 0, 0);  boolean havePlayerTarget = false;
                for (Player otherPlayer : wt.level().players()) {
                    if (! otherPlayer.isSpectator() && otherPlayer.isAlive() && ! otherPlayer.isRemoved())
                    {
                        double distance = otherPlayer.distanceTo(player);
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
