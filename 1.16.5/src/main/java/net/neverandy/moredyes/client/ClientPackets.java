package net.neverandy.moredyes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.network.SheepColorPacket;

public final class ClientPackets
{
    private ClientPackets() {}

    public static void sheepColor(SheepColorPacket packet)
    {
        if (Minecraft.getInstance().world == null)
        {
            return;
        }
        Entity entity = Minecraft.getInstance().world.getEntityByID(packet.entityId);
        if (entity instanceof SheepEntity)
        {
            DyedSheep.setColor((SheepEntity) entity, packet.color);
        }
    }
}
