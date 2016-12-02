package gr8pefish.bedbugbuster.common.network;

import gr8pefish.bedbugbuster.common.lib.ModInfo;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(ModInfo.NETWORK_CHANNEL);

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(PacketServerKickPlayer.Handler.class, PacketServerKickPlayer.class, id, Side.SERVER);
        HANDLER.registerMessage(PacketServerLeaveBed.Handler.class, PacketServerLeaveBed.class, id, Side.SERVER);
    }

}

