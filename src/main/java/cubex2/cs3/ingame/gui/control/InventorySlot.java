package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.util.GuiHelper;

public class InventorySlot extends Control
{
    public InventorySlot(Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(18, 18, anchor, offsetX, offsetY, parent);
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (rootControl.drawSlots)
        {
            int x = getX() + 1;
            int y = getY() + 1;
            GuiHelper.drawRect(x, y, x + 16, y + 16, 0x770000dd);
        }
    }
}
