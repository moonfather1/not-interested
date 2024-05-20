package moonfather.not_interested;

import moonfather.not_interested.messaging_s2c.ServerToClientMessaging;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

// client-side class that receives and handles server-to-client messages
// controls whether we draw a button
// normally these handlers have a redirection to main thread on client side but in this case, no need.
public class WindowOriginMessageHandler
{
    public static void handleMessage2(ServerToClientMessaging.S2CPayload msg, ClientPlayNetworking.Context context)
    {
        buttonVisible = msg.getValue();
    }

    private static boolean buttonVisible = false;

    public static boolean isButtonVisible()
    {
        return buttonVisible;
    }
}
