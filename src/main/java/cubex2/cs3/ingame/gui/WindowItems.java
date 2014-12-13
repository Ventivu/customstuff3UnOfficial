package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowItems extends Window implements IWindowClosedListener, IListBoxItemClickListener<WrappedItem>
{
    private final IngameContentPack pack;

    private ListBox<WrappedItem> listBox;

    public WindowItems(IngameContentPack pack)
    {
        super("Items", NEW | EDIT | DELETE | BACK, 263, 160);
        this.pack = pack;

        ListBoxDescription<WrappedItem> desc = new ListBoxDescription<WrappedItem>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(WrappedItem.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).left(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowCreateItem(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditItem(listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(WrappedItem.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(Window window)
    {
        listBox.updateElements(pack.getContentRegistry(WrappedItem.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(WrappedItem item, ListBox<WrappedItem> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1 && listBox.getSelectedItem().item != null);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
