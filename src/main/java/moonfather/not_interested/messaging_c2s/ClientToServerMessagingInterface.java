package moonfather.not_interested.messaging_c2s;

import moonfather.not_interested.NotInterested;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class ClientToServerMessagingInterface
{
    private static final Identifier C2S_NI_PACKET_ID = new Identifier(NotInterested.MODID, "packet_button");
    public static final CustomPayload.Id<C2SPayload> PACKET_ID = new CustomPayload.Id<>(C2S_NI_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, C2SPayload> PACKET_CODEC = PacketCodec.of(C2SPayload::write, C2SPayload::new);

    public static CustomPayload createMessage()
    {
        return new C2SPayload();
    }

    //----------------
    public static class C2SPayload implements CustomPayload
    {
        // id:
        @Override
        public Id<? extends CustomPayload> getId() { return ClientToServerMessagingInterface.PACKET_ID; }
        // read and write:
        private C2SPayload(PacketByteBuf buf) {
            this();
            buf.readInt();
        }
        private void write(PacketByteBuf buf) {
            buf.writeInt(15);
        }
        private C2SPayload() { }
    };
}
