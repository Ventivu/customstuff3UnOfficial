package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateFuel extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private BaseContentPack pack;
    private Fuel editingFuel;

    private Label lblItem;
    private Label lblDuration;
    private ItemDisplay itemDisplay;
    private NumericUpDown nupDuration;

    public WindowEditOrCreateFuel(BaseContentPack pack)
    {
        super("New Fuel", CREATE | CANCEL, 180, 100);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateFuel(Fuel fuel, BaseContentPack pack)
    {
        super("Edit Fuel", EDIT | CANCEL, 180, 100);
        this.pack = pack;
        editingFuel = fuel;

        initControls();
    }

    private void initControls()
    {
        lblItem = label("Item:").at(7, 7).add();
        itemDisplay = itemDisplay().below(lblItem).add();
        lblDuration = label("Duration:").below(itemDisplay, 5).add();
        nupDuration = numericUpDown().below(lblDuration).fillWidth(7).add();

        itemDisplay.setDrawSlotBackground();
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        if (editingFuel != null)
            itemDisplay.setItemStack(editingFuel.stack);

        if (editingFuel != null)
        {
            nupDuration.setValue(editingFuel.duration);
        }

        if (editingFuel == null)
            btnCreate.setEnabled(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem());
        } else if (c == btnCreate)
        {
            ItemStack stack = itemDisplay.getItemStack();
            int duration = nupDuration.getValue();
            Fuel fuel = new Fuel(stack, duration, pack);
            fuel.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingFuel.stack = itemDisplay.getItemStack();
            editingFuel.duration = nupDuration.getValue();
            editingFuel.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());

        btnCreate.setEnabled(itemDisplay.getItemStack() != null);
    }
}
