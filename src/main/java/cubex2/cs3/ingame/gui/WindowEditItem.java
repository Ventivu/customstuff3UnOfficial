package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowEditItem extends Window implements IListBoxItemClickListener<AttributeData>
{
    private final WrappedItem wrappedItem;

    private ListBox<AttributeData> listBox;

    public WindowEditItem(WrappedItem wrappedItem)
    {
        super(wrappedItem.getName(), BACK, 263, 160);
        this.wrappedItem = wrappedItem;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<AttributeData> desc = new ListBoxDescription<AttributeData>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = Lists.newArrayList(wrappedItem.container.getAttributeDatas());
        desc.canSelect = false;
        desc.sorted = true;
        listBox = new ListBox<AttributeData>(desc, this);
        addControl(listBox);
    }

    @Override
    public void itemClicked(AttributeData item, ListBox<AttributeData> listBox, int button)
    {
        try
        {
            Class<? extends Window> windowClass = item.attribute.windowClass();
            if (windowClass == WindowEditScript.class)
            {
                GuiBase.openWindow(new WindowEditScript(item.field.getName(), wrappedItem.container));
            } else if (windowClass == WindowEditInteger.class)
            {
                GuiBase.openWindow(new WindowEditInteger(item, wrappedItem.container));
            } else
            {
                GuiBase.openWindow(windowClass.getConstructor(WrappedItem.class).newInstance(wrappedItem));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
