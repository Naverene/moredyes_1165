package info.kg6jay.moredyes.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.LogHelper;

/**
 * A built-in resource pack that makes the grey textures the dyed blocks are tinted from. Each texture is generated
 * when the game loads its textures, from a source texture listed in TintSources (usually the vanilla one), so no
 * texture file is needed per color and the blocks follow whatever resource pack the player uses.
 * <p>
 * The generated textures live at moredyes:textures/blocks/tinted/&lt;key&gt;.png (and the same under items/ or
 * directly under textures/tinted/ for model textures). A resource pack can still replace any of them by providing a
 * file at that path.
 */
@SideOnly(Side.CLIENT)
public final class TintedTextures implements IResourcePack {

    public static final TintedTextures INSTANCE = new TintedTextures();

    private static final Pattern PATH = Pattern.compile("textures/(?:blocks/|items/)?tinted/(.+)\\.png");

    private TintedTextures() {}

    /** Adds this pack to the game's built-in packs. Must be called before the textures are first loaded. */
    public static void install() {
        List<IResourcePack> packs = ReflectionHelper
            .getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao");
        packs.add(INSTANCE);
    }

    /** Registers the tinted texture with this key (see TintSources) as an icon. */
    public static IIcon register(IIconRegister register, String key) {
        if (TintSources.get(key) == null) {
            LogHelper.warn("No tint source for texture " + key);
        }
        return register.registerIcon(Reference.MOD_ID + ":tinted/" + key);
    }

    /** The location of a tinted model texture (one not on the block or item atlas), such as the chest model. */
    public static ResourceLocation modelTexture(String key) {
        return new ResourceLocation(Reference.MOD_ID, "textures/tinted/" + key + ".png");
    }

    private static String keyFor(ResourceLocation location) {
        if (!Reference.MOD_ID.equals(location.getResourceDomain())) {
            return null;
        }
        Matcher matcher = PATH.matcher(location.getResourcePath());
        return matcher.matches() && TintSources.get(matcher.group(1)) != null ? matcher.group(1) : null;
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        return keyFor(location) != null;
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) throws IOException {
        String key = keyFor(location);
        if (key == null) {
            throw new FileNotFoundException(location.toString());
        }
        TintSources.Source source = TintSources.get(key);
        BufferedImage image;
        try (InputStream in = Minecraft.getMinecraft()
            .getResourceManager()
            .getResource(source.location)
            .getInputStream()) {
            image = ImageIO.read(in);
        }
        if (image == null) {
            throw new IOException("Could not read " + source.location);
        }

        // Animated textures are a vertical strip of frames; only the first frame is used.
        int size = image.getWidth();
        int height = Math.min(image.getHeight(), source.model ? image.getHeight() : size);
        BufferedImage result = new BufferedImage(size, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < size; ++x) {
                result.setRGB(x, y, source.transform.apply(image.getRGB(x, y)));
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(result, "png", out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public Set<String> getResourceDomains() {
        return Collections.singleton(Reference.MOD_ID);
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer serializer, String section) {
        return null;
    }

    @Override
    public BufferedImage getPackImage() {
        return null;
    }

    @Override
    public String getPackName() {
        return "MoreDyes tinted textures";
    }
}
