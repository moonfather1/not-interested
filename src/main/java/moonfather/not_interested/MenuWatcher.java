package moonfather.not_interested;

import moonfather.not_interested.messaging.server_to_client.ServerToClientSender;
import moonfather.not_interested.mixin.MenuAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.inventory.MerchantMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

@Mod.EventBusSubscriber
public class MenuWatcher
{
    /// server side event.
    ///
    /// here we differentiate between WT and villagers, and we send it to client.
    /// that way client side of our mod knows whether to add our button to a screen shared by WTs and villagers.
    @SubscribeEvent
    public static void onWindowOpen(PlayerContainerEvent.Open event)
    {
        if (event.getEntity() instanceof ServerPlayer sp)
        {
            if (event.getContainer() instanceof MerchantMenu menu)
            {
                if (((MenuAccessor)menu).getTraderField() instanceof WanderingTrader wt)
                {
                    ServerToClientSender.sendWindowOriginToClient(sp, true);
                    return;
                }
            }
            ServerToClientSender.sendWindowOriginToClient(sp, false);
        }
    }

    @SubscribeEvent
    public static void onWindowOpen(PlayerContainerEvent.Close event)
    {
        if (event.getEntity() instanceof ServerPlayer sp)
        {
            ServerToClientSender.sendWindowOriginToClient(sp, false);
        }
    }
}
