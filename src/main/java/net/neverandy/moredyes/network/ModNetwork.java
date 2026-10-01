package net.neverandy.moredyes.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.neverandy.moredyes.reference.Reference;

public final class ModNetwork
{
    private static final String VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MOD_ID, "main"),
            () -> VERSION, VERSION::equals, VERSION::equals);

    private ModNetwork() {}

    public static void register()
    {
        CHANNEL.registerMessage(0, SheepColorPacket.class, SheepColorPacket::encode, SheepColorPacket::decode, SheepColorPacket::handle);
    }
}
