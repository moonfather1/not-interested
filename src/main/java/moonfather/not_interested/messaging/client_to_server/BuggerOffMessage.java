package moonfather.not_interested.messaging.client_to_server;

import moonfather.not_interested.ModNotInterested;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

// client-to-server message. payload is unused. just tells us that the button was clicked.
// handler knows which server player corresponds to sender, it's all we need.
public record BuggerOffMessage(int unused) implements CustomPacketPayload
{
    private static final Identifier ID = Identifier.fromNamespaceAndPath(ModNotInterested.MODID, "message_button_pressed");
    public static final Type<BuggerOffMessage> TYPE = new Type<>(ID);



    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, BuggerOffMessage> STREAM_CODEC = StreamCodec.of(
            BuggerOffMessage::encode,
            BuggerOffMessage::decode);

    private static void encode(RegistryFriendlyByteBuf buf, BuggerOffMessage msg) { buf.writeInt(msg.unused); }
    private static @NotNull BuggerOffMessage decode(RegistryFriendlyByteBuf buf) { return new BuggerOffMessage(buf.readInt()); }
}
