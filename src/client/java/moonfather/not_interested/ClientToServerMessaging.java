package moonfather.not_interested;

import moonfather.not_interested.messaging_c2s.ClientToServerMessagingInterface;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientToServerMessaging
{
    // sends a message to the server that a button was just pressed.
    // no payload, handler will know which server player corresponds to sender.
    public static void sendButtonMessage()
    {
        ClientPlayNetworking.send(ClientToServerMessagingInterface.createMessage());
    }
}
