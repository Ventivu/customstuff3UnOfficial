package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowAliases extends Window implements IListBoxItemClickListener<Alias>, IWindowClosedListener<WindowEditOrCreateAlias>
{
    private IngameContentPack pack;

    private ListBox<Alias> listBox;

    public WindowAliases(IngameContentPack pack)
    {
        super("Aliases", BACK | NEW | EDIT | DELETE, 263, 201);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<Alias> desc = new ListBoxDescription<>(7, 7);
        desc.width = 249;
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.elements = pack.getContentManager("AliasManager").getContentList();
        desc.sorted = true;
        desc.canSelect = true;
        listBox = new ListBox<>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void itemClicked(Alias item, ListBox<Alias> listBox, int button)
    {
        if (button == 0)
        {
            btnEdit.setEnabled(item.canEdit());
            btnDelete.setEnabled(item.canRemove());
        }
        else
        {
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateAlias(pack));
        }
        else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateAlias(listBox.getSelectedItem(), pack));
        }
        else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentManager(Alias.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateAlias window)
    {
        listBox.updateElements(pack.getContentManager(Alias.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}
