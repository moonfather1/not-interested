package moonfather.not_interested;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

// client-side class that receives and handles server-to-client messages
// controls whether we draw a button
// normally these handlers have a redirection to main thread on client side but in this case, no need.
public class WindowOriginMessageHandler
{
    public static void handleMessage(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        buttonVisible = buf.readInt() == 1;
    }

    private static boolean buttonVisible = false;

    public static boolean isButtonVisible()
    {
        return buttonVisible;
    }
}
