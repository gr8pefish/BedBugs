package gr8pefish.bedbugs.common;

import gr8pefish.bedbugs.common.command.CommandKickPlayer;
import gr8pefish.bedbugs.common.lib.ModInfo;
import gr8pefish.bedbugs.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION, acceptableRemoteVersions = ModInfo.ACCEPTABLE_REMOTE_VERSION)
public class BedBugs {

    public static boolean isBedBugsPresentOnDedicatedServer;

    //Proxies
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static IProxy proxy;

    //Mod Instance
    @Mod.Instance
    public static BedBugs instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //init client event handler
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }


    //===================================================Helper/other registering========================================================

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandKickPlayer());
    }

}
