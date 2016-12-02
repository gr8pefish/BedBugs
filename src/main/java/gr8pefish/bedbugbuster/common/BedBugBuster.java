package gr8pefish.bedbugbuster.common;

import gr8pefish.bedbugbuster.common.lib.ModInfo;
import gr8pefish.bedbugbuster.common.network.PacketHandler;
import gr8pefish.bedbugbuster.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class BedBugBuster {

    //Proxies
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static IProxy proxy;

    //Mod Instance
    @Mod.Instance
    public static BedBugBuster instance;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
        //register packets
        PacketHandler.init();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        //init client event handler
        proxy.init(event);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
