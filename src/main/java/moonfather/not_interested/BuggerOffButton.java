package moonfather.not_interested;

import moonfather.not_interested.messaging.client_to_server.ClientToServerSender;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MerchantMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuggerOffButton extends Button
{
    public BuggerOffButton(int left, int top, AbstractContainerScreen<MerchantMenu> parent)
    {
        super(left, top, 88, 20, Component.translatable("message.not_interested.caption"), BuggerOffButton::handleClick, DEFAULT_NARRATION);
        //this.parent = parent;
    }

    private static void handleClick(Button button)
    {
        ClientToServerSender.sendButtonClickToServer();   // when the button is clicked, this sends an empty message to the server
    }
}
