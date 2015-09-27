package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.gui.data.SlotData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.InventorySlot;

public class WindowInventorySlot extends WindowEditOrCreateControl<InventorySlot, SlotData>
{
    public WindowInventorySlot(WrappedGui gui, Window window, InventorySlot control, SlotData data)
    {
        super("Edit Slot", gui, window, control, data);
    }

    public WindowInventorySlot(WrappedGui gui, Window window, int x, int y)
    {
        super("Create Slot", gui, window, x, y, -1, -1);
    }

    @Override
    protected SlotData createData()
    {
        return new SlotData(nupX.getValue(), nupY.getValue());
    }

    @Override
    protected void edit()
    {

    }
}
