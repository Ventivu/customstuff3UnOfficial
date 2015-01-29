package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditTexturesStairs extends WindowEditTexturesBase
{
    private Button btnRotate;
    private int rotation = 3;

    public WindowEditTexturesStairs(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        btnRotate = button("Rotate").width(40).bottom(blockDisplay, 0, true).right(blockDisplay, 3).add();

        world.setBlock(null, 0, 0, -1);
        world.setBlock(null, 1, 0, -1);
        world.setBlock(null, 1, 0, 0);
        world.setMetadata(rotation, 0, 0, 0);

        blockDisplay.lookX = 0.5f;
        blockDisplay.lookY = 0.5f;
        blockDisplay.lookZ = 0.5f;
        blockDisplay.camX = 1.5f;
        blockDisplay.camY = 1.5f;
        blockDisplay.camZ = 1.5f;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnRotate)
        {
            rotation = (rotation + 1) % 8;
            world.setMetadata(rotation, 0, 0, 0);
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }

}
