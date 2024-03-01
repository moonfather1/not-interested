package moonfather.not_interested;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;

public class BuggerOffButton extends ButtonWidget
{
    public BuggerOffButton(int left, int top, HandledScreen<MerchantScreenHandler> parent)
    {
        super(left, top, 88, 20, Text.translatable("message.not_interested.caption"), BuggerOffButton::handleClick, DEFAULT_NARRATION_SUPPLIER);
        //this.parent = parent;
    }

    private static void handleClick(ButtonWidget button)
    {
        ClientToServerMessaging.sendButtonMessage();   // when the button is clicked, this sends an empty message to the server
    }
}
