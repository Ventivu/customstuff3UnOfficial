package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.TradeRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.listbox.IListBoxItemClickListener;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowTradeRecipes extends Window implements IWindowClosedListener<WindowEditOrCreateTradeRecipe>, IListBoxItemClickListener<TradeRecipe>
{
    private final IngameContentPack pack;

    private ListBox<TradeRecipe> listBox;

    public WindowTradeRecipes(IngameContentPack pack)
    {
        super("Trade Recipes", NEW | EDIT | DELETE | BACK, 263, 171);
        this.pack = pack;

        ListBoxDescription<TradeRecipe> desc = new ListBoxDescription<TradeRecipe>(7, 7);
        desc.columns = 2;
        desc.rows = 6;
        desc.elementHeight = 22;
        desc.elements = pack.getContentRegistry(TradeRecipe.class).getContentList();
        desc.canSelect = true;
        listBox = listBox(desc).fillWidth(7).top(7).add();

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    @Override
    public void itemClicked(TradeRecipe item, ListBox<TradeRecipe> listBox, int button)
    {
        btnEdit.setEnabled(listBox.getSelectedIndex() != -1);
        btnDelete.setEnabled(listBox.getSelectedIndex() != -1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnNew)
        {
            GuiBase.openWindow(new WindowEditOrCreateTradeRecipe(pack));
        } else if (c == btnEdit)
        {
            GuiBase.openWindow(new WindowEditOrCreateTradeRecipe(listBox.getSelectedItem(), pack));
        } else if (c == btnDelete)
        {
            listBox.getSelectedItem().remove();
            listBox.updateElements(pack.getContentRegistry(TradeRecipe.class).getContentList());
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowEditOrCreateTradeRecipe window)
    {
        listBox.updateElements(pack.getContentRegistry(TradeRecipe.class).getContentList());
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
