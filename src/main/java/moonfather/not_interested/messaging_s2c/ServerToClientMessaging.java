package moonfather.not_interested.messaging_s2c;

import moonfather.not_interested.NotInterested;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class ServerToClientMessaging
{
    public static void sendWindowOriginMessage(ServerPlayer player, boolean isWanderingTrader)
    {
        S2CPayload payload = new S2CPayload(isWanderingTrader);
        ServerPlayNetworking.send(player, payload);
    }


    private static final Identifier S2C_NI_PACKET_ID = Identifier.fromNamespaceAndPath(NotInterested.MODID, "packet_trader");
    public static final CustomPacketPayload.Type<S2CPayload> PACKET_ID = new CustomPacketPayload.Type<>(S2C_NI_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CPayload> PACKET_CODEC = StreamCodec.of(S2CPayload::write, S2CPayload::new);

    //----------------
    public static class S2CPayload implements CustomPacketPayload
    {
        // data:
        private final boolean isWanderingTrader;
        public boolean getValue() { return this.isWanderingTrader; }
        // id:
        @Override
        public Type<? extends CustomPacketPayload> type() { return PACKET_ID; }
        // read and write:
        private S2CPayload(RegistryFriendlyByteBuf buf) { this(buf.readInt() == 1); }
        private S2CPayload(boolean value) {
            this.isWanderingTrader = value;
        }
        public static void write(RegistryFriendlyByteBuf buf, S2CPayload payload)  { buf.writeInt(payload.isWanderingTrader ? 1 : 0); }
    };
}
