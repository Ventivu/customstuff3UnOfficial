package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.ButtonUpDown;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class WindowEditMaxStack extends Window
{
    private WrappedBlock wrappedBlock;

    private ItemDisplay itemDisplay;
    private ButtonUpDown btnUp;
    private ButtonUpDown btnDown;

    private int newMaxStack;

    public WindowEditMaxStack(WrappedBlock item)
    {
        super("maxStack", EDIT | CANCEL, 150, 100);
        wrappedBlock = item;
        newMaxStack = wrappedBlock.container.maxStack;
    }

    @Override
    public void init()
    {
        super.init();

        itemDisplay = itemDisplay().y(31).centerHor(-5).add();
        itemDisplay.setItemStack(new ItemStack(wrappedBlock.block, newMaxStack, 0));
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
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnUp || c == btnDown)
        {
            int numChange = (GuiBase.isShiftKeyDown() ? 5 : 1) * (c == btnUp ? 1 : -1);
            newMaxStack = MathHelper.clamp_int(newMaxStack + numChange, 1, 64);
            maxStackChanged();
        } else if (c == btnEdit)
        {
            wrappedBlock.container.maxStack = newMaxStack;
            wrappedBlock.blockItem.setMaxStackSize(newMaxStack);
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
