package gr8pefish.bedbugs.client.proxy;

import gr8pefish.bedbugs.client.event.ClientEventHandler;
import gr8pefish.bedbugs.common.proxy.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
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
        //register client events
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void handleKick(EntityPlayerMP player) {

        Minecraft mc = Minecraft.getMinecraft();

        boolean singleplayer = mc.isIntegratedServerRunning();
        boolean realms = mc.isConnectedToRealms();

        mc.theWorld.sendQuittingDisconnectingPacket();
        mc.loadWorld((WorldClient)null);

        if (singleplayer) {
            mc.displayGuiScreen(new GuiWorldSelection(new GuiMainMenu())); //ToDo: Keep as-is, or just go to main menu?
        } else if (realms) {
            RealmsBridge realmsbridge = new RealmsBridge();
            realmsbridge.switchToRealms(new GuiMainMenu());
        } else {
            mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        }
    }
}
