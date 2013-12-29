package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.List;

public class WindowEditPack extends Window implements IListBoxItemClickListener<String>
{
    private static final List<String> listBoxElements = Lists.newArrayList("Aliases");

    private IngameContentPack pack;

    private ListBox<String> listBox;

    public WindowEditPack(IngameContentPack pack)
    {
        super(pack.getName(), BACK, 180, 201);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<String> desc = new ListBoxDescription<>(7, 7);
        desc.width = 166;
        desc.rows = 12;
        desc.elements = listBoxElements;
        desc.canSelect = false;
        listBox = new ListBox<>(desc, this);
        addControl(listBox);
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        if (button == 0)
        {
            if (item.equals("Aliases"))
            {
                GuiBase.openWindow(new WindowAliases(pack));
            }
        }
    }
}
