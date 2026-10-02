package info.kg6jay.moredyes.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import info.kg6jay.moredyes.entity.SheepColor;
import info.kg6jay.moredyes.reference.Reference;

public class PacketHandler {

    public static SimpleNetworkWrapper channel;

    public static void initialize() {
        channel = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        channel.registerMessage(MessageSheepColor.Handler.class, MessageSheepColor.class, 0, Side.CLIENT);
    }

    /** Sends a sheep's shade to every player who can see the sheep. */
    public static void sendSheepColor(Entity sheep, SheepColor color) {
        if (sheep.worldObj instanceof WorldServer) {
            MessageSheepColor message = new MessageSheepColor(sheep.getEntityId(), color.get());
            ((WorldServer) sheep.worldObj).getEntityTracker()
                .func_151247_a(sheep, channel.getPacketFrom(message));
        }
    }

    /** Sends a sheep's shade to one player who has just started seeing the sheep. */
    public static void sendSheepColor(Entity sheep, SheepColor color, EntityPlayerMP player) {
        channel.sendTo(new MessageSheepColor(sheep.getEntityId(), color.get()), player);
    }
}
