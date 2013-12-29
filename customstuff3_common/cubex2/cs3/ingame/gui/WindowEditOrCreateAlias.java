package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import net.minecraft.item.Item;

public class WindowEditOrCreateAlias extends Window implements IValidityProvider, IWindowClosedListener<WindowSelectItem>
{
    private IngameContentPack pack;
    private Alias editingAlias;

    private Label lblName;
    private Label lblItem;
    private TextBox tbName;
    private ItemDisplay itemDisplay;

    public WindowEditOrCreateAlias(IngameContentPack pack)
    {
        super("New Alias", CREATE | CANCEL, 180, 201);
        this.pack = pack;
    }

    public WindowEditOrCreateAlias(Alias alias, IngameContentPack pack)
    {
        super("Edit Alias", EDIT | CANCEL, 180, 201);
        this.pack = pack;
        editingAlias = alias;
    }

    @Override
    public void init()
    {
        super.init();

        lblName = new Label("Name:", 7, 7, this);
        addControl(lblName);

        tbName = new TextBox(7, 17, 166, 20, this);
        if (editingAlias != null)
        {
            tbName.setText(editingAlias.name);
            tbName.setEnabled(false);
        }
        else
        {
            tbName.setValidityProvider(this);
        }
        addControl(tbName);

        lblItem = new Label("Item:", 7, 42, this);
        addControl(lblItem);

        itemDisplay = new ItemDisplay(7, 52, this);
        itemDisplay.setDrawSlotBackground();
        if (editingAlias != null)
            itemDisplay.setItemStack(editingAlias.newItemStack());
        addControl(itemDisplay);

        updateButton(tbName.hasValidText());
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem());
        }
        else if (c == btnCreate)
        {
            Item item = itemDisplay.getItemStack().getItem();
            int damageValue = itemDisplay.getItemStack().getItemDamage();
            String name = tbName.getText().trim();
            Alias alias = new Alias(item, damageValue, name, pack);
            alias.apply();
            GuiBase.openPrevWindow();
        }
        else if (c == btnEdit)
        {
            editingAlias.item = itemDisplay.getItemStack().getItem();
            editingAlias.damageValue = itemDisplay.getItemStack().getItemDamage();
            editingAlias.name = tbName.getText().trim();
            editingAlias.edit();
            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private void updateButton(boolean tbValidity)
    {
        if (editingAlias == null)
        {
            btnCreate.setEnabled(tbValidity && itemDisplay.getItemStack() != null);
        }
        else
        {
            btnEdit.setEnabled(tbValidity && itemDisplay.getItemStack() != null);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tbName.getText().trim();
        if (text.length() == 0)
        {
            message = "Enter a name.";
        }
        else if (text.contains(" ") || text.contains("\t"))
        {
            message = "Name may not contain spaces or tabs.";
        }
        else
        {
            for (Alias alias : pack.getContentManager(Alias.class).getContentList())
            {
                if (alias.name.equals(text))
                {
                    message = "There is already an alias with this name.";
                    break;
                }
            }
        }

        updateButton(message == null);

        return message;
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());
        updateButton(tbName.hasValidText());
    }
}
