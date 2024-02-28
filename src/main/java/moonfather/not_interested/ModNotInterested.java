package moonfather.not_interested;

import moonfather.not_interested.messaging.client_to_server.BuggerOffMessage;
import moonfather.not_interested.messaging.client_to_server.ButtonClickHandler;
import com.mojang.logging.LogUtils;
import moonfather.not_interested.messaging.server_to_client.WindowOriginHandler;
import moonfather.not_interested.messaging.server_to_client.WindowOriginMessage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.slf4j.Logger;

@Mod(ModNotInterested.MODID)
public class ModNotInterested
{
    public static final String MODID = "not_interested";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModNotInterested(IEventBus modEventBus)
    {
        modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::register);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {        
    }

    private void register(final RegisterPayloadHandlerEvent event)
    {
        final IPayloadRegistrar registrar = event.registrar(ModNotInterested.MODID);
        registrar.play(BuggerOffMessage.ID, BuggerOffMessage::new, handler -> handler.server(ButtonClickHandler.getInstance()::handleClientRequest));
        registrar.play(WindowOriginMessage.ID, WindowOriginMessage::new, handler -> handler.client(WindowOriginHandler::handleMessage));
    }
}
