package cubex2.cs3.block;

import cubex2.cs3.common.WrappedBlock;

public class BlockCSCrossTexture extends BlockCS
{
    public BlockCSCrossTexture(WrappedBlock block)
    {
        super(block);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
