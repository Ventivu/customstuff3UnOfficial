package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowShapedRecipes extends Window implements IWindowClosedListener<WindowEditOreCreateShapedRecipe>
{
    private IngameContentPack pack;

    private ListBox<ShapedRecipe> listBox;

    public WindowShapedRecipes(IngameContentPack pack)
    {
        super("Shaped Recipes", NEW | EDIT | DELETE | BACK, 263, 165);
        this.pack = pack;
    }

    @Override
    public void init()
    {
        super.init();

        ListBoxDescription<ShapedRecipe> desc = new ListBoxDescription<ShapedRecipe>(7, 7);
        desc.width = 249;
        desc.rows = 2;
        desc.columns = 2;
        desc.elementHeight = 60;
        desc.elements = pack.getContentRegistry(ShapedRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = new ListBox<ShapedRecipe>(desc, this);
        addControl(listBox);

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOreCreateShapedRecipe(pack));
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void windowClosed(WindowEditOreCreateShapedRecipe window)
    {
        listBox.updateElements(pack.getContentRegistry(ShapedRecipe.class).getContentList());
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
    }
}
