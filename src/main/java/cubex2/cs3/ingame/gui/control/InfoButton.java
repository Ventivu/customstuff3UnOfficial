package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;

public class InfoButton extends PictureBox
{
    private String text;

    public InfoButton(String text, int x, int y, Control parent)
    {
        super(Textures.CONTROLS, 209, 72, x, y, 9, 9, parent);
        this.text = text;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (isMouseOverControl(mouseX, mouseY))
        {
            GuiHelper.drawToolTip(text.split("\\|"), mouseX, mouseY, mc.fontRenderer);
        }
    }
}
