package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.util.ValidateHelper;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateFuel extends Window implements IValidityProvider, IWindowClosedListener<WindowSelectItem>
{
    private IngameContentPack pack;
    private Fuel editingFuel;

    private Label lblItem;
    private Label lblDuration;
    private ItemDisplay itemDisplay;
    private TextBox tbDuration;

    public WindowEditOrCreateFuel(IngameContentPack pack)
    {
        super("New Fuel", CREATE | CANCEL, 180, 100);
        this.pack = pack;
    }

    public WindowEditOrCreateFuel(Fuel fuel, IngameContentPack pack)
    {
        super("Edit Fuel", EDIT | CANCEL, 180, 100);
        this.pack = pack;
        editingFuel = fuel;
    }

    @Override
    public void init()
    {
        super.init();

        lblItem = new Label("Item:", 7, 7, this);
        addControl(lblItem);

        itemDisplay = new ItemDisplay(7, 17, this);
        itemDisplay.setDrawSlotBackground();
        if (editingFuel != null)
            itemDisplay.setItemStack(editingFuel.stack);
        addControl(itemDisplay);

        lblDuration = new Label("Duration:", 7, 37, this);
        addControl(lblDuration);

        tbDuration = new TextBox(7, 47, 166, 20, this);
        if (editingFuel != null)
        {
            tbDuration.setText(String.valueOf(editingFuel.duration));
        }
        tbDuration.setValidityProvider(this);
        addControl(tbDuration);

        updateButton(tbDuration.hasValidText());
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem());
        } else if (c == btnCreate)
        {
            ItemStack stack = itemDisplay.getItemStack();
            int duration = Integer.valueOf(tbDuration.getText().trim());
            Fuel fuel = new Fuel(stack, duration, pack);
            fuel.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingFuel.stack = itemDisplay.getItemStack();
            editingFuel.duration = Integer.valueOf(tbDuration.getText().trim());
            editingFuel.edit();
            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private void updateButton(boolean tbValidity)
    {
        if (editingFuel == null)
        {
            btnCreate.setEnabled(tbValidity && itemDisplay.getItemStack() != null);
        } else
        {
            btnEdit.setEnabled(tbValidity && itemDisplay.getItemStack() != null);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tbDuration.getText().trim();
        if (text.length() == 0)
        {
            message = "Enter a value.";
        } else if (!ValidateHelper.isValidIntegerString(text))
        {
            message = "Enter a valid number.";
        }

        updateButton(message == null);

        return message;
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());
        updateButton(tbDuration.hasValidText());
    }
}
