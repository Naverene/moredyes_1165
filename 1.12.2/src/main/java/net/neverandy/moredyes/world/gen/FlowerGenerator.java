package net.neverandy.moredyes.world.gen;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.neverandy.moredyes.block.BlockColored;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;

/** Scatters dyed tulips of every color over the surface of new chunks. */
public class FlowerGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random,int chunkX,int chunkZ,World world,IChunkGenerator chunkGenerator,IChunkProvider chunkProvider)
	{
		int dimension=world.provider.getDimension();
		if(!ConfigHandler.worldGenFlower||dimension==-1||dimension==1||!world.provider.hasSkyLight())
		{
			return;
		}
		for(int color=0;color<ColorStrings.ALL.length;color++)
		{
			if(random.nextInt(100)<=5)
			{
				int x=chunkX*16+8+random.nextInt(16);
				int z=chunkZ*16+8+random.nextInt(16);
				BlockPos pos=world.getTopSolidOrLiquidBlock(new BlockPos(x,0,z));
				BlockBush tulip=(BlockBush)MDBlock.tulip[ColorStrings.groupOf(color)];
				IBlockState state=tulip.getDefaultState().withProperty(BlockColored.SHADE,ColorStrings.shadeOf(color));
				if(pos.getY()<255&&world.isAirBlock(pos)&&tulip.canBlockStay(world,pos,state))
				{
					world.setBlockState(pos,state,2);
				}
			}
		}
	}
}
