package moonfather.not_interested.messaging.server_to_client;

import moonfather.not_interested.ModNotInterested;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/// server to client message. value carried is 1 if we clicked a WT and 0 if it's some other window.
public record WindowOriginMessage(int flag) implements CustomPacketPayload
{
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ModNotInterested.MODID, "message_trader_type");
    public static final Type<WindowOriginMessage> TYPE = new Type<>(ID);



    @Override
    public @NotNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, WindowOriginMessage> STREAM_CODEC = StreamCodec.of(
            WindowOriginMessage::encode,
            WindowOriginMessage::decode);

    private static void encode(RegistryFriendlyByteBuf buf, WindowOriginMessage msg) { buf.writeInt(msg.flag); }
    private static @NotNull WindowOriginMessage decode(RegistryFriendlyByteBuf buf) { return new WindowOriginMessage(buf.readInt()); }
}
