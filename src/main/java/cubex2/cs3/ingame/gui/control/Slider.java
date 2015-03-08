package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public abstract class Slider extends Control
{
    protected static final int CENTER_LINE_SIZE = 6;

    protected Rectangle scrollThumbRect;
    protected int mousePos = -1;
    protected boolean mouseDown = false;
    private boolean mouseOverControl = false;
    protected int scrollOffset;
    protected int prevScrollOffset = -1;

    private int wheelScrollStep = 1;
    private boolean wheelScrollEverywhere = false;
    private boolean wheelScrollParent = false;

    protected boolean listBoxRendering = false;

    private int currentValue;
    protected int maxValue;

    private IValueListener<Slider> listener;

    public Slider(int maxValue, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        this.maxValue = maxValue;

        if (rootControl instanceof IValueListener)
        {
            listener = (IValueListener<Slider>) rootControl;
        }
    }

    protected abstract int getSliderSize();

    protected abstract int getThumbSize();

    protected abstract void updateThumbRect();

    public void setValueListener(IValueListener<Slider> listener)
    {
        this.listener = listener;
    }

    public int getValue()
    {
        return currentValue;
    }

    public float getValueFloat()
    {
        return scrollOffset / (float) (getSliderSize() - getThumbSize()) * maxValue;
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
        int scrollHeight = getSliderSize() - getThumbSize();
        scrollOffset = (int) (scrollHeight / (float) maxValue * currentValue);
        updateThumbRect();
    }

    @Override
    public void onUpdate()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && (mouseOverControl || wheelScrollEverywhere) && maxValue > 0)
        {
            currentValue = MathHelper.clamp_int(currentValue - wheel / 120 * wheelScrollStep, 0, maxValue);
            int scrollWidth = getSliderSize() - getThumbSize();
            scrollOffset = (int) (scrollWidth / (float) maxValue * currentValue);
            updateThumbRect();

            if (listener != null)
                listener.onValueChanged(this);
        }
    }

    public void updateScroll()
    {
        updateThumbRect();
        int scrollWidth = getSliderSize() - getThumbSize();
        float widthPerScroll = (scrollWidth) / (float) ((maxValue + 1));
        currentValue = (int) (scrollOffset / widthPerScroll);
        currentValue = MathHelper.clamp_int(currentValue, 0, maxValue);

        if (listener != null)
            listener.onValueChanged(this);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        mouseOverControl = bounds.contains(mouseX, mouseY);
        if (wheelScrollParent && !mouseOverControl)
        {
            mouseOverControl = parent.bounds.contains(mouseX, mouseY);
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
