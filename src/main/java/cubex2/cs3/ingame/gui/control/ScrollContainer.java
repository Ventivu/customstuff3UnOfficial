package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.ScissorHelper;
import org.lwjgl.util.Rectangle;

public class ScrollContainer extends ControlContainer
{
    private Rectangle visibleRect;

    protected int currentScroll = 0;
    private int visibleHeight;
    private int originY;

    public ScrollContainer(int visibleHeight, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);

        originY = getY();
        this.visibleHeight = visibleHeight;
        visibleRect = new Rectangle(getX(), getY(), getWidth(), visibleHeight);
    }

    public Rectangle getVisibleRect()
    {
        return visibleRect;
    }

    @Override
    public void onParentResized()
    {
        /*int absX = getX();
        int absY = getY();

        absX += parent.bounds.getX();
        absY += parent.bounds.getY();

        bounds.setBounds(absX, absY, getWidth(), getHeight());*/

        super.onParentResized();

        visibleRect = new Rectangle(getX(), getY() + currentScroll, getWidth(), visibleHeight);

        for (int i = 0; i < controls.size(); i++)
        {
            controls.get(i).onParentResized();
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled() && c.isVisible() &&
                    c.getY() >= visibleRect.getY() - c.getHeight() &&
                    c.getY() <= visibleRect.getY() + visibleRect.getHeight() &&
                    mouseY >= visibleRect.getY() && mouseY <= visibleRect.getY() + visibleRect.getHeight())
            {
                boolean clickedControl = c.bounds.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                    if (button == 0)
                        controlClicked(c, mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled() && c.isVisible() &&
                    c.getY() >= visibleRect.getY() - c.getHeight() &&
                    c.getY() <= visibleRect.getY() + visibleRect.getHeight() &&
                    mouseY >= visibleRect.getY() && mouseY <= visibleRect.getY() + visibleRect.getHeight())
            {
                c.mouseUp(mouseX, mouseY, button);
            }
        }
    }

    public void setCurrentScroll(int value)
    {
        currentScroll = value;
        updateScroll();
    }

    private void updateScroll()
    {
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
