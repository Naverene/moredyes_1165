package net.neverandy.moredyes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.handler.CauldronWashHandler;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.handler.DyedSheepHandler;
import net.neverandy.moredyes.handler.FuelHandler;
import net.neverandy.moredyes.handler.GuiHandler;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.proxy.CommonProxy;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.utility.LogHelper;
import net.neverandy.moredyes.world.gen.DyeTreeGenerator;
import net.neverandy.moredyes.world.gen.FlowerGenerator;

@Mod(modid=Reference.MOD_ID,name=Reference.MOD_NAME,version=Reference.MOD_VERSION,dependencies="after:thermalfoundation")
public class MoreDyes
{
	@Mod.Instance(Reference.MOD_ID)
	public static MoreDyes instance;

	@SidedProxy(clientSide=Reference.CLIENT_PROXY,serverSide=Reference.SERVER_PROXY)
	public static CommonProxy proxy;

	//Creative Tabs
	public static CreativeTabs tabDyes=new Tab("Dyes");
	public static CreativeTabs tabBlocks=new Tab("Blocks");
	public static CreativeTabs tabPlants=new Tab("Plants");
	public static CreativeTabs tabTrees=new Tab("Trees");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		// The blocks and items are made here, and registered when Forge fires its registry events.
		MDItem.initialize();
		MDBlock.initialize();
		NetworkRegistry.INSTANCE.registerGuiHandler(this,new GuiHandler());
		ModNetwork.register();
		GameRegistry.registerWorldGenerator(new FlowerGenerator(),1);
		GameRegistry.registerWorldGenerator(new DyeTreeGenerator(),1);
		proxy.preInit();
		LogHelper.info("Pre Initialization Complete");
	}
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		MDBlock.registerFlammability();
		MinecraftForge.EVENT_BUS.register(new CauldronWashHandler());
		MinecraftForge.EVENT_BUS.register(new FuelHandler());
		MinecraftForge.EVENT_BUS.register(new DyedSheepHandler());
		proxy.init();
		((Tab)tabDyes).setTabIcon(new ItemStack(MDItem.dye[0]));
		((Tab)tabBlocks).setTabIcon(new ItemStack(MDBlock.wool[0]));
		((Tab)tabPlants).setTabIcon(new ItemStack(MDBlock.tulip[0]));
		((Tab)tabTrees).setTabIcon(new ItemStack(MDBlock.sapling[0]));
		LogHelper.info("Initialization Complete");
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LogHelper.info("Post Initialization Complete");
	}
}
