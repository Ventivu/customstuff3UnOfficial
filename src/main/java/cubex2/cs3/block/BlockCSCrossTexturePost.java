package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.lib.RenderIds;

public class BlockCSCrossTexturePost extends BlockCSPost
{
    public BlockCSCrossTexturePost(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.crossTexturePostRenderId;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
