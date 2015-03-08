package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class HorizontalSlider extends Slider
{
    private static final int SCROLL_THUMB_WIDTH = 16;

    public HorizontalSlider(int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(maxValue, width, height, anchor, offsetX, offsetY, parent);
        scrollThumbRect = new Rectangle(getX(), getY(), SCROLL_THUMB_WIDTH, getHeight());
    }

    @Override
    protected int getSliderSize()
    {
        return getWidth();
    }

    @Override
    protected int getThumbSize()
    {
        return SCROLL_THUMB_WIDTH;
    }

    @Override
    protected void updateThumbRect()
    {
        scrollThumbRect.setX(getX() + scrollOffset);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        scrollThumbRect = new Rectangle(getX(), getY(), SCROLL_THUMB_WIDTH, getHeight());
        updateThumbRect();
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0 && maxValue > 0)
        {
            mouseDown = true;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                scrollOffset = MathHelper.clamp_int(mouseX - getX() - SCROLL_THUMB_WIDTH / 2, 0, getWidth() - SCROLL_THUMB_WIDTH);

            prevScrollOffset = scrollOffset;
            mousePos = mouseX;

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
                scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseX - mousePos, 0, getWidth() - SCROLL_THUMB_WIDTH);
                updateScroll();
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (mouseDown)
        {
            scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseX - mousePos, 0, getWidth() - SCROLL_THUMB_WIDTH);
            updateScroll();
        }

        if (listBoxRendering)
        {
            GuiHelper.drawOutlinedRect(getBounds(), Color.DARK_GREY, Color.LIGHT_GREY);
        } else
        {
            int x1 = getX();
            int y1 = getY() + (getHeight() - CENTER_LINE_SIZE) / 2;
            int x2 = x1 + getWidth();
            int y2 = y1 + CENTER_LINE_SIZE;
            GuiHelper.drawRect(x1, y1, x2, y2, Color.LIGHT_GREY);
        }

        super.draw(mouseX, mouseY, renderTick);
    }
}
