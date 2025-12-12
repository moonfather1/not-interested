package moonfather.not_interested.mixin;

import com.mojang.authlib.GameProfile;
import moonfather.not_interested.messaging_s2c.ServerToClientMessaging;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayer.class)
public abstract class MenuOpenAndCloseEvents extends Player
{
    private MenuOpenAndCloseEvents(Level world, GameProfile gameProfile) { super(world, gameProfile); }

    // in this class, server-side, we react to guis being opened and closed and set the flag on client side whether we're dealing with a WT or not.


    @Inject(method = "doCloseContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;transferState(Lnet/minecraft/world/inventory/AbstractContainerMenu;)V", shift = At.Shift.AFTER))
    private void fireCloseContainerEvent(CallbackInfo info)
    {
        ServerToClientMessaging.sendWindowOriginMessage((ServerPlayer)(Object)this, false);  // set a flag for wandering player screen to false
    }

    @Inject(method = "openMenu", at = @At(value = "INVOKE", target = "java/util/OptionalInt.of (I)Ljava/util/OptionalInt;"))
    private void fireOpenContainerEvent(MenuProvider menuProvider, CallbackInfoReturnable<OptionalInt> cir)
    {
        if (this.containerMenu instanceof MerchantMenu mm)
        {
            if (((MenuAccessor)mm).getTraderField() instanceof WanderingTrader)
            {
                ServerToClientMessaging.sendWindowOriginMessage((ServerPlayer)(Object)this, true);  // set a flag for wandering player screen to true
                return;
            }
        }
        ServerToClientMessaging.sendWindowOriginMessage((ServerPlayer)(Object)this, false);  // set a flag for wandering player screen to false
    }

}
