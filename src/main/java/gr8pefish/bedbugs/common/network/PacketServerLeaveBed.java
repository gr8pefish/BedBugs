package gr8pefish.bedbugs.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerLeaveBed implements IMessage {

    public PacketServerLeaveBed() {} //default constructor is necessary

    @Override
    public void fromBytes(ByteBuf buf){

    }

    @Override
    public void toBytes(ByteBuf buf){

    }

    public static class Handler implements IMessageHandler<PacketServerLeaveBed, IMessage> {

        @Override
        public IMessage onMessage(PacketServerLeaveBed message, MessageContext ctx) {

            //ToDo: Figure out cause of stuck in bed error and add the fix here
            //player.setSleeping = false, closeGui, etc.

            return null; //no return message
        }
    }
}

