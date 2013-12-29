package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Textures;
import org.lwjgl.opengl.GL11;

public class Button extends Control
{
    protected boolean hover;
    protected String text = "";

    /**
     * Uses 60 for width and 16 for height.
     */
    public Button(String text, int x, int y, Control parent)
    {
        this(text, x, y, 60, 16, parent);
    }

    /**
     * Uses 16 for width.
     */
    public Button(String text, int x, int y, int width, Control parent)
    {
        this(text, x, y, width, 16, parent);
    }

    public Button(String text, int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
        this.text = text;
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        }
    }

    protected int getHoverState(boolean hover)
    {
        byte b0 = 1;

        if (!isEnabled)
        {
            b0 = 0;
        }
        else if (hover)
        {
            b0 = 2;
        }

        return b0;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        mc.renderEngine.bindTexture(Textures.CONTROLS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        hover = isMouseOverControl(mouseX, mouseY);

        int k = getHoverState(hover);
        // Top Left
        drawTexturedModalRect(rect.getX(), rect.getY(), 0, k * 70, rect.getWidth() / 2, rect.getHeight() / 2);
        // Top Right
        drawTexturedModalRect(rect.getX() + rect.getWidth() / 2, rect.getY(), 200 - rect.getWidth() / 2, k * 70, rect.getWidth() / 2, rect.getHeight() / 2);
        // Bottom Left
        drawTexturedModalRect(rect.getX(), rect.getY() + rect.getHeight() / 2, 0, 70 - rect.getHeight() / 2 + k * 70, rect.getWidth() / 2, rect.getHeight() / 2);
        // Bottom Right
        drawTexturedModalRect(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2, 200 - rect.getWidth() / 2, 70 - rect.getHeight() / 2 + k * 70, rect.getWidth() / 2, rect.getHeight() / 2);

        drawContent(mouseX, mouseY);
    }

    protected void drawContent(int mouseX, int mouseY)
    {
        int l = 0xe0e0e0;

        if (!isEnabled)
        {
            l = -0x5f5f60;
        }
        else if (hover)
        {
            l = 0xffffa0;
        }

        drawCenteredString(text, rect.getX() + rect.getWidth() / 2, rect.getY() + (rect.getHeight() - 8) / 2, l);
    }
}
