package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;

public class WindowSelectItem extends Window implements IListBoxItemClickListener<ItemStack>
{
    private ListBox<ItemStack> lbItems;
    private ItemStack selectedStack = null;
    private boolean wildCardStacks = true;

    private ISelectElementCallback<ItemStack> callback;

    public WindowSelectItem()
    {
        this(false, null);
    }

    public WindowSelectItem(boolean wildCardStacks)
    {
        this(wildCardStacks, null);
    }

    public WindowSelectItem(boolean wildCardStacks, ISelectElementCallback<ItemStack> callback)
    {
        super("Select Item", SELECT | CANCEL, 197, 211);
        this.wildCardStacks = wildCardStacks;
        this.callback = callback;

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = ItemStackHelper.getAllItemStacks(wildCardStacks);
        desc.hasSearchBar = true;
        desc.filter = Filter.ITEM_STACK;
        lbItems = listBox(desc).left(7).right(7).top(7).add();
        lbItems.getSearchBox().setFocused(true);

        btnSelect.setEnabled(false);
    }

    public void setCallback(ISelectElementCallback<ItemStack> callback)
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
            GuiBase.openPrevWindow();
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
