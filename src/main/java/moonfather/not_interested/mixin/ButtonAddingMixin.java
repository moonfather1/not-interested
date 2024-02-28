package moonfather.not_interested.mixin;

import moonfather.not_interested.BuggerOffButton;
import moonfather.not_interested.messaging.server_to_client.WindowOriginHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class ButtonAddingMixin extends AbstractContainerScreen<MerchantMenu>
{
    private ButtonAddingMixin(MerchantMenu p_97741_, Inventory p_97742_, Component p_97743_) { super(p_97741_, p_97742_, p_97743_); }



    @Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blit (Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V", shift = At.Shift.AFTER))
    private void renderExpansion(GuiGraphics graphics, float p_281275_, int p_282312_, int p_282984_, CallbackInfo ci)
    {
        if (WindowOriginHandler.isButtonVisible())
        {
            int sx = (this.width - this.imageWidth) / 2;
            int sy = (this.height - this.imageHeight) / 2;
            graphics.blit(EXPANSION_LOCATION, sx, sy + 160, 0, 0.0F, 0.0F, 99, 30, 128, 128);
        }
    }
    private static final ResourceLocation EXPANSION_LOCATION = new ResourceLocation("not_interested", "textures/gui/frame1.png");



    @Inject(method = "init()V", at = @At("TAIL"))
    private void addButton(CallbackInfo ci)
    {
        if (WindowOriginHandler.isButtonVisible())
        {
            int sx = (this.width - this.imageWidth) / 2;
            int sy = (this.height - this.imageHeight) / 2;
            this.addRenderableWidget(new BuggerOffButton(sx + 4, sy + 160 + 3, this));
        }
    }
}
