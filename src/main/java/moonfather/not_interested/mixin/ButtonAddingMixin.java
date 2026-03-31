package moonfather.not_interested.mixin;

import moonfather.not_interested.BuggerOffButton;
import moonfather.not_interested.messaging.server_to_client.WindowOriginHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class ButtonAddingMixin extends AbstractContainerScreen<MerchantMenu>
{
    private ButtonAddingMixin(MerchantMenu p_97741_, Inventory p_97742_, Component p_97743_) { super(p_97741_, p_97742_, p_97743_); }



    @Inject(method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    private void renderExpansion(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float p_281275_, CallbackInfo ci)
    {
        if (WindowOriginHandler.isButtonVisible())
        {
            int sx = (this.width - this.imageWidth) / 2;
            int sy = (this.height - this.imageHeight) / 2;
            graphics.blit(RenderPipelines.GUI_TEXTURED, EXPANSION_LOCATION, sx, sy + 160, 0F, 0F, 99, 30, 128, 128);
            if (this.firstRender)
            {
                this.addRenderableWidget(new BuggerOffButton(sx + 5, sy + 160 + 3, this));
                this.firstRender = false;
            }
        }
    }
    @Unique
    private static final Identifier EXPANSION_LOCATION = Identifier.fromNamespaceAndPath("not_interested", "textures/gui/frame1b.png");
    @Unique
    private boolean firstRender = true; // instead of mixin in init() which won't work since 1.20.5, we use this flag and add button on first render.



    @Inject(method = "init()V", at = @At(value = "RETURN"))
    private void postInit(CallbackInfo ci)
    {
        this.firstRender = true;
    }
}
