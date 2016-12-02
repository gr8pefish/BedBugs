package gr8pefish.bedbugbuster.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerKickPlayer implements IMessage {

    public PacketServerKickPlayer() {} //default constructor is necessary

    @Override
    public void fromBytes(ByteBuf buf){

    }

    @Override
    public void toBytes(ByteBuf buf){

    }

    public static class Handler implements IMessageHandler<PacketServerKickPlayer, IMessage> {

        @Override
        public IMessage onMessage(PacketServerKickPlayer message, MessageContext ctx) {
            System.out.println("kicking");
            try {
                ctx.getServerHandler().playerEntity.connection.kickPlayerFromServer("Leaving bed (and server)");
            } catch (Exception e) {
                System.out.println("Can't kick player!"); //ToDo: Logger
            }
            System.out.println("wtf");
            return null; //no return message
        }
    }
}

