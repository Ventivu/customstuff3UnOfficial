package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowShapelessRecipes extends Window implements IWindowClosedListener<WindowEditOrCreateShapelessRecipe>, IListBoxItemClickListener<ShapelessRecipe>
{
    private final IngameContentPack pack;

    private ListBox<ShapelessRecipe> listBox;

    public WindowShapelessRecipes(IngameContentPack pack)
    {
        super("Shapeless Recipes", NEW | EDIT | DELETE | BACK, 263, 165);
        this.pack = pack;

        ListBoxDescription<ShapelessRecipe> desc = new ListBoxDescription<ShapelessRecipe>(7, 7);
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
        desc.elements = pack.getContentRegistry(ShapelessRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateShapelessRecipe(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateShapelessRecipe(listBox.getSelectedItem(), pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(ShapelessRecipe.class).getContentList());
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateShapelessRecipe window)
    {
        listBox.updateElements(pack.getContentRegistry(ShapelessRecipe.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    @Override
    public void itemClicked(ShapelessRecipe item, ListBox<ShapelessRecipe> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }
}
