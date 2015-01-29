package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesFence extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"bottom", "top", "north", "south", "east", "west"};

    public WindowEditTexturesFence(WrappedBlock block)
    {
        super(block, textures, false, true, false);

        world.setBlock(null, 0, 0, 0);
    }
}
