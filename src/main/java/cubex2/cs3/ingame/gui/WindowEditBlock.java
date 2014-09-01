package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.ingame.gui.common.WindowEditInteger;
import cubex2.cs3.ingame.gui.common.WindowEditScript;

public class WindowEditBlock extends Window implements IListBoxItemClickListener<String>
{
    private final WrappedBlock wrappedBlock;

    private ListBox<String> listBox;

    public WindowEditBlock(WrappedBlock wrappedBlock)
    {
        super(wrappedBlock.getName(), BACK, 263, 160);
        this.wrappedBlock = wrappedBlock;
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
        desc.elements = Lists.newArrayList(wrappedBlock.container.getAttributeFieldNames());
        desc.canSelect = false;
        desc.sorted = true;
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
            Class<? extends Window> windowClass = wrappedBlock.container.getWindowClass(item);
            if (windowClass == WindowEditScript.class)
            {
                GuiBase.openWindow(new WindowEditScript(item, wrappedBlock.container));
            } else if (windowClass == WindowEditInteger.class)
            {
                GuiBase.openWindow(new WindowEditInteger(item, wrappedBlock.container));
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
