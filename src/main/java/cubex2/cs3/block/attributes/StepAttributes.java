package cubex2.cs3.block.attributes;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.block.Block;

public class StepAttributes extends BlockAttributes
{
    public Block doubleSlabBlock;

    public int doubleSlabMeta;

    public StepAttributes(BaseContentPack pack)
    {
        super(pack);
        opacity = 0;
    }
}
