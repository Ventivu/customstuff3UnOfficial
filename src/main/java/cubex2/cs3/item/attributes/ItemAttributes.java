package cubex2.cs3.item.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.item.*;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.creativetab.CreativeTabs;

public class ItemAttributes extends AttributeContainer
{
    @Attribute(windowClass = WindowEditDisplayName.class)
    public String displayName = "Unnamed";

    @Attribute(windowClass = WindowEditIcon.class)
    public IconWrapper icon = new IconWrapper("");

    @Attribute(windowClass = WindowEditMaxStack.class)
    public int maxStack = 64;

    @Attribute(windowClass = WindowEditFull3D.class)
    public boolean full3d = false;

    @Attribute(windowClass = WindowEditHasEffect.class)
    public boolean hasEffect = false;

    @Attribute(windowClass = WindowEditCreativeTab.class)
    public CreativeTabs creativeTab = CreativeTabs.tabAllSearch;

    public ItemAttributes(BaseContentPack pack)
    {
        super(pack);
    }
}
