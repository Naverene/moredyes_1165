package net.neverandy.moredyes.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.neverandy.moredyes.MoreDyes;

/** Tells a client the mod color of a sheep it can see (-1 for none), so it draws the wool in that color. */
public class SheepColorMessage implements IMessage
{
	private int entityId;
	private int color;

	public SheepColorMessage()
	{
	}
	public SheepColorMessage(int entityId,int color)
	{
		this.entityId=entityId;
		this.color=color;
	}
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId=buf.readInt();
		this.color=buf.readInt();
	}
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeInt(this.color);
	}

	public static class Handler implements IMessageHandler<SheepColorMessage,IMessage>
	{
		@Override
		public IMessage onMessage(SheepColorMessage message,MessageContext ctx)
		{
			MoreDyes.proxy.setSheepColor(message.entityId,message.color);
			return null;
		}
	}
}
