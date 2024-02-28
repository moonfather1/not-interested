package moonfather.not_interested.messaging.client_to_server;

import moonfather.not_interested.ModNotInterested;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

// client-to-server message. payload is unused. just tells us that the button was clicked.
// handler knows which server player corresponds to sender, it's all we need.
public record BuggerOffMessage(int unused) implements CustomPacketPayload
{
    public static final ResourceLocation ID = new ResourceLocation(ModNotInterested.MODID, "message_button_pressed");



    public BuggerOffMessage(final FriendlyByteBuf buffer)
    {
        this(buffer.readInt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeInt(unused);
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
