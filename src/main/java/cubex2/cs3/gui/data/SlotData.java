package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import net.minecraft.inventory.IInventory;

public class SlotData extends ControlData
{
    public SlotData()
    {
    }

    public SlotData(int x, int y)
    {
        super(x, y, 0, 0);
    }

    @Override
    public boolean isSizeable()
    {
        return false;
    }

    @Override
    public Control addToWindow(Window window, IInventory slotInv)
    {
        return window.inventorySlot().at(0, 0).offset(x, y).add();
    }

    @Override
    protected String getControlType()
    {
        return "invSlot";
    }
}
