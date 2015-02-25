package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowSmeltingRecipes extends Window implements IListBoxItemClickListener<SmeltingRecipe>, IWindowClosedListener<WindowEditOrCreateSmeltingRecipe>
{
    private IngameContentPack pack;

    private ListBox<SmeltingRecipe> listBox;

    public WindowSmeltingRecipes(IngameContentPack pack)
    {
        super("Smelting Recipes", BACK | NEW | EDIT | DELETE, 263, 201);
        this.pack = pack;

        ListBoxDescription<SmeltingRecipe> desc = new ListBoxDescription<SmeltingRecipe>(7, 7);
        desc.rows = 7;
        desc.columns = 3;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(SmeltingRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void itemClicked(SmeltingRecipe item, ListBox<SmeltingRecipe> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateSmeltingRecipe(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateSmeltingRecipe(listBox.getSelectedItem(), pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(SmeltingRecipe.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateSmeltingRecipe window)
    {
        listBox.updateElements(pack.getContentRegistry(SmeltingRecipe.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}
