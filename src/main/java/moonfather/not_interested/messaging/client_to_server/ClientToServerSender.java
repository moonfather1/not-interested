package moonfather.not_interested.messaging.client_to_server;

import net.neoforged.neoforge.network.PacketDistributor;

// client->server sending. in nf, networking registration is moved to main mod class.
public class ClientToServerSender
{
    public static void sendButtonClickToServer()
    {
        BuggerOffMessage message = new BuggerOffMessage(-1);
        PacketDistributor.SERVER.noArg().send(message);
    }
}
