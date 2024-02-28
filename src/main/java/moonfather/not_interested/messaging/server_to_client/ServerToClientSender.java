package moonfather.not_interested.messaging.server_to_client;

import net.minecraft.server.level.ServerPlayer;

// server->client sending. in nf, networking registration is move to main class.
public class ServerToClientSender
{
    public static void sendWindowOriginToClient(ServerPlayer serverPlayer, boolean isTrader)
    {
        WindowOriginMessage message = new WindowOriginMessage(isTrader ? 1 : 0);
        serverPlayer.connection.send(message);
    }
}
