package info.kg6jay.moredyes.block.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityPiston;

import info.kg6jay.moredyes.block.MDBlockDyedPiston;
import info.kg6jay.moredyes.block.TileColor;

/**
 * A moving dyed piston or piston head (see MDBlockDyedPiston). Vanilla's moving block only remembers the block and its
 * metadata, so this also carries the color, puts it back on the piston when it stops, and is drawn in that color by
 * TileEntityMDPistonRenderer.
 */
public class TileEntityMDPiston extends TileEntityPiston {

    private static final String TAG_COLOR = "color";

    private int color;
    /** Whether a retracting piston draws its head sliding in. Kept here because vanilla keeps its own private. */
    private boolean renderHead;
    /** How far it has moved (0 to 1), for drawing. Kept here because vanilla keeps its own private. */
    private float renderProgress, lastRenderProgress;

    public TileEntityMDPiston() {}

    public TileEntityMDPiston(Block block, int meta, int facing, boolean extending, boolean renderHead, int color) {
        super(block, meta, facing, extending, renderHead);
        this.color = color;
        this.renderHead = renderHead;
    }

    public int getColor() {
        return this.color;
    }

    public boolean shouldRenderHead() {
        return this.renderHead;
    }

    /** How far it has moved, between the last tick and this one. Advances like vanilla's progress. */
    public float getRenderProgress(float partialTicks) {
        if (partialTicks > 1.0F) {
            partialTicks = 1.0F;
        }
        return this.lastRenderProgress + (this.renderProgress - this.lastRenderProgress) * partialTicks;
    }

    @Override
    public void updateEntity() {
        this.lastRenderProgress = this.renderProgress;
        if (this.renderProgress < 1.0F) {
            this.renderProgress = Math.min(1.0F, this.renderProgress + 0.5F);
        }
        super.updateEntity();
        this.restoreColor();
    }

    @Override
    public void clearPistonTileEntity() {
        super.clearPistonTileEntity();
        this.restoreColor();
    }

    /** Once the piston has been put back in place, give its new tile entity this color. */
    private void restoreColor() {
        Block block = this.getStoredBlockID();
        if (this.worldObj != null && block instanceof MDBlockDyedPiston
            && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == block) {
            TileColor.set(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.color);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.color = tag.getShort(TAG_COLOR);
        this.lastRenderProgress = this.renderProgress = tag.getFloat("progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort(TAG_COLOR, (short) this.color);
    }
}
