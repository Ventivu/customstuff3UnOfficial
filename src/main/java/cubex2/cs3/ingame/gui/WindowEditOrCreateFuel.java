package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateFuel extends WindowEditOrCreate<Fuel> implements IWindowClosedListener<WindowSelectItem>
{
    private ItemDisplay itemDisplay;
    private NumericUpDown nupDuration;

    public WindowEditOrCreateFuel(BaseContentPack pack)
    {
        super("New Fuel", 180, 100, pack);
    }

    public WindowEditOrCreateFuel(Fuel fuel, BaseContentPack pack)
    {
        super("Edit Fuel", 180, 100, fuel, pack);
    }

    @Override
    protected void initControls()
    {
        row("Item:");
        itemDisplay = row(itemDisplay());
        row("Duration:", Strings.INFO_FUEL_DURATION);
        nupDuration = row(numericUpDown());
        nupDuration.setValue(300);

        itemDisplay.setDrawSlotBackground();
        itemDisplay.useSelectItemDialog(false);
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        if (editingContent != null)
            itemDisplay.setItemStack(editingContent.stack);

        if (editingContent != null)
        {
            nupDuration.setValue(editingContent.duration);
        }
    }

    @Override
    protected Fuel createContent()
    {
        ItemStack stack = itemDisplay.getItemStack();
        int duration = nupDuration.getValue();
        return new Fuel(stack, duration, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.stack = itemDisplay.getItemStack();
        editingContent.duration = nupDuration.getValue();
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());

        btnCreate.setEnabled(itemDisplay.getItemStack() != null);
    }
}
