package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateFuel extends Window implements IWindowClosedListener<WindowSelectItem>
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

        lblItem = label("Item:").at(7,7).add();
        itemDisplay = itemDisplay().below(lblItem).add();
        lblDuration = label("Duration:").below(itemDisplay, 5).add();
        tbDuration = textBox().below(lblDuration).fillWidth(7).height(20).add();

        itemDisplay.setDrawSlotBackground();
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        if (editingFuel != null)
            itemDisplay.setItemStack(editingFuel.stack);

        if (editingFuel != null)
        {
            tbDuration.setText(String.valueOf(editingFuel.duration));
        }
        tbDuration.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
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

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());
    }
}
