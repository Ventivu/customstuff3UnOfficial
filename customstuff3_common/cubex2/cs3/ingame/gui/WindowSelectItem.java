package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;

public class WindowSelectItem extends Window implements IListBoxItemClickListener<ItemStack>
{
    private ListBox<ItemStack> lbItems;
    private ItemStack selectedStack = null;

    public WindowSelectItem()
    {
        super("Select Item", SELECT | CANCEL, 197, 201);
    }

    public ItemStack getSelectedStack()
    {
        return selectedStack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = ItemStackHelper.getAllItemStacks();
        lbItems = new ListBox<>(desc, this);
        addControl(lbItems);

        btnSelect.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnCancel)
        {
            selectedStack = null;
        }
        else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        }
        super.controlClicked(c, mouseX, mouseY, button);
    }

    @Override
    public void itemClicked(ItemStack item, ListBox<ItemStack> listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedStack = listBox.getSelectedItem();
    }
}
