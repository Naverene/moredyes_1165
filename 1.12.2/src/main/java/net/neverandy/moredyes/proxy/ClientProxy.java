package net.neverandy.moredyes.proxy;

import net.neverandy.moredyes.client.ClientHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		ClientHandler.registerColors();
		ClientHandler.registerChestRenderers();
		ClientHandler.registerSheepLayer();
	}
	@Override
	public void setSheepColor(int entityId,int color)
	{
		ClientHandler.setSheepColor(entityId,color);
	}
}
