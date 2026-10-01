package net.neverandy.moredyes.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/** Tells a client the MoreDyes color of a sheep (-1 for a vanilla color). */
public class SheepColorPacket
{
    public final int entityId;
    public final int color;

    public SheepColorPacket(int entityId, int color)
    {
        this.entityId = entityId;
        this.color = color;
    }

    public static void encode(SheepColorPacket packet, PacketBuffer buffer)
    {
        buffer.writeVarInt(packet.entityId);
        buffer.writeVarInt(packet.color);
    }

    public static SheepColorPacket decode(PacketBuffer buffer)
    {
        return new SheepColorPacket(buffer.readVarInt(), buffer.readVarInt());
    }

    public static void handle(SheepColorPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> net.neverandy.moredyes.client.ClientPackets.sheepColor(packet)));
        context.get().setPacketHandled(true);
    }
}
