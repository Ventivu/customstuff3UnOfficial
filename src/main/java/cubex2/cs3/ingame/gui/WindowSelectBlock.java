package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.Filter;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WindowSelectBlock extends Window implements IListBoxItemClickListener<ItemStack>
{
    private ListBox<ItemStack> lbItems;
    private ItemStack selectedStack = null;
    private boolean wildCardStacks = true;
    private boolean subBlocks = true;

    private TextBox tbSearch;

    private ISelectElementCallback<ItemStack> callback;

    public WindowSelectBlock()
    {
        this(false, false);
    }

    public WindowSelectBlock(boolean wildCardStacks, boolean subBlocks)
    {
        super("Select Block", SELECT | CANCEL, 197, 211);
        this.wildCardStacks = wildCardStacks;
        this.subBlocks = subBlocks;

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = getStacks();
        lbItems = listBox(desc).left(7).top(7).right(7).add();

        btnSelect.setEnabled(false);

        tbSearch = textBox().top(lbItems, 3).fillWidth(7).add();
    }

    private List<ItemStack> getStacks()
    {
        if (subBlocks)
        {
            return ItemStackHelper.getBlockStacks(wildCardStacks);
        } else
        {
            return ItemStackHelper.getBlockOnlyStacks();
        }
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
    public void keyTyped(char c, int key)
    {
        String prev = tbSearch.getText();
        super.keyTyped(c, key);
        String now = tbSearch.getText();
        if (!prev.equals(now))
        {
            lbItems.updateElements(getStacks(), Filter.ITEM_STACK, now);
        }
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
