package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.creativetab.CreativeTabs;

public class DoorAttributes extends BlockAttributes
{
    @Attribute(windowClass = WindowEditBoolean.class, additionalInfo = "Only redstone can open door")
    public boolean redstoneOnly = false;

    // TODO window
    public IconWrapper iconFile = new IconWrapper("");

    public DoorAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
    }
}
