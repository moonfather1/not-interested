package moonfather.not_interested;

import moonfather.not_interested.mixin.MenuAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.Heightmap;

public class ButtonMessageHandler
{
    // here we respond to a button click. this is on server-side.


    public static void handleMessage(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        ThreadExecutor<ServerTask> executor = player.getServerWorld().getServer();
        if (! executor.isOnThread())
        {
            executor.execute(() -> sendTheTraderAway(player));
        }
        else
        {
            sendTheTraderAway(player);
        }
    }



    private static void sendTheTraderAway(ServerPlayerEntity player)
    {
        if (player.currentScreenHandler instanceof MerchantScreenHandler menu)
        {
            assert player != null;
            player.closeHandledScreen();
            if (((MenuAccessor)menu).getTraderField() instanceof WanderingTraderEntity wt)
            {
                // multiplayer? let's see if there is someone in range...
                BlockPos target = new BlockPos(0, 0, 0);  boolean havePlayerTarget = false;
                for (PlayerEntity otherPlayer : wt.getWorld().getPlayers()) {
                    if (EntityPredicates.EXCEPT_SPECTATOR.test(otherPlayer) && EntityPredicates.VALID_LIVING_ENTITY.test(otherPlayer))
                    {
                        double distance = otherPlayer.distanceTo(player);
                        if (distance > 40.0D && distance < 200)
                        {
                            target = otherPlayer.getBlockPos();
                            havePlayerTarget = true;
                            break;
                        }
                    }
                }

                if (! havePlayerTarget)
                {
                    // single player (or others too far or too close) - have him fade away
                    int x = wt.getBlockPos().getX()+33*(wt.getWorld().random.nextInt(3)-1); // (-1..1) * 33
                    int z = wt.getBlockPos().getZ()+33*(wt.getWorld().random.nextInt(3)-1); // (-1..1) * 33
                    int y = wt.getWorld().getTopY(Heightmap.Type.WORLD_SURFACE, x, z);
                    target = new BlockPos(x, y, z);
                }
                wt.setDespawnDelay(10 * 20);
                wt.setWanderTarget(target);
                wt.setPositionTarget(target, 8);
            }
            //else
            //{
            //    ExampleMod.LOGGER.info("~~NOT found trader");
            //}
        }
    }
}
