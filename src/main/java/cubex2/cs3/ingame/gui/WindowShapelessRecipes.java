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
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<ShapelessRecipe> desc = new ListBoxDescription<ShapelessRecipe>(7, 7);
        desc.width = 249;
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
        desc.elements = pack.getContentRegistry(ShapelessRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = new ListBox<ShapelessRecipe>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

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
            super.controlClicked(c, mouseX, mouseY, button);
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
