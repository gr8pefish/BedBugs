package gr8pefish.bedbugs.common.proxy;

import gr8pefish.bedbugs.common.lib.Logger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    /**
     * Handle a player being kicked from a server, server-side logic.
     * @param player - the player; used in the kick method
     */
    @Override
    public void handleKick(EntityPlayerMP player) {
        if (player != null)
            player.connection.kickPlayerFromServer(I18n.translateToLocal("bedbugs.kickServerMessage")); //ToDo: Localize correctly
        else
            Logger.warn("Can't kick player!");
    }

}
