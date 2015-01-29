package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditTextureTrapDoor extends WindowEditTexturesBase
{
    private Button btnRotate;
    private int rotation = 0;
    private Button btnState;
    private boolean isOpen = false;

    public WindowEditTextureTrapDoor(WrappedBlock block)
    {
        super(block, DEFAULT_TEXTURES, false, true, false);

        blockDisplay.lookX = 0.5f;
        blockDisplay.lookY = 0.5f;
        blockDisplay.lookZ = 0.5f;
        blockDisplay.camX = 1.5f;
        blockDisplay.camY = 1.5f;
        blockDisplay.camZ = 1.5f;

        world.setBlock(null, 0, 0, -1);
        world.setBlock(null, 1, 0, -1);
        world.setBlock(null, 1, 0, 0);

        btnRotate = button("Rotate").width(40).bottom(blockDisplay, 0, true).right(blockDisplay, 3).add();
        btnState = button("Open").width(40).bottom(btnRotate, 3).right(blockDisplay, 3).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnRotate)
        {
            rotation = (rotation + 1) % 4;
            int meta = rotation;
            if (isOpen)
                meta |= 4;
            else
                meta &= 3;
            world.setMetadata(meta, 0, 0, 0);
        } else if (c == btnState)
        {
            int meta = world.getBlockMetadata(0, 0, 0);
            if (isOpen)
                meta &= 3;
            else
                meta |= 4;
            isOpen = !isOpen;
            btnState.setText(isOpen ? "Close" : "Open");
            world.setMetadata(meta, 0, 0, 0);
        } else
        {
            super.controlClicked(c, mouseX, mouseY);
        }
    }
}
