package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class ControlContainer extends Control
{
    protected List<Control> controls = Lists.newArrayList();

    public ControlContainer(int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
    }

    public void addControl(Control c)
    {
        controls.add(c);
    }

    @Override
    public void updateRect()
    {
        super.updateRect();

        for (int i = 0; i < controls.size(); i++)
        {
            controls.get(i).updateRect();
        }
    }

    @Override
    public void update()
    {
        for (int i = 0; i < controls.size(); i++)
        {
            controls.get(i).update();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled && c.isVisible)
            {
                boolean clickedControl = c.rect.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                }
            }
        }
    }

    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled && c.isVisible)
            {
                c.mouseUp(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyTyped(char c, int key)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c_ = controls.get(i);
            if (c_.isEnabled && c_.isVisible)
            {
                c_.keyTyped(c, key);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible)
            {
                c.draw(mouseX, mouseY);
            }
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible)
            {
                c.drawForeground(mouseX, mouseY);
            }
        }
    }
}
