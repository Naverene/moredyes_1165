package net.neverandy.moredyes.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.neverandy.moredyes.reference.Reference;

public class ModNetwork
{
	public static final SimpleNetworkWrapper CHANNEL=NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

	public static void register()
	{
		CHANNEL.registerMessage(SheepColorMessage.Handler.class,SheepColorMessage.class,0,Side.CLIENT);
	}
}
