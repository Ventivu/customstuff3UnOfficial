package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.control.builder.*;

import java.util.Collections;
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

    public List<Control> getControls()
    {
        return Collections.unmodifiableList(controls);
    }

    public Control getControlAt(int x, int y)
    {
        for (Control c : controls)
        {
            if (c.isMouseOverControl(x, y))
            {
                if (c instanceof ControlContainer)
                {
                    ControlContainer cc = (ControlContainer) c;
                    Control c1 = cc.getControlAt(x, y);
                    return c1 != null ? c1 : cc;
                } else
                {
                    return c;
                }
            }
        }

        if (isMouseOverControl(x, y))
        {
            return this;
        }
        return null;
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
        boolean wasLocked = GuiBase.inputLockedControl != null;

        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled && c.isVisible)
            {
                if (!c.canHandleInput())
                    continue;

                boolean clickedControl = c.rect.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                }

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
            }
        }
    }

    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        for (int i = 0; i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled && c.isVisible)
            {
                if (!c.canHandleInput())
                    continue;

                c.mouseUp(mouseX, mouseY, button);

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
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
                if (!c_.canHandleInput())
                    continue;

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

    /* Builders */
    public LabelBuilder label(String text)
    {
        return new LabelBuilder(text, this);
    }

    public TextBoxBuilder textBox()
    {
        return new TextBoxBuilder(this);
    }

    public DropBoxBuilder dropBox(String[] values)
    {
        return new DropBoxBuilder(values, this);
    }

    public ButtonBuilder button(String text)
    {
        return new ButtonBuilder(text, this);
    }

    public ItemDisplayBuilder itemDisplay()
    {
        return new ItemDisplayBuilder(this);
    }

    public InfoButtonBuilder infoButton(String text)
    {
        return new InfoButtonBuilder(text, this);
    }

    public ButtonUpDownBuilder buttonUpDown(boolean up)
    {
        return new ButtonUpDownBuilder(up, this);
    }

    public PlayerDisplayBuilder playerDisplay()
    {
        return new PlayerDisplayBuilder(this);
    }

    public CheckBoxBuilder checkBox()
    {
        return new CheckBoxBuilder(this);
    }

    public TextFieldBuilder textField()
    {
        return new TextFieldBuilder(this);
    }
}
