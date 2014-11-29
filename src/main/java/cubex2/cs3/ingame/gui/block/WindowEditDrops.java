package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.IWindowClosedListener;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSelectItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.BlockDrop;
import net.minecraft.item.ItemStack;

public class WindowEditDrops extends Window implements IWindowClosedListener, IListBoxItemClickListener<BlockDrop.DropData>
{
    private final WrappedBlock wrappedBlock;

    private ListBox<BlockDrop.DropData> listBox;

    public WindowEditDrops(WrappedBlock block)
    {
        super("drops", NEW | EDIT | DELETE | BACK, 263, 160);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<BlockDrop.DropData> desc = new ListBoxDescription<BlockDrop.DropData>(7, 7);
        desc.width = 249;
        desc.rows = 5;
        desc.columns = 1;
        desc.elementHeight = 22;
        desc.elements = wrappedBlock.container.drop.getDrops();
        desc.canSelect = true;
        listBox = new ListBox<BlockDrop.DropData>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowSelectItem(false));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditDropData(wrappedBlock, listBox.getSelectedItem()));
        } else if (c == btnDelete)
        {
            BlockDrop.DropData drop = listBox.getSelectedItem();
            wrappedBlock.container.drop.getDrops().remove(drop);
            listBox.updateElements(wrappedBlock.container.drop.getDrops());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            wrappedBlock.getPack().save();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void itemClicked(BlockDrop.DropData item, ListBox<BlockDrop.DropData> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window instanceof WindowSelectItem)
        {
            WindowSelectItem wdw = (WindowSelectItem) window;
            ItemStack stack = wdw.getSelectedStack();
            if (stack != null)
            {
                BlockDrop.DropData drop = new BlockDrop.DropData(stack.getItem(), stack.getItemDamage(), 1, 1);
                wrappedBlock.container.drop.getDrops().add(drop);
                listBox.updateElements(wrappedBlock.container.drop.getDrops());
                wrappedBlock.getPack().save();
            }
        }
        else if (window instanceof WindowEditDropData)
        {
            listBox.updateElements(wrappedBlock.container.drop.getDrops());
        }
    }
}
