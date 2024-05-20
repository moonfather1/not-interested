package moonfather.not_interested;

import moonfather.not_interested.messaging_c2s.ClientToServerMessagingInterface;
import moonfather.not_interested.messaging_s2c.ServerToClientMessaging;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotInterested implements ModInitializer
{
	public static final String MODID = "not_interested";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize()
	{
		//LOGGER.info("Hello Fabric world!");

		// register server->client message
		PayloadTypeRegistry.playS2C().register(ServerToClientMessaging.PACKET_ID, ServerToClientMessaging.PACKET_CODEC);

		// register client->server message
		PayloadTypeRegistry.playC2S().register(ClientToServerMessagingInterface.PACKET_ID, ClientToServerMessagingInterface.PACKET_CODEC);
		// register handler for client->server message
		ServerPlayNetworking.registerGlobalReceiver(ClientToServerMessagingInterface.PACKET_ID, ButtonMessageHandler::handleMessage2);
	}
}