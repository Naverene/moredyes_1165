package info.kg6jay.moredyes.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import info.kg6jay.moredyes.MoreDyes;
import io.netty.buffer.ByteBuf;

/** Tells a player's game which More Dyes shade a sheep has, so it can draw the fleece in it. */
public class MessageSheepColor implements IMessage {

    private int entityId;
    private int color;

    public MessageSheepColor() {}

    public MessageSheepColor(int entityId, int color) {
        this.entityId = entityId;
        this.color = color;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.color = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeShort(this.color);
    }

    public static class Handler implements IMessageHandler<MessageSheepColor, IMessage> {

        @Override
        public IMessage onMessage(MessageSheepColor message, MessageContext ctx) {
            MoreDyes.proxy.setSheepColor(message.entityId, message.color);
            return null;
        }
    }
}
