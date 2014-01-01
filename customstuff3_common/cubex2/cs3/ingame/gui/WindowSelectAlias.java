package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowSelectAlias extends Window implements IListBoxItemClickListener<Alias>
{
    private IngameContentPack pack;
    private ListBox<Alias> lbItems;
    private Alias selectedAlias = null;

    public WindowSelectAlias(IngameContentPack pack)
    {
        super("Select Alias", SELECT | CANCEL, 197, 201);
        this.pack = pack;
    }

    public Alias getSelectedAlias()
    {
        return selectedAlias;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<Alias> desc = new ListBoxDescription<>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = pack.getContentManager(Alias.class).getContentList();
        desc.sorted = true;
        desc.listBoxItemMeta = 1;
        lbItems = new ListBox<>(desc, this);
        addControl(lbItems);

        btnSelect.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnCancel)
        {
            selectedAlias = null;
        }
        else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        }
        super.controlClicked(c, mouseX, mouseY, button);
    }

    @Override
    public void itemClicked(Alias item, ListBox<Alias> listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedAlias = listBox.getSelectedItem();
    }
}
