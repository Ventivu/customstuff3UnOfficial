package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.tileentity.TileEntityInventory;
import net.minecraft.util.ResourceLocation;

public class FuelProgressBar extends ImageProgressBar
{
    private final String name;
    private final TileEntityInventory tile;

    public FuelProgressBar(String name, TileEntityInventory tile, ResourceLocation texture, int u, int v, int direction, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(texture, u, v, direction, width, height, anchor, offsetX, offsetY, parent);
        this.name = name;
        this.tile = tile;
    }

    @Override
    public int getProgress()
    {
        if (tile == null)
            return getWidth() / 2;
        return tile.getBurnTimeRemainingScaled(name, getWidth());
    }
}
