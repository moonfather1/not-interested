package moonfather.not_interested;

import moonfather.not_interested.messaging_c2s.ClientToServerMessagingInterface;
import moonfather.not_interested.messaging_s2c.ServerToClientMessaging;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;



public class NotInterestedClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		// register client->server message
		//PayloadTypeRegistry.playC2S().register(ClientToServerMessagingInterface.PACKET_ID, ClientToServerMessagingInterface.PACKET_CODEC);
		// register handler for server->client message
		ClientPlayNetworking.registerGlobalReceiver(ServerToClientMessaging.PACKET_ID, WindowOriginMessageHandler::handleMessage2);
	}
}