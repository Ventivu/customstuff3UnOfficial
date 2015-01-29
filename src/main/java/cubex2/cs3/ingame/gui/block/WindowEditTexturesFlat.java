package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesFlat extends WindowEditTextureSingle
{
    public WindowEditTexturesFlat(WrappedBlock block)
    {
        super("bottom", block);

        world.setMetadata(1, 0, 0, 0);
    }
}
