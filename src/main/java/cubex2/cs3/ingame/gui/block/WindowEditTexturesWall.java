package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesWall extends WindowEditTexturesBase
{
    public WindowEditTexturesWall(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        blockDisplay.camY = 1.5f;
        blockDisplay.camX = -0.75f;
        blockDisplay.camZ = 2.0f;
        blockDisplay.lookX = 0.5f;
        blockDisplay.lookY = 0.5f;
        blockDisplay.lookZ = 0.5f;


        world.setBlock(null, 1, 0, 0);
        world.setBlock(null, 0, 0, -1);
        world.setBlock(null, 1, 0, -1);
        world.setBlock(wrappedBlock.block, 0, 0, 0);
        world.setBlock(wrappedBlock.block, -1, 0, 0);
        world.setBlock(wrappedBlock.block, 0, 0, 1);

    }
}
