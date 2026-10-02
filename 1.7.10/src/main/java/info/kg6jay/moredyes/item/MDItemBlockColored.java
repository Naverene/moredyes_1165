package info.kg6jay.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.ILayeredBlock;

public class MDItemBlockColored extends ItemBlockWithMetadata {

    public MDItemBlockColored(Block block) {
        super(block, block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
    }

    private ILayeredBlock getLayeredBlock() {
        return Block.getBlockFromItem(this) instanceof ILayeredBlock layered ? layered : null;
    }

    /** Tints flat item icons (glass panes, plants); cube items are tinted through Block.getRenderColor. */
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        ILayeredBlock layered = this.getLayeredBlock();
        if (layered != null) {
            return layered.getLayerColor(stack.getItemDamage(), pass);
        }
        return Block.getBlockFromItem(this)
            .getRenderColor(stack.getItemDamage());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return this.getLayeredBlock() != null;
    }

    @Override
    public int getRenderPasses(int meta) {
        ILayeredBlock layered = this.getLayeredBlock();
        return layered != null ? layered.getLayerCount() : 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        ILayeredBlock layered = this.getLayeredBlock();
        if (layered != null) {
            IIcon icon = layered.getLayerIcon(2, meta, pass);
            if (icon != null) {
                return icon;
            }
        }
        return this.getIconFromDamage(meta);
    }
}
