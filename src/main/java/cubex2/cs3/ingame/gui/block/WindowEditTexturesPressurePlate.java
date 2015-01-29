package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesPressurePlate extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"bottom", "top", "north", "south", "east", "west"};

    public WindowEditTexturesPressurePlate(WrappedBlock block)
    {
        super(block, textures, false, true, false);

        blockDisplay.lookX = 0.5f;
        blockDisplay.lookY = 0.0f;
        blockDisplay.lookZ = 0.5f;
        blockDisplay.camX = 1.25f;
        blockDisplay.camY = 1.0f;
        blockDisplay.camZ = 1.25f;

        world.setBlock(null, 0, 0, -1);
        world.setBlock(null, 1, 0, -1);
        world.setBlock(null, 1, 0, 0);
    }
}
