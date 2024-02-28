package moonfather.not_interested.messaging.server_to_client;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.function.Supplier;

// client-side class that receives and handles server-to-client messages
// controls whether we draw a button
// normally these handlers have a redirection to main thread on client side but in this case, no need.
public class WindowOriginHandler
{
    public static void handleMessage(final WindowOriginMessage msg, final PlayPayloadContext context)
    {
        buttonVisible = msg.flag() == 1;
    }

    private static boolean buttonVisible = false;

    public static boolean isButtonVisible()
    {
        return buttonVisible;
    }
}
