package net.neverandy.moredyes.utility;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.reference.Reference;

/** What every color of one kind of block has in common. The values follow the vanilla block. */
public class BlockInfo
{
	/** Name of the kind of block ("wool"); also the name of its blockstate file and model. */
	public final String blockName;
	public final Material blockMaterial;
	public final float hardness;
	public final SoundType sound;
	/** Tool class that mines this block fastest, or null for none (like vanilla leaves, wool and glass). */
	public String harvestTool;
	public int harvestLevel;
	/** Blast resistance, or a negative number to derive it from the hardness like vanilla does. */
	public float resistance=-1.0f;
	public CreativeTabs tab=MoreDyes.tabBlocks;

	public BlockInfo(String blockName,Material mat,float hardness,SoundType sound)
	{
		this.blockName=blockName;
		this.blockMaterial=mat;
		this.hardness=hardness;
		this.sound=sound;
	}
	public BlockInfo tool(String tool,int level)
	{
		this.harvestTool=tool;
		this.harvestLevel=level;
		return this;
	}
	public BlockInfo resistance(float resistance)
	{
		this.resistance=resistance;
		return this;
	}
	public BlockInfo tab(CreativeTabs tab)
	{
		this.tab=tab;
		return this;
	}
	/**
	 * Applies these settings to a block and names it. The suffix tells the colors of this kind of block apart: the
	 * name of a color group, or a hex code for blocks that hold a single color. The sound is set by the block itself.
	 */
	public void apply(Block block,String suffix)
	{
		block.setHardness(this.hardness);
		if(this.resistance>=0.0f)
		{
			block.setResistance(this.resistance);
		}
		if(this.harvestTool!=null)
		{
			block.setHarvestLevel(this.harvestTool,this.harvestLevel);
		}
		block.setCreativeTab(this.tab);
		block.setRegistryName(Reference.MOD_ID,this.blockName+"_"+suffix);
		block.setUnlocalizedName(Reference.MOD_ID+"."+this.blockName);
	}
}
