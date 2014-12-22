package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.ScissorHelper;
import org.lwjgl.util.Rectangle;

public class ScrollContainer extends ControlContainer
{
    private Rectangle visibleRect;

    protected int currentScroll = 0;
    private int visibleHeight;

    public ScrollContainer(int visibleHeight, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);

        this.visibleHeight = visibleHeight;
        visibleRect = new Rectangle(getX(), getY(), getWidth(), visibleHeight);
    }

    public Rectangle getVisibleRect()
    {
        return visibleRect;
    }

    @Override
    public boolean isMouseOverControl(int mouseX, int mouseY)
    {
        return parent.isMouseOverControl(mouseX, mouseY) && visibleRect.contains(mouseX, mouseY);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        visibleRect = new Rectangle(getX(), getY() + currentScroll, getWidth(), visibleHeight);
    }

    public void setCurrentScroll(int value)
    {
        currentScroll = value;
        offsetY = -currentScroll;
        onParentResized();
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        ScissorHelper.startScissor(mc, visibleRect.getX(), visibleRect.getY(), visibleRect.getWidth(), visibleRect.getHeight());

        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible() && c.getY() >= visibleRect.getY() - c.getHeight() && c.getY() <= visibleRect.getY() + visibleRect.getHeight())
            {
                c.draw(mouseX, mouseY, renderTick);
            }
        }

        ScissorHelper.endScissor();
    }
}
