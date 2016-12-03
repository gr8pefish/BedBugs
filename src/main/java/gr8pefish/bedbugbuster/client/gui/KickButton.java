package gr8pefish.bedbugbuster.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KickButton extends GuiButton {

    //ToDo: Add tooltip?

    public KickButton(int x, int y){
        super(2, x, y, 40, 20, I18n.format("bedbugbuster.kickButtonText")); //mojang code
    }
}
