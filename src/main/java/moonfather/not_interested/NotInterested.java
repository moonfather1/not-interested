package moonfather.not_interested;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
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

		// register handler for client->server message
		ServerPlayNetworking.registerGlobalReceiver(C2S_NI_PACKET_ID, ButtonMessageHandler::handleMessage);
	}
	public static final Identifier C2S_NI_PACKET_ID = new Identifier(NotInterested.MODID, "packet_button");
}