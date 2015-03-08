package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class HorizontalSlider extends Control
{
    private static final int SCROLL_THUMNB_WIDTH = 16;
    private static final int CENTER_LINE_HEIGHT = 6;

    private int maxValue;
    private int currentValue;
    private int scrollOffset;
    private Rectangle scrollThumbRect;
    private boolean mouseDown = false;
    private boolean mouseOverControl = false;
    private int prevScrollOffset = -1;
    private int mx = -1;
    private int wheelScrollStep = 1;
    private boolean wheelScrollEverywhere = false;
    private boolean wheelScrollParent = false;
    private boolean listBoxRendering = false;

    private IHorizontalSliderValueListener listener;

    public HorizontalSlider(int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        scrollThumbRect = new Rectangle(getX(), getY(), SCROLL_THUMNB_WIDTH, getHeight());
        this.maxValue = maxValue;

        if (rootControl instanceof IHorizontalSliderValueListener)
        {
            listener = (IHorizontalSliderValueListener) rootControl;
        }
    }

    public void setValueListener(IHorizontalSliderValueListener listener)
    {
        this.listener = listener;
    }

    public int getValue()
    {
        return currentValue;
    }

    public float getValueFloat()
    {
        return scrollOffset / (float) (getWidth() - SCROLL_THUMNB_WIDTH) * maxValue;
    }

    public void setWheelScrollStep(int value)
    {
        wheelScrollStep = value;
    }

    public void setWheelScrollEverywhere(boolean value)
    {
        wheelScrollEverywhere = value;
    }

    public void setWheelScrollParent(boolean value)
    {
        wheelScrollParent = value;
    }

    public void setListBoxRendering(boolean value)
    {
        listBoxRendering = value;
    }

    public void setMaxValue(int value)
    {
        maxValue = value;

        currentValue = Math.min(currentValue, maxValue);
        int scrollHeight = getWidth() - SCROLL_THUMNB_WIDTH;
        scrollOffset = (int) (scrollHeight / (float) maxValue * currentValue);
        scrollThumbRect.setX(getX() + scrollOffset);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        scrollThumbRect = new Rectangle(getX(), getY(), SCROLL_THUMNB_WIDTH, getHeight());
        scrollThumbRect.setX(getX() + scrollOffset);
    }

    @Override
    public void onUpdate()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && (mouseOverControl || wheelScrollEverywhere) && maxValue > 0)
        {
            currentValue = MathHelper.clamp_int(currentValue - wheel / 120 * wheelScrollStep, 0, maxValue);
            int scrollWidth = getWidth() - SCROLL_THUMNB_WIDTH;
            scrollOffset = (int) (scrollWidth / (float) maxValue * currentValue);
            scrollThumbRect.setX(getX() + scrollOffset);

            if (listener != null)
                listener.valueChanged(this);
        }
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            mouseDown = true;

            if (!scrollThumbRect.contains(mouseX, mouseY))
                scrollOffset = MathHelper.clamp_int(mouseX - getX() - SCROLL_THUMNB_WIDTH / 2, 0, getWidth() - SCROLL_THUMNB_WIDTH);

            prevScrollOffset = scrollOffset;
            mx = mouseX;

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
                scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseX - mx, 0, getWidth() - SCROLL_THUMNB_WIDTH);
                updateScroll();
            }
        }
    }

    public void updateScroll()
    {
        scrollThumbRect.setX(getX() + scrollOffset);
        int scrollWidth = getWidth() - SCROLL_THUMNB_WIDTH;
        float widthPerScroll = (scrollWidth) / (float) ((maxValue + 1));
        currentValue = (int) (scrollOffset / widthPerScroll);
        currentValue = MathHelper.clamp_int(currentValue, 0, maxValue);

        if (listener != null)
            listener.valueChanged(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mouseOverControl = bounds.contains(mouseX, mouseY);
        if (wheelScrollParent && !mouseOverControl)
        {
            mouseOverControl = parent.bounds.contains(mouseX, mouseY);
        }

        if (mouseDown)
        {
            scrollOffset = MathHelper.clamp_int(prevScrollOffset + mouseX - mx, 0, getWidth() - SCROLL_THUMNB_WIDTH);
            updateScroll();
        }

        if (listBoxRendering)
        {
            GuiHelper.drawOutlinedRect(getBounds(), Color.DARK_GREY, Color.LIGHT_GREY);
        } else
        {
            int x1 = getX();
            int y1 = getY() + (getHeight() - CENTER_LINE_HEIGHT) / 2;
            int x2 = x1 + getWidth();
            int y2 = y1 + CENTER_LINE_HEIGHT;
            GuiHelper.drawRect(x1, y1, x2, y2, Color.LIGHT_GREY);
        }

        // Scroll bar thumb
        if (maxValue > 0)
        {
            if (scrollThumbRect.contains(mouseX, mouseY) || mouseDown)
            {
                GuiHelper.drawOutlinedRect(scrollThumbRect, Color.WHITE, Color.DARK_GREY);
            } else
            {
                GuiHelper.drawRect(scrollThumbRect, Color.DARK_GREY);
            }
        }
    }
}
