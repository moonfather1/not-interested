package moonfather.not_interested;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NotInterestedClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		// register handler for server->client message
		ClientPlayNetworking.registerGlobalReceiver(ServerToClientMessaging.S2C_NI_PACKET_ID, WindowOriginMessageHandler::handleMessage);
	}
}