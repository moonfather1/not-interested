package moonfather.not_interested.messaging.server_to_client;

import moonfather.not_interested.ModNotInterested;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/// server to client message. value carried is 1 if we clicked a WT and 0 if it's some other window.
public record WindowOriginMessage(int flag) implements CustomPacketPayload
{
    public static final ResourceLocation ID = new ResourceLocation(ModNotInterested.MODID, "message_trader_type");



    public WindowOriginMessage(final FriendlyByteBuf buffer)
    {
        this(buffer.readInt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeInt(flag);
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
