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
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<IngameContentPack> desc = new ListBoxDescription<IngameContentPack>(7, 7);
        desc.width = 166;
        desc.rows = 12;
        desc.elements = IngameContentPackLoader.instance().getContentPacks();
        desc.sorted = true;
        desc.canSelect = false;
        lbPacks = new ListBox<IngameContentPack>(desc, this);
        addControl(lbPacks);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            if (c == btnNew)
            {
                GuiBase.openWindow(new WindowNewPack(lbPacks));
            }
            else
            {
                super.controlClicked(c, mouseX, mouseY, button);
            }
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
