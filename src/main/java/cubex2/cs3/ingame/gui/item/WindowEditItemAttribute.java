package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;

public abstract class WindowEditItemAttribute extends Window
{
    protected final WrappedItem wrappedItem;

    public WindowEditItemAttribute(WrappedItem item, String title, int usedControls, int width, int height)
    {
        super(title, usedControls, width, height);
        wrappedItem = item;
    }

    public WindowEditItemAttribute(WrappedItem item, String title, int width, int height)
    {
        super(title, EDIT | CANCEL, width, height);
        wrappedItem = item;
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
        wrappedItem.getPack().save();
        GuiBase.openPrevWindow();
    }
}
