package moonfather.not_interested.mixin;

import com.mojang.authlib.GameProfile;
import moonfather.not_interested.ServerToClientMessaging;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayerEntity.class)
public abstract class MenuOpenAndCloseEvents extends PlayerEntity
{
    private MenuOpenAndCloseEvents(World world, BlockPos pos, float yaw, GameProfile gameProfile) { super(world, pos, yaw, gameProfile); }

    // in this class, server-side, we react to guis being opened and closed and set the flag on client side whether we're dealing with a WT or not.


    @Inject(method = "onHandledScreenClosed", at = @At(value = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler.copySharedSlots (Lnet/minecraft/screen/ScreenHandler;)V", shift = At.Shift.AFTER))
    private void fireCloseContainerEvent(CallbackInfo info)
    {
        ServerToClientMessaging.sendWindowOriginMessage((ServerPlayerEntity)(Object)this, false);  // set a flag for wandering player screen to false
    }

    @Inject(method = "openHandledScreen", at = @At(value = "INVOKE", target = "java/util/OptionalInt.of (I)Ljava/util/OptionalInt;"))
    private void fireOpenContainerEvent(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> cir)
    {
        if (this.currentScreenHandler instanceof MerchantScreenHandler msh)
        {
            if (((MenuAccessor)msh).getTraderField() instanceof WanderingTraderEntity)
            {
                ServerToClientMessaging.sendWindowOriginMessage((ServerPlayerEntity)(Object)this, true);  // set a flag for wandering player screen to true
                return;
            }
        }
        ServerToClientMessaging.sendWindowOriginMessage((ServerPlayerEntity)(Object)this, false);  // set a flag for wandering player screen to false
    }

}
