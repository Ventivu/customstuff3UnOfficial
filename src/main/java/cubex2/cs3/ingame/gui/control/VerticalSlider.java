package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class VerticalSlider extends Control
{
    private static final int SCROLL_THUMNB_HEIGHT = 10;
    private static final int CENTER_LINE_WIDTH = 6;

    private int maxValue;
    private int currentValue = 0;
    private int scrollOffset = 0;
    private Rectangle scrollThumbRect;
    private boolean mouseDown = false;
    private boolean mouseOverControl = false;
    private int prevScrollOffset = -1;
    private int my = -1;
    private int wheelScrollStep = 1;
    private boolean wheelScrollEverywhere = false;
    private boolean listBoxRendering = false;

    private IVerticalSliderValueListener listener;

    public VerticalSlider(int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        scrollThumbRect = new Rectangle(getX(), getY(), getWidth(), SCROLL_THUMNB_HEIGHT);
        this.maxValue = maxValue;

        if (rootControl instanceof IVerticalSliderValueListener)
        {
            listener = (IVerticalSliderValueListener) rootControl;
        }
    }

    public void setValueListener(IVerticalSliderValueListener listener)
    {
        this.listener = listener;
    }

    public int getValue()
    {
        return currentValue;
    }

    public float getValueFloat()
    {
        return scrollOffset / (float) (getHeight() - SCROLL_THUMNB_HEIGHT) * maxValue;
    }

    public void setWheelScrollStep(int value)
    {
        wheelScrollStep = value;
    }

    public void setWheelScrollEverywhere(boolean value)
    {
        wheelScrollEverywhere = value;
    }

    public void setListBoxRendering(boolean value)
    {
        listBoxRendering = value;
    }

    public void setMaxValue(int value)
    {
        maxValue = value;

        currentValue = Math.min(currentValue, maxValue);
        int scrollHeight = getHeight() - SCROLL_THUMNB_HEIGHT;
        scrollOffset = (int) (scrollHeight / (float) maxValue * currentValue);
        scrollThumbRect.setY(getY() + scrollOffset);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        scrollThumbRect = new Rectangle(getX(), getY(), getWidth(), SCROLL_THUMNB_HEIGHT);
        scrollThumbRect.setY(getY() + scrollOffset);
    }

    @Override
    public void onUpdate()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && (mouseOverControl || wheelScrollEverywhere) && maxValue > 0)
        {
            currentValue = MathHelper.clamp_int(currentValue - wheel / 120 * wheelScrollStep, 0, maxValue);
            int scrollHeight = getHeight() - SCROLL_THUMNB_HEIGHT;
            scrollOffset = (int) (scrollHeight / (float) maxValue * currentValue);
            scrollThumbRect.setY(getY() + scrollOffset);

            if (listener != null)
                listener.valueChanged(this);
        }
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0 && maxValue > 0)
        {
            mouseDown = true;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                scrollOffset = MathHelper.clamp_int(mouseY - getY() - SCROLL_THUMNB_HEIGHT / 2, 0, getHeight() - SCROLL_THUMNB_HEIGHT);

            prevScrollOffset = scrollOffset;
            my = mouseY;

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
                scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseY - my, 0, getHeight() - SCROLL_THUMNB_HEIGHT);
                updateScroll();
            }
        }
    }

    public void updateScroll()
    {
        scrollThumbRect.setY(getY() + scrollOffset);
        int scrollHeight = getHeight() - SCROLL_THUMNB_HEIGHT;
        float heightPerScroll = (scrollHeight) / (float) ((maxValue + 1));
        currentValue = (int) (scrollOffset / heightPerScroll);
        currentValue = MathHelper.clamp_int(currentValue, 0, maxValue);

        if (listener != null)
            listener.valueChanged(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mouseOverControl = bounds.contains(mouseX, mouseY);

        if (mouseDown)
        {
            scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseY - my, 0, getHeight() - SCROLL_THUMNB_HEIGHT);
            updateScroll();
        }

        if (listBoxRendering)
        {
            GuiHelper.drawOutlinedRect(getBounds(), Color.DARK_GREY, Color.LIGHT_GREY);
        }
        else
        {
            int x1 = getX() + (getWidth() - CENTER_LINE_WIDTH) / 2;
            int y1 = getY();
            int x2 = x1 + CENTER_LINE_WIDTH;
            int y2 = y1 + getHeight();
            GuiHelper.drawRect(x1, y1, x2, y2, Color.LIGHT_GREY);
        }

        // Scroll bar thumb
        if (maxValue > 0)
        {
            if (scrollThumbRect.contains(mouseX, mouseY) || mouseDown)
            {
                GuiHelper.drawOutlinedRect(scrollThumbRect, Color.WHITE, Color.DARK_GREY);
            }
            else
            {
                GuiHelper.drawRect(scrollThumbRect, Color.DARK_GREY);
            }
        }
    }
}
