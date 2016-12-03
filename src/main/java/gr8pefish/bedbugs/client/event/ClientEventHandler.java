package gr8pefish.bedbugs.client.event;

import gr8pefish.bedbugs.client.gui.KickButton;
import gr8pefish.bedbugs.common.network.PacketHandler;
import gr8pefish.bedbugs.common.network.PacketServerKickPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    private KickButton kickButton;

    @SubscribeEvent
    public void onSleepGui(GuiScreenEvent.InitGuiEvent.Post event){
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            List<GuiButton> list = event.getButtonList(); //get all buttons

            kickButton = new KickButton(event.getGui().width / 2 - 30, event.getGui().height - 70); //create KickButton
            kickButton.visible = false; //set invisible
            event.getButtonList().add(kickButton); //add my own Kick Button, default invisible

            event.setButtonList(list); //update button list
        }
    }

    @SubscribeEvent
    public void onClick(GuiScreenEvent.ActionPerformedEvent.Pre event) { //ToDo: try with post
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI
            if (event.getButton().id == 1) { //normal 'Leave Bed' button clicked
                for (GuiButton button : event.getButtonList()) { //loop through all buttons on screen
                    if (button instanceof KickButton) { //find kick button
                        button.visible = true; //set it to visible
                    }
                }
                event.setCanceled(true); //ToDo: testing purposes, remove in production!
            }
            if (event.getButton() instanceof KickButton) {
                PacketHandler.HANDLER.sendToServer(new PacketServerKickPlayer());
            }
        }
    }

    @SubscribeEvent
    public void onHover(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiSleepMP) {
            if (kickButton != null && kickButton.visible && kickButton.isMouseInButton(event.getMouseX(), event.getMouseY())) {
                GuiUtils.drawHoveringText(kickButton.getTooltipLines(), event.getMouseX(), event.getMouseY(), event.getGui().width, event.getGui().height, 300, Minecraft.getMinecraft().fontRendererObj);
            }
        }
    }

}
