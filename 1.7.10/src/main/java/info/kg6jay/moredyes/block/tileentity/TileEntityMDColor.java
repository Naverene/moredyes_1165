package info.kg6jay.moredyes.block.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Holds the color of a stair, slab, wall, trapdoor or piston (see ColorIndex). Those blocks need their metadata for
 * their direction or shape, so the color is kept here instead.
 */
public class TileEntityMDColor extends TileEntity {

    private static final String TAG_COLOR = "color";

    private int color;

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
        this.markDirty();
    }

    /**
     * Keep this tile entity while the block stays the same. Forge's default replaces a modded tile entity whenever the
     * metadata changes, which would lose what is stored here when the block turns or opens.
     */
    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y,
        int z) {
        return oldBlock != newBlock;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.color = tag.getShort(TAG_COLOR);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort(TAG_COLOR, (short) this.color);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setShort(TAG_COLOR, (short) this.color);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    /** The color arrives after the chunk may already have been drawn, so draw the block again. */
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        this.color = packet.func_148857_g()
            .getShort(TAG_COLOR);
        if (this.worldObj != null) {
            this.worldObj.markBlockRangeForRenderUpdate(
                this.xCoord,
                this.yCoord,
                this.zCoord,
                this.xCoord,
                this.yCoord,
                this.zCoord);
        }
    }
}
