package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.control.builder.*;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.RecipeInput;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public abstract class ControlContainer extends Control
{
    protected List<Control> controls = Lists.newArrayList();

    public ControlContainer(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
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
    public void onParentResized()
    {
        super.onParentResized();

        for (Control control : controls)
        {
            control.onParentResized();
        }
    }

    @Override
    public void onUpdate()
    {
        for (Control control : controls)
        {
            control.onUpdate();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        for (Control c : controls)
        {
            if (c.isEnabled() && c.isVisible())
            {
                if (!c.canHandleInput())
                    continue;

                boolean clickedControl = c.isMouseOverControl(mouseX, mouseY);// c.bounds.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                    if (button == 0)
                        controlClicked(c, mouseX, mouseY);
                }

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
            }
        }
    }

    /**
     * This is called when a control has been clicked
     *
     * @param c      The clicked control.
     * @param mouseX The x-coord of the mouse
     * @param mouseY The y-coord of the mouse
     * @param button The mouse button (0=left, 1=right)
     */
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
    }

    /**
     * This is called when a control has been left-clicked
     *
     * @param c      The clicked control.
     * @param mouseX The x-coord of the mouse
     * @param mouseY The y-coord of the mouse
     */
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {

    }

    @Override
    public void mouseUp(int mouseX, int mouseY, int button)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        for (Control c : controls)
        {
            if (c.isEnabled() && c.isVisible())
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
        for (Control c_ : controls)
        {
            if (c_.isEnabled() && c_.isVisible())
            {
                if (!c_.canHandleInput())
                    continue;

                c_.keyTyped(c, key);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        for (Control c : controls)
        {
            if (c.isVisible())
            {
                c.draw(mouseX, mouseY, renderTick);
            }
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        for (Control c : controls)
        {
            if (c.isVisible())
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

    public <T> DropBoxBuilder<T> dropBox(T[] values)
    {
        return new DropBoxBuilder<T>(values, this);
    }

    public ButtonBuilder button(String text)
    {
        return new ButtonBuilder(text, this);
    }

    public ItemDisplayBuilder itemDisplay()
    {
        return new ItemDisplayBuilder(null, this);
    }

    public ItemDisplayBuilder itemDisplay(ItemStack stack)
    {
        return new ItemDisplayBuilder(stack, this);
    }

    public InfoButtonBuilder infoButton(String text)
    {
        return new InfoButtonBuilder(text, this);
    }

    public ButtonUpDownBuilder buttonUpDown(boolean up)
    {
        return new ButtonUpDownBuilder(up, this);
    }

    public ButtonUpDownBuilder buttonUp()
    {
        return buttonUpDown(true);
    }

    public ButtonUpDownBuilder buttonDown()
    {
        return buttonUpDown(false);
    }

    public PlayerDisplayBuilder playerDisplay()
    {
        return new PlayerDisplayBuilder(this);
    }

    public CheckBoxBuilder checkBox()
    {
        return new CheckBoxBuilder(this);
    }

    public CheckBoxBuilder checkBox(boolean checked)
    {
        return new CheckBoxBuilder(this).checked(checked);
    }

    public CheckBoxBuilder checkBox(String text)
    {
        return new CheckBoxBuilder(text, this);
    }

    public CheckBoxBuilder checkBox(String text, boolean checked)
    {
        return new CheckBoxBuilder(text, this).checked(checked);
    }

    public TextFieldBuilder textField()
    {
        return new TextFieldBuilder(this);
    }

    public PictureBoxBuilder pictureBox(ResourceLocation texture, int u, int v)
    {
        return new PictureBoxBuilder(texture, u, v, this);
    }

    public WorldDisplayBuilder worldDisplay(SimulatedWorld world)
    {
        return new WorldDisplayBuilder(world, this);
    }

    public RecipeInputDisplayBuilder recipeInputDisplay()
    {
        return new RecipeInputDisplayBuilder(null, this);
    }

    public RecipeInputDisplayBuilder recipeInputDisplay(RecipeInput input)
    {
        return new RecipeInputDisplayBuilder(input, this);
    }

    public TabControlBuilder tabControl(int tabWidth, int tabHeight)
    {
        return new TabControlBuilder(tabWidth, tabHeight, this);
    }

    public VerticalSliderBuilder verticalSlider(int maxValue)
    {
        return new VerticalSliderBuilder(maxValue, this);
    }

    public <T> ListBoxBuilder<T> listBox(ListBoxDescription<T> desc)
    {
        return new ListBoxBuilder<T>(desc, this);
    }
}
