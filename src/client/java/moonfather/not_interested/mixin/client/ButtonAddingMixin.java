package moonfather.not_interested.mixin.client;

import moonfather.not_interested.BuggerOffButton;
import moonfather.not_interested.WindowOriginMessageHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class ButtonAddingMixin extends HandledScreen<MerchantScreenHandler>
{
    private ButtonAddingMixin(MerchantScreenHandler p_97741_, PlayerInventory p_97742_, Text p_97743_) { super(p_97741_, p_97742_, p_97743_); }



    @Inject(method = "drawBackground(Lnet/minecraft/client/gui/DrawContext;FII)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/DrawContext.drawTexture (Lnet/minecraft/util/Identifier;IIIFFIIII)V", shift = At.Shift.AFTER))
    private void renderExpansion(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci)
    {
        if (WindowOriginMessageHandler.isButtonVisible())
        {
            int sx = (this.width - this.backgroundWidth) / 2;
            int sy = (this.height - this.backgroundHeight) / 2;
            context.drawTexture(EXPANSION_LOCATION, sx, sy + 160, 0, 0.0F, 0.0F, 99, 30, 128, 128);
        }
    }
    private static final Identifier EXPANSION_LOCATION = new Identifier("not_interested", "textures/gui/frame1.png");



    @Inject(method = "init()V", at = @At("TAIL"))
    private void addButton(CallbackInfo ci)
    {
        if (WindowOriginMessageHandler.isButtonVisible())
        {
            int sx = (this.width - this.backgroundWidth) / 2;
            int sy = (this.height - this.backgroundHeight) / 2;
            this.addDrawableChild(new BuggerOffButton(sx + 4, sy + 160 + 3, this));
        }
    }
}
