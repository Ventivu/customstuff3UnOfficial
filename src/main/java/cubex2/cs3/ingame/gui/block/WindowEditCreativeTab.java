package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import net.minecraft.creativetab.CreativeTabs;

public class WindowEditCreativeTab extends WindowEditBlockAttribute implements IStringProvider<CreativeTabs>
{
    private DropBox<CreativeTabs> dbTabs;

    public WindowEditCreativeTab(WrappedBlock block)
    {
        super(block, "creativeTab", 150, 55);
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
    protected void applyChanges()
    {
        wrappedBlock.container.creativeTab = dbTabs.getSelectedValue();
    }

    @Override
    public String getStringFor(CreativeTabs value)
    {
        return value.getTabLabel();
    }
}
