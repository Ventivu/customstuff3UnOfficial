package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.SlotData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.InventorySlot;

public class WindowInventorySlot extends WindowEditOrCreateControl<InventorySlot, SlotData>
{
    private CheckBox cbFurnaceOutput;

    public WindowInventorySlot(WrappedGui gui, Window window, InventorySlot control, SlotData data)
    {
        super("Edit Slot", gui, window, control, data);
    }

    public WindowInventorySlot(WrappedGui gui, Window window, int x, int y)
    {
        super("Create Slot", gui, window, x, y, -1, -1);
    }

    @Override
    protected void initControls(boolean hasSize)
    {
        super.initControls(hasSize);

        cbFurnaceOutput = row(checkBox("Furnace Output", data != null && data.furnaceOutput));
    }

    @Override
    protected SlotData createData()
    {
        return new SlotData(nupX.getValue(), nupY.getValue(), cbFurnaceOutput.getIsChecked());
    }

    @Override
    protected void edit()
    {
        data.furnaceOutput = cbFurnaceOutput.getIsChecked();
    }
}
