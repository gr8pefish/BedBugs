package gr8pefish.bedbugs.client.gui;

import gr8pefish.bedbugs.common.BedBugs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class KickButton extends GuiButton {
    
    private ArrayList<String> tooltipLines = new ArrayList<>();

    public KickButton(int x, int y){
        super(2, x, y, 60, 20, I18n.format("bedbugs.kickButtonText"));
        this.tooltipLines.clear();

        boolean useNormalTooltips;

        if (Minecraft.getMinecraft().isIntegratedServerRunning()) { //singleplayer
            useNormalTooltips = true;
        } else { //multiplayer
            useNormalTooltips = BedBugs.isBedBugsPresentOnDedicatedServer; //need to check if remote server has it installed or not
        }

        if (useNormalTooltips) {
            this.tooltipLines.add(I18n.format("bedbugs.kickButtonTooltipLine1"));
            this.tooltipLines.add(I18n.format("bedbugs.kickButtonTooltipLine2"));
        } else {
            this.tooltipLines.add(I18n.format("bedbugs.kickButtonTooltipAddServerLine1"));
            this.tooltipLines.add(I18n.format("bedbugs.kickButtonTooltipAddServerLine2"));
        }
    }

    public boolean isMouseInButton(int mousePosX, int mousePosY){
        return (mousePosX >= this.xPosition && mousePosY >= this.yPosition && mousePosX < this.xPosition + this.width && mousePosY < this.yPosition + this.height);
    }

    public ArrayList<String> getTooltipLines() {
        return tooltipLines;
    }


}
