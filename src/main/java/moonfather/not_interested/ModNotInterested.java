package moonfather.not_interested;

import moonfather.not_interested.messaging.client_to_server.BuggerOffMessage;
import moonfather.not_interested.messaging.client_to_server.ButtonClickHandler;
import moonfather.not_interested.messaging.server_to_client.WindowOriginHandler;
import moonfather.not_interested.messaging.server_to_client.WindowOriginMessage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(ModNotInterested.MODID)
public class ModNotInterested
{
    public static final String MODID = "not_interested";

    public ModNotInterested(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerPayloads);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {        
    }

    public void registerPayloads(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar("v2");
        registrar.playToServer(BuggerOffMessage.TYPE, BuggerOffMessage.STREAM_CODEC, ButtonClickHandler::handleClientRequest);
        registrar.playToClient(WindowOriginMessage.TYPE, WindowOriginMessage.STREAM_CODEC, WindowOriginHandler::handleMessage);
    }
}
