package net.neverandy.moredyes.utility;

/**
 * The tool a block's BlockInfo names. Forge 1.18 dropped its ToolType: which tool breaks a block is now the vanilla
 * mineable block tags, which ModBlockTagsProvider fills from each block's material.
 */
public enum ToolType
{
    PICKAXE, AXE, SHOVEL, HOE
}
