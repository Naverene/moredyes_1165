package info.kg6jay.moredyes.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.reference.Reference;

/**
 * Where each tinted texture comes from. To give a new block a texture, add a line here and call
 * TintedTextures.register(iconRegister, key) from the block's registerBlockIcons.
 * <p>
 * The generated texture keeps the source's brightness (so dark materials like obsidian stay dark) and drops its
 * color; the block's dye color is then multiplied on top when it is drawn.
 */
@SideOnly(Side.CLIENT)
public final class TintSources {

    /** Changes one ARGB pixel of the source texture. */
    public interface PixelTransform {

        int apply(int argb);
    }

    public static final class Source {

        public final ResourceLocation location;
        public final PixelTransform transform;
        /** Model textures are not square and are used whole. */
        public final boolean model;

        Source(ResourceLocation location, PixelTransform transform, boolean model) {
            this.location = location;
            this.transform = transform;
            this.model = model;
        }
    }

    /** Converts to grey using the pixel's brightness, keeping its transparency. */
    public static final PixelTransform GREY = TintSources::grey;
    /** Grey where the pixel is green, transparent elsewhere (the leaves of a sapling). */
    public static final PixelTransform GREY_IF_GREEN = argb -> isGreen(argb) ? grey(argb) : 0;
    /** Grey where the pixel is not green, transparent elsewhere (the petals of a flower). */
    public static final PixelTransform GREY_IF_NOT_GREEN = argb -> isGreen(argb) ? 0 : grey(argb);
    /** Unchanged where the pixel is green, transparent elsewhere (the stem of a flower). */
    public static final PixelTransform KEEP_IF_GREEN = argb -> isGreen(argb) ? argb : 0;
    /** Unchanged where the pixel is not green, transparent elsewhere (the trunk of a sapling). */
    public static final PixelTransform KEEP_IF_NOT_GREEN = argb -> isGreen(argb) ? 0 : argb;

    /**
     * Grey and this many times brighter, for textures that are dark because of their color rather than their material
     * (vanilla nether brick is dark red), so the dye still shows.
     */
    public static PixelTransform greyBrighter(float factor) {
        return argb -> {
            int lum = Math.min(255, Math.round((grey(argb) & 255) * factor));
            return argb & 0xFF000000 | lum << 16 | lum << 8 | lum;
        };
    }

    private static final Map<String, Source> SOURCES = new HashMap<>();

    static {
        block("wool", "wool_colored_white");
        block("woolRock", "wool_colored_white");
        block("stone", "stone");
        block("cobble", "cobblestone");
        block("stonebrick", "stonebrick");
        block("stonebrickCarved", "stonebrick_carved");
        block("stonebrickCracked", "stonebrick_cracked");
        block("obsidian", "obsidian");
        block("soulsand", "soul_sand");
        block("quartz/top", "quartz_block_top");
        block("quartz/side", "quartz_block_side");
        block("quartz/bottom", "quartz_block_bottom");
        block("clay", "hardened_clay_stained_white");
        block("hardenedClay", "hardened_clay");
        block("coal", "coal_block");
        block("glowstone", "glowstone");
        block("lapis", "lapis_block");
        block("redstone", "redstone_block");
        block("plank", "planks_oak");
        block("brick", "brick");
        block("sand", "sand");
        block("sandstone/top", "sandstone_top");
        block("sandstone/side", "sandstone_normal");
        block("sandstone/bottom", "sandstone_bottom");
        block("bookshelf", "bookshelf");
        block("workbench/top", "crafting_table_top");
        block("workbench/side", "crafting_table_side");
        block("workbench/front", "crafting_table_front");
        block("log/top", "log_oak_top");
        block("leaf", "leaves_oak");
        block("glass/clear", "glass");
        block("glass/pane", "glass_pane_top");
        add("glass/foggy", modTexture("blocks/base/glass_foggy"), GREY, false);
        add("diorite", modTexture("blocks/base/diorite"), GREY, false);

        // Blocks from newer Minecraft versions. Where 1.7 has a texture that fits, that is used; the rest have a grey
        // texture of their own in textures/blocks/base.
        block("smoothStone", "stone_slab_top");
        block("smoothSandstone", "sandstone_top");
        block("smoothQuartz", "quartz_block_bottom");
        block("cutSandstone/top", "sandstone_top");
        block("cutSandstone/side", "sandstone_smooth");
        block("cutSandstone/bottom", "sandstone_top");
        block("mossyCobble", "cobblestone_mossy");
        block("mossyStonebrick", "stonebrick_mossy");
        add("netherBrick", vanillaBlock("nether_brick"), greyBrighter(4.0F), false);
        base("granite");
        base("andesite");
        base("polishedGranite");
        base("polishedDiorite");
        base("polishedAndesite");
        base("concrete");
        base("concretePowder");
        base("soulSoil");
        base("basalt/top");
        base("basalt/side");
        add("basalt/bottom", modTexture("blocks/base/basalt/top"), GREY, false);
        base("polishedBasalt/top");
        base("polishedBasalt/side");
        add("polishedBasalt/bottom", modTexture("blocks/base/polishedBasalt/top"), GREY, false);
        base("crackedNetherBrick");
        base("chiseledNetherBrick");
        base("endStoneBrick");
        base("boneBlock/top");
        base("boneBlock/side");
        add("boneBlock/bottom", modTexture("blocks/base/boneBlock/top"), GREY, false);
        base("glazedTerracotta");
        base("netheriteBlock");
        base("cryingObsidian");
        base("ironTrapdoor");
        base("chain");
        block("piston/side", "piston_side");
        block("piston/top", "piston_top_normal");
        block("piston/topSticky", "piston_top_sticky");
        block("piston/inner", "piston_inner");
        block("piston/bottom", "piston_bottom");
        add("cornflower/petals", modTexture("blocks/base/cornflower"), GREY_IF_NOT_GREEN, false);
        add("cornflower/stem", modTexture("blocks/base/cornflower"), KEEP_IF_GREEN, false);

        add("tulip/petals", vanillaBlock("flower_tulip_white"), GREY_IF_NOT_GREEN, false);
        add("tulip/stem", vanillaBlock("flower_tulip_white"), KEEP_IF_GREEN, false);
        add("sapling/leaves", vanillaBlock("sapling_oak"), GREY_IF_GREEN, false);
        add("sapling/trunk", vanillaBlock("sapling_oak"), KEEP_IF_NOT_GREEN, false);

        add("dye", new ResourceLocation("minecraft", "textures/items/dye_powder_white.png"), GREY, false);

        add("chest/normal", new ResourceLocation("minecraft", "textures/entity/chest/normal.png"), GREY, true);
        add("chest/double", new ResourceLocation("minecraft", "textures/entity/chest/normal_double.png"), GREY, true);
    }

    private TintSources() {}

    public static Source get(String key) {
        return SOURCES.get(key);
    }

    private static void block(String key, String vanillaName) {
        add(key, vanillaBlock(vanillaName), GREY, false);
    }

    /** A texture of this mod's own, at textures/blocks/base/&lt;key&gt;.png. */
    private static void base(String key) {
        add(key, modTexture("blocks/base/" + key), GREY, false);
    }

    private static void add(String key, ResourceLocation location, PixelTransform transform, boolean model) {
        SOURCES.put(key, new Source(location, transform, model));
    }

    private static ResourceLocation vanillaBlock(String name) {
        return new ResourceLocation("minecraft", "textures/blocks/" + name + ".png");
    }

    private static ResourceLocation modTexture(String path) {
        return new ResourceLocation(Reference.MOD_ID, "textures/" + path + ".png");
    }

    private static int grey(int argb) {
        int r = argb >> 16 & 255, g = argb >> 8 & 255, b = argb & 255;
        int lum = Math.min(255, Math.round(0.299F * r + 0.587F * g + 0.114F * b));
        return argb & 0xFF000000 | lum << 16 | lum << 8 | lum;
    }

    private static boolean isGreen(int argb) {
        if ((argb >>> 24) == 0) {
            return false;
        }
        float[] hsb = java.awt.Color.RGBtoHSB(argb >> 16 & 255, argb >> 8 & 255, argb & 255, null);
        float hue = hsb[0] * 360.0F;
        return hsb[1] > 0.15F && hue >= 65.0F && hue <= 170.0F;
    }
}
