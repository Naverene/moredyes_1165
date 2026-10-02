package net.neverandy.moredyes.color;

/**
 * The fifteen dye groups from the original mod. Each group lists the mixed colors that are crafted starting from that
 * vanilla dye. The order of the hex codes matters: it is the order of the colors everywhere in the mod, and
 * {@code tools/generate_resources.py} reads it from this file.
 */
public enum ColorGroup {

    WHITE("ecbf99", "d9a6ec", "b3ccec", "f2f299", "bfe68c", "f9bfd2", "cccccc", "a6bfcc", "bf9fd9", "99a6d9",
        "b3a699", "b3bf99", "cc9999"),
    ORANGE("c56685", "9f8c85", "deb233", "aca526", "e57f6c", "92663f", "b98c66", "927f66", "ac5f72", "866672",
        "9f6633", "9f7f33", "b95933", "794c26"),
    MAGENTA("8c72d8", "cb9886", "998c79", "d265bf", "7f4c92", "a672b9", "7f65b9", "9946c5", "734cc5", "8c4c86",
        "8c6586", "a64086", "663379"),
    LIGHT_BLUE("a5bf86", "72b279", "ac8cbf", "597392", "7f99b9", "598cb9", "726cc5", "4d73c5", "667386", "668c86",
        "7f6686", "405979"),
    YELLOW("b2d926", "ebb26c", "99993f", "bfbf66", "99b266", "b29272", "8c9972", "a69933", "a6b233", "bf8c33",
        "7f7f26"),
    LIME("b8a65f", "668c32", "8cb359", "66a659", "7f5665", "598c65", "738c26", "73a626", "8c8026", "4c7319"),
    PINK("864c5f", "c6596c", "ac7f6c", "ac666c", "9366ab", "b95fab", "9f7f9f", "c68c9f", "9f6679"),
    GRAY("727272", "4c6572", "65467f", "404c7f", "594c40", "596540", "724040", "333333"),
    LIGHT_GRAY("738c99", "8c6ca5", "6673a5", "807366", "808c66", "996666", "595959"),
    CYAN("655fa5", "4066a5", "596666", "597f66", "725966", "334c59"),
    PURPLE("5945b2", "734573", "735f73", "8c3973", "4c2c66"),
    BLUE("4c4c73", "4c6573", "664073", "263366"),
    BROWN("403326", "7f4033", "666533"),
    GREEN("7f5933", "404c26"),
    RED("592626");

    private final String[] hexes;

    ColorGroup(String... hexes) {
        this.hexes = hexes;
    }

    String[] hexes() {
        return hexes;
    }
}
