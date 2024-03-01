package moonfather.not_interested;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ServerToClientMessaging
{
    public static void sendWindowOriginMessage(ServerPlayerEntity player, boolean isWanderingTrader)
    {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(isWanderingTrader ? 1 : 0);
        ServerPlayNetworking.send(player, S2C_NI_PACKET_ID, packet);
    }



    public static final Identifier S2C_NI_PACKET_ID = new Identifier(NotInterested.MODID, "packet_trader");
}
