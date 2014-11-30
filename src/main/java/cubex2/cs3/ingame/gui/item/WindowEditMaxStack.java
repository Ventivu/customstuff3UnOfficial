package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.ButtonUpDown;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class WindowEditMaxStack extends WindowEditItemAttribute
{
    private ItemDisplay itemDisplay;
    private ButtonUpDown btnUp;
    private ButtonUpDown btnDown;

    private int newMaxStack;

    public WindowEditMaxStack(WrappedItem item)
    {
        super(item, "maxStack", EDIT | CANCEL, 150, 100);
        newMaxStack = wrappedItem.container.maxStack;
    }

    @Override
    public void init()
    {
        super.init();

        itemDisplay = itemDisplay().y(31).centerHor(-5).add();
        itemDisplay.setItemStack(new ItemStack(wrappedItem.item, newMaxStack, 0));
        itemDisplay.setDrawSlotBackground();

        btnUp = buttonUpDown(true).rightTo(itemDisplay).add();
        btnDown = buttonUpDown(false).rightTo(itemDisplay).add();

        maxStackChanged();
    }

    private void maxStackChanged()
    {
        btnUp.setEnabled(newMaxStack < 64);
        btnDown.setEnabled(newMaxStack > 1);

        itemDisplay.setStackSize(newMaxStack);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnUp || c == btnDown)
        {
            int numChange = (GuiBase.isShiftKeyDown() ? 5 : 1) * (c == btnUp ? 1 : -1);
            newMaxStack = MathHelper.clamp_int(newMaxStack + numChange, 1, 64);
            maxStackChanged();
        }
        else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        wrappedItem.container.maxStack = newMaxStack;
        wrappedItem.item.setMaxStackSize(newMaxStack);
    }
}
