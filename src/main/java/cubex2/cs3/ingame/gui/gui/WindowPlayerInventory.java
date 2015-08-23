package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.gui.data.PlayerInventoryData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.PlayerInventoryArea;

public class WindowPlayerInventory extends WindowEditOrCreateControl<PlayerInventoryArea, PlayerInventoryData>
{
    public WindowPlayerInventory(GuiData guiData, Window window, PlayerInventoryArea control, PlayerInventoryData data)
    {
        super("Edit Player Inventory", guiData, window, control, data);
    }

    public WindowPlayerInventory(GuiData guiData, Window window, int x, int y)
    {
        super("Create Player Inventory", guiData, window, x, y, -1, -1);
    }

    @Override
    protected PlayerInventoryData createData()
    {
        return new PlayerInventoryData(nupX.getValue(), nupY.getValue());
    }

    @Override
    protected void edit()
    {

    }
}
