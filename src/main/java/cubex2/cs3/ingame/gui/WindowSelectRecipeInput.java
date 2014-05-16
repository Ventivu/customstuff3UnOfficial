package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Tab;
import cubex2.cs3.ingame.gui.control.TabControl;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.util.RecipeInput;
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
        super("Select", SELECT | CANCEL, 197, 201);
        this.pack = pack;
    }

    public RecipeInput getSelectedInput()
    {
        if (selectedInput == null) return null;
        return selectedInput instanceof ItemStack ? new RecipeInput((ItemStack) selectedInput) : new RecipeInput(((OreDictionaryClass) selectedInput).oreClass);
    }

    @Override
    public void init()
    {
        super.init();

        tabControl = new TabControl(70, 20, getWidth(), getHeight(), this);
        Tab aliasTab = tabControl.addTab("Items");
        Tab oreTab = tabControl.addTab("Ore Classes");
        addControl(tabControl);

        ListBoxDescription<ItemStack> desc = new ListBoxDescription<ItemStack>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = ItemStackHelper.getAllItemStacks();
        desc.sorted = false;
        desc.listBoxItemMeta = 1;
        lbItems = new ListBox<ItemStack>(desc, aliasTab);
        aliasTab.addControl(lbItems);

        ListBoxDescription<OreDictionaryClass> desc1 = new ListBoxDescription<OreDictionaryClass>(7, 7);
        desc1.elementWidth = 22;
        desc1.elementHeight = 22;
        desc1.columns = 7;
        desc1.rows = 7;
        desc1.elements = OreDictionaryClass.getAllClasses();
        desc1.sorted = true;
        lbOreDictClasses = new ListBox<OreDictionaryClass>(desc1, oreTab);
        oreTab.addControl(lbOreDictClasses);

        btnSelect.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnCancel)
        {
            selectedInput = null;
        } else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        }
        super.controlClicked(c, mouseX, mouseY, button);
    }

    @Override
    public void itemClicked(Object item, ListBox listBox, int button)
    {
        ListBox otherListBox = (listBox.getRect() == lbItems.getRect() ? lbOreDictClasses : lbItems);
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        otherListBox.removeSelection();

        selectedInput = listBox.getSelectedItem();
    }
}
