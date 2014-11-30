package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowBlocks extends Window implements IWindowClosedListener, IListBoxItemClickListener<WrappedBlock>
{
    private final IngameContentPack pack;

    private ListBox<WrappedBlock> listBox;

    public WindowBlocks(IngameContentPack pack)
    {
        super("Blocks", NEW | EDIT | DELETE | BACK, 263, 160);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<WrappedBlock> desc = new ListBoxDescription<WrappedBlock>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(WrappedBlock.class).getContentList();
        desc.canSelect = true;
        listBox = new ListBox<WrappedBlock>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowCreateBlock(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditBlock(listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(WrappedBlock.class).getContentList());
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
        listBox.updateElements(pack.getContentRegistry(WrappedBlock.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(WrappedBlock item, ListBox<WrappedBlock> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1 && listBox.getSelectedItem().block != null);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
