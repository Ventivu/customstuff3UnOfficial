package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.ScissorHelper;
import org.lwjgl.util.Rectangle;

public class ScrollContainer extends ControlContainer
{
    private Rectangle visibleRect;

    private int currentScroll = 0;
    private int visibleHeight;
    private int originY;

    public ScrollContainer(int visibleHeight, int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);

        originY = y;
        this.visibleHeight = visibleHeight;
        visibleRect = new Rectangle(getX(), getY(), getWidth(), visibleHeight);
    }

    public Rectangle getVisibleRect()
    {
        return visibleRect;
    }

    @Override
    public void updateRect()
    {
        int absX = x;
        int absY = y;

        absX += parent.rect.getX();
        absY += parent.rect.getY();

        rect.setBounds(absX, absY, width, height);

        visibleRect = new Rectangle(getX(), getY() + currentScroll, getWidth(), visibleHeight);


        for (int i = 0; i < controls.size(); i++)
        {
            controls.get(i).updateRect();
        }
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled && c.isVisible &&
                    c.getY() >= visibleRect.getY() - c.getHeight() &&
                    c.getY() <= visibleRect.getY() + visibleRect.getHeight() &&
                    mouseY >= visibleRect.getY() && mouseY <= visibleRect.getY() + visibleRect.getHeight())
            {
                boolean clickedControl = c.rect.contains(mouseX, mouseY);
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
            if (c.isEnabled && c.isVisible &&
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
        y = (originY - currentScroll);
        updateRect();
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        ScissorHelper.startScissor(mc, visibleRect.getX(), visibleRect.getY(), visibleRect.getWidth(), visibleRect.getHeight());

        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible && c.getY() >= visibleRect.getY() - c.getHeight() && c.getY() <= visibleRect.getY() + visibleRect.getHeight())
            {
                c.draw(mouseX, mouseY, renderTick);
            }
        }

        ScissorHelper.endScissor();
    }
}
