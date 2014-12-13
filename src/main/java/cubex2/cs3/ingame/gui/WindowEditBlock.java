package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.common.WindowEditBoolean;
import cubex2.cs3.ingame.gui.common.WindowEditFloat;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowEditBlock extends Window implements IListBoxItemClickListener<AttributeData>
{
    private final WrappedBlock wrappedBlock;

    private ListBox<AttributeData> listBox;

    public WindowEditBlock(WrappedBlock wrappedBlock)
    {
        super(wrappedBlock.getName(), BACK, 263, 160);
        this.wrappedBlock = wrappedBlock;

        ListBoxDescription<AttributeData> desc = new ListBoxDescription<AttributeData>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = Lists.newArrayList(wrappedBlock.container.getAttributeDatas());
        desc.canSelect = false;
        desc.sorted = true;
        listBox = listBox(desc).left(7).top(7).add();
    }

    @Override
    public void itemClicked(AttributeData item, ListBox<AttributeData> listBox, int button)
    {
        try
        {
            Class<? extends Window> windowClass = item.attribute.windowClass();
            if (windowClass == WindowEditScript.class)
            {
                GuiBase.openWindow(new WindowEditScript(item.field.getName(), wrappedBlock.container));
            } else if (windowClass == WindowEditInteger.class)
            {
                GuiBase.openWindow(new WindowEditInteger(item, wrappedBlock.container));
            } else if (windowClass == WindowEditFloat.class)
            {
                GuiBase.openWindow(new WindowEditFloat(item.field.getName(), wrappedBlock.container));
            } else if (windowClass == WindowEditBoolean.class)
            {
                GuiBase.openWindow(new WindowEditBoolean(item, wrappedBlock.container));
            } else
            {
                GuiBase.openWindow(windowClass.getConstructor(WrappedBlock.class).newInstance(wrappedBlock));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
