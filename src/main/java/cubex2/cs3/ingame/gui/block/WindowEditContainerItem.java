package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditContainerItem extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private WrappedBlock wrappedBlock;

    private ItemDisplay itemDisplay;
    private CheckBox checkBox;

    public WindowEditContainerItem(WrappedBlock item)
    {
        super("containerItem", EDIT | CANCEL, 150, 100);
        wrappedBlock = item;
    }

    @Override
    public void init()
    {
        super.init();

        itemDisplay = itemDisplay().y(8).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setItemStack(wrappedBlock.container.containerItem);

        checkBox = checkBox().below(itemDisplay, 5).x(7).add();
        checkBox.setIsChecked(wrappedBlock.container.leaveContainerItem);

        label("Remain in crafting grid").rightTo(checkBox).add();

    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0 && c != itemDisplay)
            return;

        if (c == btnEdit)
        {
            wrappedBlock.container.containerItem = itemDisplay.getItemStack();
            wrappedBlock.container.leaveContainerItem = checkBox.getIsChecked();
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else if (c == itemDisplay)
        {
            if (button == 0)
            {
                GuiBase.openWindow(new WindowSelectItem(false));
            } else if (button == 1)
            {
                itemDisplay.setItemStack(null);
            }
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        ItemStack stack = window.getSelectedStack();
        if (stack != null)
        {
            itemDisplay.setItemStack(stack);
        }
    }
}

