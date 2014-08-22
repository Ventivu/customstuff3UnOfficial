package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.lib.CreativeTabs;

public class WindowEditCreativeTab extends Window
{
    private WrappedItem wrappedItem;

    private DropBox dbTabs;

    public WindowEditCreativeTab(WrappedItem item)
    {
        super("creativeTab", EDIT | CANCEL, 150, 55);
        wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        dbTabs = dropBox(CreativeTabs.getTabNames()).y(7).fillWidth(7).add();
        dbTabs.setSelectedValue(wrappedItem.container.creativeTab.getTabLabel());
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedItem.container.creativeTab = CreativeTabs.getCreativeTab(dbTabs.getSelectedValue());
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
