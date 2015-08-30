package cubex2.cs3.tileentity.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;

public class TileEntityInventoryAttributes extends TileEntityAttributes
{
    @Attribute(windowClass = WindowEditInteger.class)
    public int slotCount = 0;

    public TileEntityInventoryAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
