package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import net.minecraft.creativetab.CreativeTabs;

public class WindowEditCreativeTab extends Window implements IStringProvider<CreativeTabs>
{
    private WrappedBlock wrappedBlock;

    private DropBox<CreativeTabs> dbTabs;

    public WindowEditCreativeTab(WrappedBlock block)
    {
        super("creativeTab", EDIT | CANCEL, 150, 55);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        dbTabs = dropBox(CreativeTabs.creativeTabArray).y(7).fillWidth(7).add();
        dbTabs.setStringProvider(this);
        dbTabs.setSelectedValue(wrappedBlock.container.creativeTab);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedBlock.container.creativeTab = dbTabs.getSelectedValue();
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String getStringFor(CreativeTabs value)
    {
        return value.getTabLabel();
    }
}
