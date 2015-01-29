package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.block.WindowEditTextureWheat;

public class WheatAttributes extends BlockAttributes
{
    public WheatAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
        textureWindow = WindowEditTextureWheat.class;
    }
}
