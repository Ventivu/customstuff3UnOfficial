package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowFuels extends WindowContentList<Fuel>
{
    public WindowFuels(BaseContentPack pack)
    {
        super(Fuel.class, "Fuels", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<Fuel> desc)
    {
        desc.rows = 7;
        desc.elementHeight = 22;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateFuel(pack);
    }

    @Override
    protected Window createEditContentWindow(Fuel content)
    {
        return new WindowEditOrCreateFuel(content, pack);
    }
}
