package net.neverandy.moredyes.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.neverandy.moredyes.block.BlockColoredSapling;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;

/** Scatters dye trees of every color over the surface of new chunks. */
public class DyeTreeGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random,int chunkX,int chunkZ,World world,IChunkGenerator chunkGenerator,IChunkProvider chunkProvider)
	{
		int dimension=world.provider.getDimension();
		if(!ConfigHandler.worldGenTree||dimension==-1||dimension==1)
		{
			return;
		}
		for(int color=0;color<ColorStrings.ALL.length;color++)
		{
			if(random.nextInt(100)==0)
			{
				// Inside the middle of the 2x2 chunks around the corner, so the tree never reaches unloaded chunks.
				int x=chunkX*16+8+random.nextInt(16);
				int z=chunkZ*16+8+random.nextInt(16);
				BlockPos pos=world.getTopSolidOrLiquidBlock(new BlockPos(x,0,z));
				BlockColoredSapling.randomTree(random,false,color).generate(world,random,pos);
			}
		}
	}
}
