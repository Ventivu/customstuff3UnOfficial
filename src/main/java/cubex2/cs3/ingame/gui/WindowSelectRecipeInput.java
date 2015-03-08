package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Tab;
import cubex2.cs3.ingame.gui.control.TabControl;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.util.RecipeInput;
import cubex2.cs3.util.Filter;
import net.minecraft.item.ItemStack;

public class WindowSelectRecipeInput extends Window implements IListBoxItemClickListener
{
    private IngameContentPack pack;
    private ListBox<ItemStack> lbItems;
    private ListBox<OreDictionaryClass> lbOreDictClasses;
    private TabControl tabControl;
    private Object selectedInput = null;

    public WindowSelectRecipeInput(IngameContentPack pack)
    {
        super("Select", SELECT | CANCEL, 197, 211);
        this.pack = pack;

        tabControl = tabControl(70, 20).fill().add();
        Tab itemTab = tabControl.addTab("Items");
        Tab oreTab = tabControl.addTab("Ore Classes");

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = ItemStackHelper.getAllItemStacks();
        desc.listBoxItemMeta = 1;
        desc.hasSearchBar = true;
        desc.filter = Filter.ITEM_STACK;
        lbItems = itemTab.listBox(desc).left(7).top(7).right(7).add();

        ListBoxDescription<OreDictionaryClass> desc1 = new ListBoxDescription<OreDictionaryClass>(7, 7);
        desc1.elementWidth = 22;
        desc1.elementHeight = 22;
        desc1.columns = 7;
        desc1.rows = 7;
        desc1.elements = OreDictionaryClass.getAllClasses();
        desc1.sorted = true;
        desc1.hasSearchBar = true;
        desc1.filter = Filter.ORE_CLASS;
        lbOreDictClasses = oreTab.listBox(desc1).left(7).top(7).right(7).add();

        btnSelect.setEnabled(false);
    }

    public RecipeInput getSelectedInput()
    {
        if (selectedInput == null) return null;
        return selectedInput instanceof ItemStack ? new RecipeInput((ItemStack) selectedInput) : new RecipeInput(((OreDictionaryClass) selectedInput).oreClass);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCancel)
        {
            selectedInput = null;
            GuiBase.openPrevWindow();
        } else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void itemClicked(Object item, ListBox listBox, int button)
    {
        ListBox otherListBox = (listBox.getBounds() == lbItems.getBounds() ? lbOreDictClasses : lbItems);
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        otherListBox.removeSelection();

        selectedInput = listBox.getSelectedItem();
    }
}
