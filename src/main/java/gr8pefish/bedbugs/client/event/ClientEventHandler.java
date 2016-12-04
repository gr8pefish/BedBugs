package gr8pefish.bedbugs.client.event;

import gr8pefish.bedbugs.client.gui.KickButton;
import gr8pefish.bedbugs.common.BedBugs;
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

    private boolean showButton;
    private long prevSystemVisibleTime;
    private long visibleTime;
    private final int visibleShow = 500; //0.5 seconds

    private long prevSystemHoverTime;
    private long hoverTime;
    private final int hoverShow = 1000; //1 second

    @SubscribeEvent
    public void onSleepGui(GuiScreenEvent.InitGuiEvent.Post event){
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            List<GuiButton> list = event.getButtonList(); //get all buttons

            kickButton = new KickButton(event.getGui().width / 2 - 30, event.getGui().height - 70); //create KickButton
            kickButton.visible = false; //set invisible
            event.getButtonList().add(kickButton); //add my own Kick Button, default invisible

            showButton = false;
            prevSystemVisibleTime = 0;
            visibleTime = 0;
            prevSystemHoverTime = 0;
            hoverTime = 0;

            event.setButtonList(list); //update button list
        }
    }

    /**
     * Makes the button visible after 1 second.
     * @param event
     */
    @SubscribeEvent
    public void onClick(GuiScreenEvent.ActionPerformedEvent.Pre event) { //ToDo: try with post
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            if (event.getButton().id == 1) { //normal 'Leave Bed' button clicked
                for (GuiButton button : event.getButtonList()) { //loop through all buttons on screen
                    if (button instanceof KickButton) { //find kick button
                        if (showButton == false) showButton = true;
                        break; //stop looping
                    }
                }
                event.setCanceled(true); //ToDo: testing purposes, remove in production!
            }

            if (event.getButton() instanceof KickButton) {
                BedBugs.proxy.handleKick(null);
            }
        }
    }

    /**
     * Draws the tooltip after a delay.
     * @param event - drawScreenEvent
     */
    @SubscribeEvent
    public void onHover(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiSleepMP) {

            //show tooltip after 1 second delay
            KickButton curr = null;
            if (kickButton != null && kickButton.visible && kickButton.isMouseInButton(event.getMouseX(), event.getMouseY())) {
                curr = kickButton;
            }
            if (curr != null){
                long systemTime = System.currentTimeMillis();
                if(prevSystemHoverTime != 0) {
                    hoverTime += systemTime - prevSystemHoverTime;
                }
                prevSystemHoverTime = systemTime;
                if(hoverTime > hoverShow) {
                    if (Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP)
                        GuiUtils.drawHoveringText(kickButton.getTooltipLines(), event.getMouseX(), event.getMouseY(), event.getGui().width, event.getGui().height, 300, Minecraft.getMinecraft().fontRendererObj);
                }
            }else{
                hoverTime = 0;
                prevSystemHoverTime = 0;
            }

            //Deal with showing the button after 0.5 second delay
            if (showButton){
                long systemTime = System.currentTimeMillis();
                if(prevSystemVisibleTime != 0) {
                    visibleTime += systemTime - prevSystemVisibleTime;
                }
                prevSystemVisibleTime = systemTime;
                if(visibleTime > visibleShow) {
                    if (Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP) {
                        kickButton.visible = true;
                    }
                }
                if (!(Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP)) {
                    showButton = false;
                    visibleTime = 0;
                    prevSystemVisibleTime = 0;
                }
            } else {
                visibleTime = 0;
                prevSystemVisibleTime = 0;
            }

        }
    }

}
