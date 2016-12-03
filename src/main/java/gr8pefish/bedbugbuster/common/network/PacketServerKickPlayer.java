package gr8pefish.bedbugbuster.common.network;

import gr8pefish.bedbugbuster.common.lib.Logger;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerKickPlayer implements IMessage {

    public PacketServerKickPlayer() {}

    @Override
    public void fromBytes(ByteBuf buf){

    }

    @Override
    public void toBytes(ByteBuf buf){

    }

    public static class Handler implements IMessageHandler<PacketServerKickPlayer, IMessage> {

        @Override
        public IMessage onMessage(PacketServerKickPlayer message, MessageContext ctx) {

            try {
                ctx.getServerHandler().playerEntity.connection.kickPlayerFromServer(I18n.translateToLocal("bedbugbuster.kickServerMessage"));
            } catch (Exception e) {
                Logger.error("Can't kick player! Please create a bug report on BedBugBuster's Github with details.");
            }

            return null; //no return message
        }
    }
}

