package net.neverandy.moredyes.block;

/**
 * The six kinds of wood that come in every color. Each one has a log, planks, leaves, a sapling and a fence, and its
 * sapling grows the same shape of tree as the vanilla sapling (see {@code tools/generate_resources.py}).
 */
public enum Wood {
    OAK("oak"),
    BIRCH("birch"),
    SPRUCE("spruce"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak");

    private final String id;

    Wood(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
