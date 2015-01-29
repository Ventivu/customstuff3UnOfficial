package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;

public abstract class WindowEditBlockAttribute extends Window
{
    protected final WrappedBlock wrappedBlock;
    protected final BlockAttributes container;

    public WindowEditBlockAttribute(WrappedBlock block, String title, int usedControls, int width, int height)
    {
        super(title, usedControls, width, height);
        wrappedBlock = block;
        container = block.container;
    }

    public WindowEditBlockAttribute(WrappedBlock block, String title, int width, int height)
    {
        super(title, EDIT | CANCEL, width, height);
        wrappedBlock = block;
        container = block.container;
    }

    @Override
    protected void handleEditButtonClicked()
    {
        applyChanges();
        saveAndClose();
    }

    protected void applyChanges()
    {

    }

    protected void saveAndClose()
    {
        wrappedBlock.getPack().save();
        GuiBase.openPrevWindow();
    }
}
