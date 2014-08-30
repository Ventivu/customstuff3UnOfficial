package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditContainerItem extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private WrappedItem wrappedItem;

    private ItemDisplay itemDisplay;

    public WindowEditContainerItem(WrappedItem item)
    {
        super("containerItem", EDIT | CANCEL, 150, 100);
        wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        itemDisplay = itemDisplay().y(31).centerHor().add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setItemStack(wrappedItem.container.containerItem);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0 && c != itemDisplay)
            return;

        if (c == btnEdit)
        {
            wrappedItem.container.containerItem = itemDisplay.getItemStack();
            wrappedItem.getPack().save();

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
