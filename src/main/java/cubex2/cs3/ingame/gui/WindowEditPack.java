package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

import java.util.List;

public class WindowEditPack extends Window implements IListBoxItemClickListener<String>
{
    private static final List<String> listBoxElements = Lists.newArrayList("Fuels", "Smelting Recipes",
            "Ore Dictionary", "Shaped Recipes");

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

        ListBoxDescription<String> desc = new ListBoxDescription<String>(7, 7);
        desc.width = 166;
        desc.rows = 12;
        desc.elements = listBoxElements;
        desc.canSelect = false;
        listBox = new ListBox<String>(desc, this);
        addControl(listBox);
    }

    @Override
    public void itemClicked(String item, ListBox<String> listBox, int button)
    {
        if (button == 0)
        {
            if (item.equals("Fuels"))
            {
                GuiBase.openWindow(new WindowFuels(pack));
            }
            else if (item.equals("Smelting Recipes"))
            {
                GuiBase.openWindow(new WindowSmeltingRecipes(pack));
            }
            else if (item.equals("Ore Dictionary"))
            {
                GuiBase.openWindow(new WindowOreDictionaryEntries(pack));
            }
            else if (item.equals("Shaped Recipes"))
            {
                GuiBase.openWindow(new WindowShapedRecipes(pack));
            }
        }
    }
}
