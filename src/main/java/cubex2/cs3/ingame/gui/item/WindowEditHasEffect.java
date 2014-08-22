package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditHasEffect extends Window
{
    private WrappedItem wrappedItem;

    private CheckBox checkBox;
    private ItemDisplay itemDisplay;

    private boolean oldHasEffect;
    private boolean newHasEffect;

    public WindowEditHasEffect(WrappedItem item)
    {
        super("hasEffect", EDIT | CANCEL, 150, 100);
        wrappedItem = item;
        oldHasEffect = newHasEffect = wrappedItem.container.hasEffect;
    }

    @Override
    public void init()
    {
        super.init();

        checkBox = checkBox().at(7, 7).add();
        checkBox.setIsChecked(newHasEffect);
        label("Has effect").rightTo(checkBox).add();

        itemDisplay = itemDisplay().below(checkBox).add();
        itemDisplay.setItemStack(new ItemStack(wrappedItem.item));
        itemDisplay.setDrawSlotBackground();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == checkBox)
        {
            newHasEffect = checkBox.getIsChecked();
        } else if (c == btnEdit)
        {
            wrappedItem.container.hasEffect = newHasEffect;
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        wrappedItem.container.hasEffect = newHasEffect;
        super.draw(mouseX, mouseY);
        wrappedItem.container.hasEffect = oldHasEffect;
    }
}
