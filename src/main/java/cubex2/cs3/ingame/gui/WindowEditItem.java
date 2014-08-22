package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowEditItem extends Window implements IListBoxItemClickListener<String>
{
    private final WrappedItem wrappedItem;

    private ListBox<String> listBox;

    public WindowEditItem(WrappedItem wrappedItem)
    {
        super(wrappedItem.getName(), BACK, 263, 160);
        this.wrappedItem = wrappedItem;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<String> desc = new ListBoxDescription<String>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = Lists.newArrayList(wrappedItem.container.getAttributeFieldNames());
        desc.canSelect = false;
        listBox = new ListBox<String>(desc, this);
        addControl(listBox);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        super.controlClicked(c, mouseX, mouseY, button);
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        try
        {
            GuiBase.openWindow(wrappedItem.container.getWindowClass(item).getConstructor(WrappedItem.class).newInstance(wrappedItem));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
