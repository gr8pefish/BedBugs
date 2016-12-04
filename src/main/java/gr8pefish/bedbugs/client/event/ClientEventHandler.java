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

    private KickButton kickButton; //the button

    private boolean showButton; //to show the button (set to true after clicking the leave bed button)
    private long prevSystemShowButtonTime; //to count the time elapsed to make the button visible or not
    private long showButtonTime;
    private final int visibleShowLimit = 250; //0.25 seconds

    private long prevSystemTooltipHoverTime; //to count the time elapsed to show the tooltip
    private long tooltipHoverTime;
    private final int hoverShowLimit = 1000; //1 second

    /**
     * Initializes the gui by adding a kick button invisibly.
     *
     * @param event - init gui event
     */
    @SubscribeEvent
    public void onSleepGui(GuiScreenEvent.InitGuiEvent.Post event){
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            List<GuiButton> list = event.getButtonList(); //get all buttons

            kickButton = new KickButton(event.getGui().width / 2 - 30, event.getGui().height - 70); //create KickButton
            kickButton.visible = false; //set invisible
            event.getButtonList().add(kickButton); //add my own Kick Button, default invisible

            //reset all counters
            showButton = false;
            prevSystemShowButtonTime = 0;
            showButtonTime = 0;
            prevSystemTooltipHoverTime = 0;
            tooltipHoverTime = 0;

            event.setButtonList(list); //update button list
        }
    }

    /**
     * Makes the button possible to be visible, or handles KickButton clicks.
     *
     * @param event - button click event
     */
    @SubscribeEvent
    public void onClick(GuiScreenEvent.ActionPerformedEvent.Pre event) { //ToDo: switch to post for production
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            if (event.getButton().id == 1) { //normal 'Leave Bed' button clicked
                if (showButton == false) showButton = true; //boolean to start timer to show the kick button
                event.setCanceled(true); //ToDo: For testing purposes, remove in production!
            }

            //if kick button clicked then handle that
            if (event.getButton() instanceof KickButton) {
                BedBugs.proxy.handleKick(null);
            }
        }
    }

    /**
     * Makes the button visible after a delay.
     * Draws the tooltip after a delay.
     *
     * @param event - drawScreenEvent
     */
    @SubscribeEvent
    public void onHover(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiSleepMP) { //sleep GUI

            //Deal with showing the button after a delay
            handleCounter(showButton, visibleShowLimit, CounterType.SHOW_BUTTON, event);

            //Deal with showing the tooltip after a delay
            boolean hovering = (kickButton != null && kickButton.visible && kickButton.isMouseInButton(event.getMouseX(), event.getMouseY()));
            handleCounter(hovering, hoverShowLimit, CounterType.SHOW_TOOLTIP, event);

        }
    }

    //==================================================Helper Methods====================================================

    /**
     * Handle the delay in making the button/tooltip appear.
     * Doesn't do much work itself but delegates to more helper methods. Probably abstraction overkill.
     *
     * @param goOn - if you should continue processing
     * @param timeLimit - the minimum time to process
     * @param counterType - the enum type of counter method
     * @param event - the draw screen event
     */
    private void handleCounter(boolean goOn, int timeLimit, CounterType counterType, GuiScreenEvent.DrawScreenEvent.Post event){
        if (goOn){ //if should continue on
            long time = handleCounterSpecificsSystemTime(counterType, System.currentTimeMillis()); //update class variables and get the current time
            if (time > timeLimit) { //if time is greater than time to actions
                if (Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP) { //if in the right gui
                    handleCounterSpecificsDelegateMethod(counterType, event); //call the gui method
                }
            }
            if (!(Minecraft.getMinecraft().currentScreen instanceof GuiSleepMP)) { //if not in the right gui
                handleCounterSpecificsNotGuiSleep(counterType); //call the non-gui method
            }
        } else { //if shouldn't continue on
            handleCounterSpecificsStop(counterType); //reset variables
        }
    }

    /**
     * Updates class level timing variables and returns the current time.
     *
     * @param counterType - the enum type of counter method
     * @param systemTime - the system time ot update with
     * @return long representing the current time
     */
    private long handleCounterSpecificsSystemTime(CounterType counterType, long systemTime){
        switch (counterType) {

            case SHOW_BUTTON: //for making the kick button visible
                if (prevSystemShowButtonTime != 0) //if counting
                    showButtonTime += systemTime - prevSystemShowButtonTime; //update new count
                prevSystemShowButtonTime = systemTime; //set previous to new for next iteration/tick
                return showButtonTime; //return updated time

            case SHOW_TOOLTIP: //for making the tooltip visible
                if (prevSystemTooltipHoverTime != 0) //if counting
                    tooltipHoverTime += systemTime - prevSystemTooltipHoverTime; //update new count
                prevSystemTooltipHoverTime = systemTime; //set previous to new for next iteration/tick
                return tooltipHoverTime; //return update time

        }

        return 0; //default if some error occurs. Should never be reached.
    }

    /**
     * Handle the key functionality (setting the kick button visible or displaying the tooltip).
     *
     * @param counterType - the enum type of counter method
     * @param event - the draw screen event
     */
    private void handleCounterSpecificsDelegateMethod(CounterType counterType, GuiScreenEvent.DrawScreenEvent.Post event) {
        switch (counterType) {

            case SHOW_BUTTON: //for making the kick button visible
                kickButton.visible = true; //set the kick button to be visible
                break;

            case SHOW_TOOLTIP: //for making the tooltip visible
                //draw the tooltip
                GuiUtils.drawHoveringText(kickButton.getTooltipLines(), event.getMouseX(), event.getMouseY(), event.getGui().width, event.getGui().height, 300, Minecraft.getMinecraft().fontRendererObj);
                break;

        }
    }


    /**
     * Handle what happens when the Sleep GUI is not the current GUI.
     * Used for resetting the kickButton's boolean/goOn variable
     *
     * @param counterType - the enum type of counter method
     */
    private void handleCounterSpecificsNotGuiSleep(CounterType counterType) {
        switch (counterType) {

            case SHOW_BUTTON: //for making the kick button visible
                showButton = false; //resets if the button should be shown
                showButtonTime = 0; //reset the timers for good measure
                prevSystemShowButtonTime = 0; //reset the timers for good measure
                break;

            case SHOW_TOOLTIP: //for making the tooltip visible
                //nothing needed here
                break;

        }

    }

    /**
     * Handle the resetting of class level timer variables.
     *
     * @param counterType - the enum type of counter method
     */
    private void handleCounterSpecificsStop(CounterType counterType){
        switch (counterType) {

            case SHOW_BUTTON: //for making the kick button visible
                showButtonTime = 0; //reset the timers
                prevSystemShowButtonTime = 0; //reset the timers
                break;

            case SHOW_TOOLTIP: //for making the tooltip visible
                tooltipHoverTime = 0; //reset the timers
                prevSystemTooltipHoverTime = 0; //reset the timers
                break;

        }
    }

    /**
     * A tiny enum to distinguish between the logic/timers for the kick button and the tooltip.
     */
    private enum CounterType {
        SHOW_BUTTON, //for making the kick button visible
        SHOW_TOOLTIP //for making the tooltip visible
    }

}
