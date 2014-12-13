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
    private boolean wildCardStacks = true;

    private ISelectItemCallback callback;

    public WindowSelectItem()
    {
        this(false);
    }

    public WindowSelectItem(boolean wildCardStacks)
    {
        super("Select Item", SELECT | CANCEL, 197, 201);
        this.wildCardStacks = wildCardStacks;

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = ItemStackHelper.getAllItemStacks(wildCardStacks);
        lbItems = listBox(desc).left(7).top(7).add();

        btnSelect.setEnabled(false);
    }

    public void setCallback(ISelectItemCallback callback)
    {
        this.callback = callback;
    }

    public ItemStack getSelectedStack()
    {
        return selectedStack;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedStack = null;
        } else if (c == btnSelect)
        {
            if (callback != null)
            {
                callback.itemSelected(selectedStack);
            }
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(ItemStack item, ListBox<ItemStack> listBox, int button)
    {
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        selectedStack = listBox.getSelectedItem();
    }
}
