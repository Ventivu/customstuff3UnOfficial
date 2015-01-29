package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTexturesPressurePlate;
import net.minecraft.creativetab.CreativeTabs;

public class PressurePlateAttributes extends BlockAttributes
{
    public String[] include = new String[]{"all"};
    public String[] exclude = new String[0];

    public PressurePlateAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        creativeTab = CreativeTabs.tabRedstone;
        textureWindow = WindowEditTexturesPressurePlate.class;
    }
}
