package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Textures;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Button extends Control
{
    public boolean playSound = true;
    protected boolean hover;
    protected String text = "";

    /**
     * Uses 60 for width and 16 for height.
     */
    public Button(String text, Control parent)
    {
        this(text, 60, 16, parent);
    }

    /**
     * Uses 16 for height.
     */
    public Button(String text, int width, Control parent)
    {
        this(text, width, 16, parent);
    }

    public Button(String text, int width, int height, Control parent)
    {
        this(text, width, height, null, 0, 0, parent);
    }

    public Button(String text, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.text = text;
    }


    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0 && playSound)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    protected int getHoverState(boolean hover)
    {
        byte b0 = 1;

        if (!isEnabled())
        {
            b0 = 0;
        } else if (hover)
        {
            b0 = 2;
        }

        return b0;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mc.renderEngine.bindTexture(Textures.CONTROLS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        hover = isMouseOverControl(mouseX, mouseY);

        int k = getHoverState(hover);
        int heightChange = bounds.getHeight() % 2 != 0 ? 1 : 0;
        int widthChange = bounds.getWidth() % 2 != 0 ? 1 : 0;
        // Top Left
        drawTexturedModalRect(bounds.getX(), bounds.getY(), 0, k * 70, bounds.getWidth() / 2 + widthChange, bounds.getHeight() / 2 + heightChange);
        // Top Right
        drawTexturedModalRect(bounds.getX() + bounds.getWidth() / 2 + widthChange, bounds.getY(), 200 - bounds.getWidth() / 2, k * 70, bounds.getWidth() / 2, bounds.getHeight() / 2 + heightChange);
        // Bottom Left
        drawTexturedModalRect(bounds.getX(), bounds.getY() + bounds.getHeight() / 2 + heightChange, 0, 70 - bounds.getHeight() / 2 + k * 70, bounds.getWidth() / 2 + widthChange, bounds.getHeight() / 2);
        // Bottom Right
        drawTexturedModalRect(bounds.getX() + bounds.getWidth() / 2 + widthChange, bounds.getY() + bounds.getHeight() / 2 + heightChange, 200 - bounds.getWidth() / 2, 70 - bounds.getHeight() / 2 + k * 70, bounds.getWidth() / 2, bounds.getHeight() / 2);

        drawContent(mouseX, mouseY);
    }

    protected void drawContent(int mouseX, int mouseY)
    {
        int l = 0xe0e0e0;

        if (!isEnabled())
        {
            l = -0x5f5f60;
        } else if (hover)
        {
            l = 0xffffa0;
        }

        drawCenteredString(text, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + (bounds.getHeight() - 8) / 2, l);
    }
}
