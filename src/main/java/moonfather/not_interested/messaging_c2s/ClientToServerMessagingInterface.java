package moonfather.not_interested.messaging_c2s;

import moonfather.not_interested.NotInterested;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class ClientToServerMessagingInterface
{
    private static final Identifier C2S_NI_PACKET_ID = Identifier.fromNamespaceAndPath(NotInterested.MODID, "packet_button");
    public static final CustomPacketPayload.Type<C2SPayload> PACKET_ID = new CustomPacketPayload.Type<>(C2S_NI_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SPayload> PACKET_CODEC = StreamCodec.of(C2SPayload::write, C2SPayload::new);

    public static CustomPacketPayload createMessage()
    {
        return new C2SPayload();
    }

    //----------------
    public static class C2SPayload implements CustomPacketPayload
    {
        // id:
        @Override
        public Type<? extends CustomPacketPayload> type() { return ClientToServerMessagingInterface.PACKET_ID; }
        // read and write:
        private C2SPayload(RegistryFriendlyByteBuf buf) {
            this();
            buf.readInt();
        }
        public static void write(RegistryFriendlyByteBuf buf, C2SPayload c2SPayload) { buf.writeInt(15); }

        private C2SPayload() { }
    };
}
