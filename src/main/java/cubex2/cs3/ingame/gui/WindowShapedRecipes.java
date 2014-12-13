package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowShapedRecipes extends Window implements IWindowClosedListener<WindowEditOrCreateShapedRecipe>, IListBoxItemClickListener<ShapedRecipe>
{
    private IngameContentPack pack;

    private ListBox<ShapedRecipe> listBox;

    public WindowShapedRecipes(IngameContentPack pack)
    {
        super("Shaped Recipes", NEW | EDIT | DELETE | BACK, 263, 165);
        this.pack = pack;

        ListBoxDescription<ShapedRecipe> desc = new ListBoxDescription<ShapedRecipe>(7, 7);
        desc.width = 249;
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
        desc.elements = pack.getContentRegistry(ShapedRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).left(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateShapedRecipe(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateShapedRecipe(listBox.getSelectedItem(), pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(ShapedRecipe.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateShapedRecipe window)
    {
        listBox.updateElements(pack.getContentRegistry(ShapedRecipe.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(ShapedRecipe item, ListBox<ShapedRecipe> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
