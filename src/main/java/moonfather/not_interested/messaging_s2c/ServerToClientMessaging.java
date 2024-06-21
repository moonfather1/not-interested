package moonfather.not_interested.messaging_s2c;

import moonfather.not_interested.NotInterested;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ServerToClientMessaging
{
    public static void sendWindowOriginMessage(ServerPlayerEntity player, boolean isWanderingTrader)
    {
        S2CPayload payload = new S2CPayload(isWanderingTrader);
        ServerPlayNetworking.send(player, payload);
    }


    private static final Identifier S2C_NI_PACKET_ID = Identifier.of(NotInterested.MODID, "packet_trader");
    public static final CustomPayload.Id<S2CPayload> PACKET_ID = new CustomPayload.Id<>(S2C_NI_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, S2CPayload> PACKET_CODEC = PacketCodec.of(S2CPayload::write, S2CPayload::new);

    //----------------
    public static class S2CPayload implements CustomPayload
    {
        // data:
        private final boolean isWanderingTrader;
        public boolean getValue() { return this.isWanderingTrader; }
        // id:
        @Override
        public Id<? extends CustomPayload> getId() { return PACKET_ID; }
        // read and write:
        private S2CPayload(PacketByteBuf buf) { this(buf.readInt() == 1); }
        private void write(PacketByteBuf buf) {
            buf.writeInt(this.isWanderingTrader ? 1 : 0);
        }
        private S2CPayload(boolean value) {
            this.isWanderingTrader = value;
        }
    };
}
