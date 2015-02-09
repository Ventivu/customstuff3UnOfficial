package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.IngameContentPackLoader;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowContentPacks extends Window implements IListBoxItemClickListener<IngameContentPack>
{
    private ListBox<IngameContentPack> lbPacks;

    public WindowContentPacks()
    {
        super("Content Packs", NEW | BACK, 180, 201);

        ListBoxDescription<IngameContentPack> desc = new ListBoxDescription<IngameContentPack>(7, 7);
        desc.rows = 12;
        desc.elements = IngameContentPackLoader.instance().getContentPacks();
        desc.sorted = true;
        desc.canSelect = false;
        lbPacks = listBox(desc).left(7).right(7).top(7).height(12 * 14 - 1).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowNewPack(lbPacks));
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(IngameContentPack item, ListBox<IngameContentPack> listBox, int button)
    {
        if (button == 0)
        {
            GuiBase.openWindow(new WindowEditPack(item));
        }
    }
}
