package gr8pefish.bedbugs.client.event;

import gr8pefish.bedbugs.client.gui.KickButton;
import gr8pefish.bedbugs.common.network.PacketHandler;
import gr8pefish.bedbugs.common.network.PacketServerKickPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onSleepGui(GuiScreenEvent.InitGuiEvent.Post event){
        if (event.getGui() instanceof GuiSleepMP) { //sleepGui
            List<GuiButton> list = event.getButtonList();
            event.getButtonList().add((new KickButton(event.getGui().width / 2 + 100, event.getGui().height - 40)));
            event.setButtonList(list);
        }
    }

    @SubscribeEvent
    public void onClick(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiSleepMP) {
            if (event.getButton() instanceof KickButton) {
                PacketHandler.HANDLER.sendToServer(new PacketServerKickPlayer());
            }
        }
    }

}
