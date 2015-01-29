package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.init.Blocks;

public class WindowEditTexturesLadder extends WindowEditTextureSingle
{
    public WindowEditTexturesLadder(WrappedBlock block)
    {
        super("bottom", block);
        worldDisplay.lookX = 0.5f;
        worldDisplay.lookY = 0.5f;
        worldDisplay.lookZ = 0.0f;
        worldDisplay.camX = 0.250f;
        worldDisplay.camZ = 0.75f;
        worldDisplay.camY = 1.0f;

        world.setBlock(Blocks.bedrock, 0, 0, -1);
        world.setMetadata(3,0,0,0);
    }
}
