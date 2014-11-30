package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditContainerItem extends WindowEditBlockAttribute implements IWindowClosedListener<WindowSelectItem>
{
    private ItemDisplay itemDisplay;
    private CheckBox checkBox;

    public WindowEditContainerItem(WrappedBlock block)
    {
        super(block, "containerItem", 150, 100);
    }

    @Override
    public void init()
    {
        super.init();

        itemDisplay = itemDisplay().y(8).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setClearOnRightClick();
        itemDisplay.setItemStack(wrappedBlock.container.containerItem);

        checkBox = checkBox().below(itemDisplay, 5).x(7).add();
        checkBox.setIsChecked(wrappedBlock.container.leaveContainerItem);

        label("Remain in crafting grid").rightTo(checkBox).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(false));
        }
        else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        wrappedBlock.container.containerItem = itemDisplay.getItemStack();
        wrappedBlock.container.leaveContainerItem = checkBox.getIsChecked();
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

