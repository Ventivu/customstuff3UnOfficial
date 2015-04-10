package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowContentList;
import cubex2.cs3.ingame.gui.WindowCreateItem;
import cubex2.cs3.ingame.gui.common.WindowEditAttributeContent;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowItems extends WindowContentList<WrappedItem>
{
    public WindowItems(BaseContentPack pack)
    {
        super(WrappedItem.class, "Items", 263, 160, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<WrappedItem> desc)
    {
        desc.rows = 5;
        desc.elementHeight = 22;
        desc.sorted = true;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowCreateItem(pack);
    }

    @Override
    protected Window createEditContentWindow(WrappedItem content)
    {
        return new WindowEditAttributeContent(content);
    }
}
