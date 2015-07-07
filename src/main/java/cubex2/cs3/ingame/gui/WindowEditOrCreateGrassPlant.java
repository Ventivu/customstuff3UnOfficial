package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.GrassPlant;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.Validators;

public class WindowEditOrCreateGrassPlant extends Window implements IWindowClosedListener<WindowSelectBlock>
{
    private final BaseContentPack pack;
    private GrassPlant editingPlant;

    private ItemDisplay blockDisplay;
    private NumericUpDown nupWeight;

    public WindowEditOrCreateGrassPlant(BaseContentPack pack)
    {
        super("New Grass Plant", CREATE | CANCEL, 180, 103);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateGrassPlant(GrassPlant editingPlant, BaseContentPack pack)
    {
        super("Edit Grass Plant", EDIT | CANCEL, 180, 103);
        this.pack = pack;
        this.editingPlant = editingPlant;

        initControls();
    }

    private void initControls()
    {
        row("Block:");
        blockDisplay = row(itemDisplay());
        blockDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        if (editingPlant == null)
            blockDisplay.useSelectBlockDialog();
        else
            blockDisplay.setItemStack(editingPlant.block);
        blockDisplay.setDrawSlotBackground();

        row("Weight:", Strings.INFO_GRASS_PLANT_WEIGHT);
        nupWeight = row(numericUpDown());
        nupWeight.setMinValue(1);
        nupWeight.setValue(editingPlant == null ? 1 : editingPlant.weight);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            GrassPlant plant = new GrassPlant(blockDisplay.getItemStack(), nupWeight.getValue(), pack);
            plant.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingPlant.weight = nupWeight.getValue();
            editingPlant.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowSelectBlock window)
    {
        if (window.getSelectedStack() != null)
        {
            blockDisplay.setItemStack(window.getSelectedStack());
        }
    }
}
