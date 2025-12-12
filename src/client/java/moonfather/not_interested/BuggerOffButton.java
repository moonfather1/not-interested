package moonfather.not_interested;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MerchantMenu;

public class BuggerOffButton extends Button.Plain
{
    public BuggerOffButton(int left, int top, AbstractContainerScreen<MerchantMenu> parent)
    {
        super(left, top, 88, 20, Component.translatable("message.not_interested.caption"), BuggerOffButton::handleClick, DEFAULT_NARRATION);
        //this.parent = parent;
    }

    private static void handleClick(Button button)
    {
        ClientToServerMessaging.sendButtonMessage();   // when the button is clicked, this sends an empty message to the server
    }

//    @Override
//    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta)
//    {
//        if (lastIsVisible != WindowOriginMessageHandler.isButtonVisible())
//        {
//            lastIsVisible = ! lastIsVisible;
//            this.visible = lastIsVisible;
//        }
//        super.renderWidget(context, mouseX, mouseY, delta);
//    }
//    private boolean lastIsVisible;
}
