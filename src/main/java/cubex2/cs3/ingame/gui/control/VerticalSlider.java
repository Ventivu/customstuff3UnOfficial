package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class VerticalSlider extends Slider
{
    private static final int SCROLL_THUMB_HEIGHT = 10;

    public VerticalSlider(int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(maxValue, width, height, anchor, offsetX, offsetY, parent);
        scrollThumbRect = new Rectangle(getX(), getY(), getWidth(), SCROLL_THUMB_HEIGHT);
    }

    @Override
    protected int getSliderSize()
    {
        return getHeight();
    }

    @Override
    protected int getThumbSize()
    {
        return SCROLL_THUMB_HEIGHT;
    }

    @Override
    protected void updateThumbRect()
    {
        scrollThumbRect.setY(getY() + scrollOffset);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        scrollThumbRect = new Rectangle(getX(), getY(), getWidth(), SCROLL_THUMB_HEIGHT);
        updateThumbRect();
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0 && maxValue > 0)
        {
            mouseDown = true;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                scrollOffset = MathHelper.clamp_int(mouseY - getY() - SCROLL_THUMB_HEIGHT / 2, 0, getHeight() - SCROLL_THUMB_HEIGHT);

            prevScrollOffset = scrollOffset;
            mousePos = mouseY;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                updateScroll();
        }
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        if (mouseDown)
        {
            if (button == 0)
            {
                mouseDown = false;
                scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseY - mousePos, 0, getHeight() - SCROLL_THUMB_HEIGHT);
                updateScroll();
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (mouseDown)
        {
            scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseY - mousePos, 0, getHeight() - SCROLL_THUMB_HEIGHT);
            updateScroll();
        }

        if (listBoxRendering)
        {
            GuiHelper.drawOutlinedRect(getBounds(), Color.DARK_GREY, Color.LIGHT_GREY);
        } else
        {
            int x1 = getX() + (getWidth() - CENTER_LINE_SIZE) / 2;
            int y1 = getY();
            int x2 = x1 + CENTER_LINE_SIZE;
            int y2 = y1 + getHeight();
            GuiHelper.drawRect(x1, y1, x2, y2, Color.LIGHT_GREY);
        }

        super.draw(mouseX, mouseY, renderTick);
    }
}
