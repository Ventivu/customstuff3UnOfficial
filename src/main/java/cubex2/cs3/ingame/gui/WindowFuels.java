package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowFuels extends Window implements IListBoxItemClickListener<Fuel>, IWindowClosedListener<WindowEditOrCreateFuel>
{
    private IngameContentPack pack;

    private ListBox<Fuel> listBox;

    public WindowFuels(IngameContentPack pack)
    {
        super("Fuels", BACK | NEW | EDIT | DELETE, 263, 201);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<Fuel> desc = new ListBoxDescription<Fuel>(7, 7);
        desc.width = 249;
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(Fuel.class).getContentList();
        desc.canSelect = true;
        listBox = new ListBox<Fuel>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void itemClicked(Fuel item, ListBox<Fuel> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateFuel(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateFuel(listBox.getSelectedItem(), pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(Fuel.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateFuel window)
    {
        listBox.updateElements(pack.getContentRegistry(Fuel.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}
