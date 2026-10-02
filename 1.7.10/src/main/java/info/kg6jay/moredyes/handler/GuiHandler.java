package info.kg6jay.moredyes.handler;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;
import info.kg6jay.moredyes.inventory.ContainerMDWorkbench;

public class GuiHandler implements IGuiHandler {

    public static final int COLORED_WORKBENCH_GUI_ID = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == COLORED_WORKBENCH_GUI_ID) {
            return new ContainerMDWorkbench(player.inventory, world, x, y, z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == COLORED_WORKBENCH_GUI_ID) {
            // The client never checks canInteractWith, so the vanilla crafting GUI can be used as is.
            return new GuiCrafting(player.inventory, world, x, y, z);
        }
        return null;
    }
}
