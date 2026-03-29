package moonfather.not_interested.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import moonfather.not_interested.messaging_s2c.ServerToClientMessaging;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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



    @WrapOperation(
            method = "openMenu",
            at = @At(value = "FIELD", target="containerMenu:Lnet/minecraft/world/inventory/AbstractContainerMenu;", opcode = Opcodes.PUTFIELD)
    )
    private void fireOpenContainerEvent2(ServerPlayer instance, AbstractContainerMenu newMenu, Operation<Void> original)
    {
        original.call(instance, newMenu);
        if (newMenu instanceof MerchantMenu mm)
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
