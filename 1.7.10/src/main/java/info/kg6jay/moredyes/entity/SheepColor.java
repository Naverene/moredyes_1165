package info.kg6jay.moredyes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import info.kg6jay.moredyes.block.MDBlock;

/**
 * A More Dyes shade on a vanilla sheep. Vanilla sheep only have room for the 16 vanilla colors, so the shade is kept
 * here next to them, saved with the sheep and sent to the players who can see it. While a sheep has a shade, its
 * vanilla color is set to the shade's color set (for example orange for the orange shades).
 */
public class SheepColor implements IExtendedEntityProperties {

    public static final String KEY = "MoreDyesSheep";
    /** The value of {@link #get()} when the sheep has no More Dyes shade. */
    public static final int NONE = 0;

    /** The shade packed as set * 16 + shade + 1, or {@link #NONE}. */
    private int color = NONE;
    /**
     * Whether the sheep has already been given its chance to spawn in a More Dyes shade, or was loaded from a save. A
     * sheep that is new to the world has not.
     */
    public boolean settled;

    public static void attach(EntitySheep sheep) {
        sheep.registerExtendedProperties(KEY, new SheepColor());
    }

    public static SheepColor of(Entity entity) {
        return entity instanceof EntitySheep ? (SheepColor) entity.getExtendedProperties(KEY) : null;
    }

    public static int pack(int set, int shade) {
        if (set < 0 || set >= MDBlock.colorStrings.length) {
            return NONE;
        }
        if (shade < 0 || shade >= MDBlock.colorStrings[set].length) {
            return NONE;
        }
        return set * 16 + shade + 1;
    }

    public static int setOf(int color) {
        return (color - 1) / 16;
    }

    public static int shadeOf(int color) {
        return (color - 1) % 16;
    }

    public int get() {
        return this.color;
    }

    public boolean hasShade() {
        return this.color != NONE;
    }

    /** Sets the packed shade, turning anything that is not a known shade into {@link #NONE}. */
    public void put(int color) {
        this.color = color == NONE ? NONE : pack(setOf(color), shadeOf(color));
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        if (this.hasShade()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Set", (byte) setOf(this.color));
            tag.setByte("Shade", (byte) shadeOf(this.color));
            compound.setTag(KEY, tag);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.settled = true;
        NBTTagCompound tag = compound.getCompoundTag(KEY);
        this.color = tag.hasKey("Set") ? pack(tag.getByte("Set"), tag.getByte("Shade")) : NONE;
    }

    @Override
    public void init(Entity entity, World world) {}
}
