package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowOreDictionaryEntries extends Window implements IListBoxItemClickListener<OreDictionaryEntry>, IWindowClosedListener<WindowEditOrCreateOreDictEntry>
{
    private IngameContentPack pack;
    private ListBox<OreDictionaryEntry> listBox;

    public WindowOreDictionaryEntries(IngameContentPack pack)
    {
        super("Ore Dictionary Entries", NEW | DELETE | BACK, 263, 201);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<OreDictionaryEntry> desc = new ListBoxDescription<OreDictionaryEntry>(7, 7);
        desc.width = 249;
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(OreDictionaryEntry.class).getContentList();
        desc.canSelect = true;
        listBox = new ListBox<OreDictionaryEntry>(desc, this);
        addControl(listBox);

        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateOreDictEntry(pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(OreDictionaryEntry.class).getContentList());
            btnDelete.setEnabled(false);
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void itemClicked(OreDictionaryEntry item, ListBox<OreDictionaryEntry> listBox, int button)
    {
        if (button == 0)
        {
            btnDelete.setEnabled(item.canRemove());
        } else
        {
            btnDelete.setEnabled(false);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateOreDictEntry window)
    {
        listBox.updateElements(pack.getContentRegistry(OreDictionaryEntry.class).getContentList());
        btnDelete.setEnabled(false);
    }
}
