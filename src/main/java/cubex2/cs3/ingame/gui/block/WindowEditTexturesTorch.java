package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesTorch extends WindowEditTextureSingle
{
    public WindowEditTexturesTorch(WrappedBlock block)
    {
        super("bottom", block);

        worldDisplay.lookX = 0.5f;
        worldDisplay.lookY = 0.5f;
        worldDisplay.lookZ = 0.5f;
        worldDisplay.camX = 1.125f;
        worldDisplay.camY = 1.25f;
        worldDisplay.camZ = 1.125f;

        world.setBlock(null, 0, 0, -1);
        world.setBlock(null, 1, 0, -1);
        world.setBlock(null, 1, 0, 0);
        world.setMetadata(5,0,0,0);
    }
}
