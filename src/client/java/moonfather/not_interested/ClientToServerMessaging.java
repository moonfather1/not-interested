package moonfather.not_interested;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class ClientToServerMessaging
{
    // sends a message to the server that a button was just pressed.
    // no payload, handler will know which server player corresponds to sender.
    public static void sendButtonMessage()
    {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(15);
        ClientPlayNetworking.send(NotInterested.C2S_NI_PACKET_ID, packet);
    }
}
