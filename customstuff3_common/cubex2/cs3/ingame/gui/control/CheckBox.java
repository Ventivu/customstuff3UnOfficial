package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Textures;
import org.lwjgl.opengl.GL11;

public class CheckBox extends Control
{
    protected boolean isChecked = false;

    public CheckBox(int x, int y, Control parent)
    {
        super(x, y, 9, 9, parent);
    }

    public boolean getIsChecked()
    {
        return isChecked;
    }

    public void setIsChecked(boolean value)
    {
        isChecked = value;
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            isChecked = !isChecked;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        mc.renderEngine.bindTexture(Textures.CONTROLS);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean hover = rect.contains(mouseX, mouseY);

        int u = isChecked ? 200 : 209;
        int v = !isEnabled ? 45 : hover ? 63 : 54;
        this.drawTexturedModalRect(rect.getX(), rect.getY(), u, v, 9, 9);
    }
}
