package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.util.SimulatedWorld;
import net.minecraft.init.Blocks;

public class WindowEditTexturesButton extends WindowEditTexturesBase
{
    private static final String[] textures = new String[]{"bottom", "top", "north", "south", "east", "west"};

    public WindowEditTexturesButton(WrappedBlock block)
    {
        super(block, textures, false, true, false);
        blockDisplay.lookX = 0.5f;
        blockDisplay.camX = 0.250f;
        blockDisplay.camZ = 0.75f;
        blockDisplay.camY = 1.0f;
    }

    @Override
    protected void fillWorld(SimulatedWorld world)
    {
        world.setBlock(Blocks.bedrock, 0, 0, -1);
        world.setBlock(wrappedBlock.block, 0, 0, 0);
        world.setMetadata(3, 0, 0, 0);
    }
}
