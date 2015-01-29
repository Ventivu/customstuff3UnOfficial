package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;

public class WindowEditTexturesCarpet extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"bottom", "top", "north", "south", "east", "west"};

    public WindowEditTexturesCarpet(WrappedBlock block)
    {
        super(block, textures,false, true, false);
    }
}
