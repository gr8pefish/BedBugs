package gr8pefish.bedbugs.client.proxy;

import gr8pefish.bedbugs.client.event.ClientEventHandler;
import gr8pefish.bedbugs.common.proxy.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.realms.RealmsBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler()); //register client events
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    /**
     * Handle a player being kicked from a server, client-side logic.
     * @param player - the player; unused (used in CommonProxy)
     */
    @Override
    public void handleKick(EntityPlayerMP player) {

        Minecraft mc = Minecraft.getMinecraft();

        boolean singleplayer = mc.isIntegratedServerRunning();
        boolean realms = mc.isConnectedToRealms();

        mc.theWorld.sendQuittingDisconnectingPacket();
        mc.loadWorld((WorldClient)null); //It's vanilla code, don't blame me :P

        if (singleplayer) {
            mc.displayGuiScreen(new GuiWorldSelection(new GuiMainMenu())); //show singleplayer world selection screen //ToDo: Keep as-is, or just go to main menu?
        } else if (realms) { //do realms stuff (untested as it will likely never be used)
            RealmsBridge realmsbridge = new RealmsBridge();
            realmsbridge.switchToRealms(new GuiMainMenu());
        } else {
            mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu())); //show multiplayer server selection screen
        }
    }
}
