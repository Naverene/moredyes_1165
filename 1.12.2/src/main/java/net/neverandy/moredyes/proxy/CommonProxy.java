package net.neverandy.moredyes.proxy;

/** Work that differs between the game client and a dedicated server. */
public class CommonProxy
{
	public void preInit()
	{
	}
	public void init()
	{
	}
	/** Client only: records the color the server sent for a sheep. */
	public void setSheepColor(int entityId,int color)
	{
	}
}
