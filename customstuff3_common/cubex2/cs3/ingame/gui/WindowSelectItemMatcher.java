package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.common.IItemMatcher;
import cubex2.cs3.common.OreDictionaryClass;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Tab;
import cubex2.cs3.ingame.gui.control.TabControl;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowSelectItemMatcher extends Window implements IListBoxItemClickListener<IItemMatcher>
{
    private IngameContentPack pack;
    private ListBox<Alias> lbAliases;
    private ListBox<OreDictionaryClass> lbOreDictClasses;
    private TabControl tabControl;
    private IItemMatcher selectedItem = null;

    public WindowSelectItemMatcher(IngameContentPack pack)
    {
        super("Select", SELECT | CANCEL, 197, 201);
        this.pack = pack;
    }

    public IItemMatcher getSelectedItem()
    {
        return selectedItem;
    }

    @Override
    public void init()
    {
        super.init();

        tabControl = new TabControl(70, 20, getWidth(), getHeight(), this);
        Tab aliasTab = tabControl.addTab("Aliases");
        Tab oreTab = tabControl.addTab("Ore Classes");
        addControl(tabControl);

        ListBoxDescription<Alias> desc = new ListBoxDescription<>(7, 7);
        desc.elementWidth = 22;
        desc.elementHeight = 22;
        desc.columns = 7;
        desc.rows = 7;
        desc.elements = pack.getContentManager(Alias.class).getContentList();
        desc.sorted = true;
        desc.listBoxItemMeta = 1;
        lbAliases = new ListBox<>(desc, aliasTab);
        aliasTab.addControl(lbAliases);

        ListBoxDescription<OreDictionaryClass> desc1 = new ListBoxDescription<>(7, 7);
        desc1.elementWidth = 22;
        desc1.elementHeight = 22;
        desc1.columns = 7;
        desc1.rows = 7;
        desc1.elements = OreDictionaryClass.getAllClasses();
        desc1.sorted = true;
        lbOreDictClasses = new ListBox<>(desc1, oreTab);
        oreTab.addControl(lbOreDictClasses);

        btnSelect.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnCancel)
        {
            selectedItem = null;
        }
        else if (c == btnSelect)
        {
            GuiBase.openPrevWindow();
        }
        super.controlClicked(c, mouseX, mouseY, button);
    }

    @Override
    public void itemClicked(IItemMatcher item, ListBox<IItemMatcher> listBox, int button)
    {
        ListBox<? extends IItemMatcher> otherListBox = (listBox.getRect() == lbAliases.getRect() ? lbOreDictClasses : lbAliases);
        btnSelect.setEnabled(listBox.getSelectedIndex() != -1);
        otherListBox.removeSelection();
        selectedItem = listBox.getSelectedItem();
    }
}
