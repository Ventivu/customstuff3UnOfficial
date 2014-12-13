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

        ListBoxDescription<OreDictionaryEntry> desc = new ListBoxDescription<OreDictionaryEntry>(7, 7);
        desc.width = 249;
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(OreDictionaryEntry.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).left(7).top(7).add();

        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
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
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(OreDictionaryEntry item, ListBox<OreDictionaryEntry> listBox, int button)
    {
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(WindowEditOrCreateOreDictEntry window)
    {
        listBox.updateElements(pack.getContentRegistry(OreDictionaryEntry.class).getContentList());
        btnDelete.setEnabled(false);
    }
}
