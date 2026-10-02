package net.neverandy.moredyes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.network.SheepColorPacket;

public final class ClientPackets
{
    private ClientPackets() {}

    public static void sheepColor(SheepColorPacket packet)
    {
        if (Minecraft.getInstance().level == null)
        {
            return;
        }
        Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId);
        if (entity instanceof Sheep sheep)
        {
            DyedSheep.setColor(sheep, packet.color);
        }
    }
}
