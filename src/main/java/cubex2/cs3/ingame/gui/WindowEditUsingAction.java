package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import net.minecraft.item.EnumAction;

public class WindowEditUsingAction extends Window implements IStringProvider<EnumAction>
{
    private WrappedItem wrappedItem;

    private DropBox<EnumAction> dbActions;

    public WindowEditUsingAction(WrappedItem item)
    {
        super("usingAction", EDIT | CANCEL, 150, 55);
        wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        dbActions = dropBox(EnumAction.values()).y(7).fillWidth(7).add();
        dbActions.setStringProvider(this);
        dbActions.setSelectedValue(wrappedItem.container.usingAction);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
             wrappedItem.container.usingAction = dbActions.getSelectedValue();
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String getStringFor(EnumAction value)
    {
        return value.name();
    }
}
