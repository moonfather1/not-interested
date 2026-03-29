package moonfather.not_interested.mixin.client;

import moonfather.not_interested.BuggerOffButton;
import moonfather.not_interested.WindowOriginMessageHandler;
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
    private ButtonAddingMixin(MerchantMenu p_97741_, Inventory inventory, Component component) { super(p_97741_, inventory, component); }

    //@Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    @Inject(method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    private void renderExpansion(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        if (WindowOriginMessageHandler.isButtonVisible())
        {
            int sx = (this.width - this.imageWidth) / 2;
            int sy = (this.height - this.imageHeight) / 2;
            graphics.blit(RenderPipelines.GUI_TEXTURED, EXPANSION_LOCATION, sx, sy + 160, 0.0F, 0.0F, 99, 30, 128, 128);
            if (this.firstRender)
            {
                this.addRenderableWidget(new BuggerOffButton(sx + 5, sy + 160 + 3, this));
                this.firstRender = false;
            }
        }
    }

    @Unique
    private static final Identifier EXPANSION_LOCATION = Identifier.fromNamespaceAndPath("not_interested", "textures/gui/frame1b.png");
    // as of 1.20.5, server2client message comes AFTER init() and we can't check WindowOriginMessageHandler.isButtonVisible() here.

    @Unique
    private boolean firstRender = true; // instead of mixin in init() which won't work since 1.20.5, we use this flag.



    @Inject(method = "init()V", at = @At("TAIL"))
    private void addButton(CallbackInfo ci)
    {
        // as of 1.20.5, server2client message comes AFTER init() and we can't check WindowOriginMessageHandler.isButtonVisible() here.
        firstRender = true; // this takes care of resizing while trade window is open. not a big deal
    }
}
