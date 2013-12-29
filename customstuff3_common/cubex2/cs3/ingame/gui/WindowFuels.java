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

        ListBoxDescription<Fuel> desc = new ListBoxDescription<>(7, 7);
        desc.width = 249;
        desc.rows = 7;
        desc.elementHeight = 22;
        desc.elements = pack.getContentManager("FuelManager").getContentList();
        desc.canSelect = true;
        listBox = new ListBox<>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void itemClicked(Fuel item, ListBox<Fuel> listBox, int button)
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
            GuiBase.openWindow(new WindowEditOrCreateFuel(pack));
        }
        else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateFuel(listBox.getSelectedItem(), pack));
        }
        else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentManager(Fuel.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateFuel window)
    {
        listBox.updateElements(pack.getContentManager(Fuel.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}
