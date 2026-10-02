package net.neverandy.moredyes.block;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.tileentity.TileEntityDyedChest;
import net.neverandy.moredyes.utility.BlockInfo;

/**
 * A dyed chest. It is a vanilla chest in every way but its color, and joins a neighbor of the same color into a
 * double chest. Each color is its own block with its own chest type, because vanilla chests join by chest type.
 */
public class BlockDyedChest extends BlockChest implements IColoredBlock
{
	private final int colorIndex;
	private final String typeName;

	public BlockDyedChest(BlockInfo info,int colorIndex)
	{
		super(EnumHelper.addEnum(BlockChest.Type.class,"MOREDYES_"+ColorStrings.ALL[colorIndex].toUpperCase(),new Class<?>[0]));
		this.colorIndex=colorIndex;
		this.typeName=info.blockName;
		info.apply(this,ColorStrings.ALL[colorIndex]);
		this.setSoundType(info.sound);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta)
	{
		return new TileEntityDyedChest();
	}
	@Override
	public String getTypeName()
	{
		return this.typeName;
	}
	@Override
	public int getShadeCount()
	{
		return 1;
	}
	@Override
	public int getColorIndex(int meta)
	{
		return this.colorIndex;
	}
	@Override
	public String getColorName(int meta)
	{
		return ColorStrings.ALL[this.colorIndex];
	}
}
