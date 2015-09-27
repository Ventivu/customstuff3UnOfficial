package cubex2.cs3.ingame.gui.tileentity;

import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;
import cubex2.cs3.tileentity.data.FurnaceModule;

public class WindowEditOrCreateFurnaceModule extends Window
{
    private FurnaceModule editingModule;
    private WrappedTileEntity tile;

    private TextBox tbName;
    private NumericUpDown nupInput;
    private NumericUpDown nupOutput;
    private NumericUpDown nupFuel;
    private NumericUpDown nupCookTime;

    public WindowEditOrCreateFurnaceModule(WrappedTileEntity tile, FurnaceModule module)
    {
        super("Edit Furnace Module", EDIT | CANCEL, 150, 220);
        this.tile = tile;
        editingModule = module;
        initControls();
    }

    public WindowEditOrCreateFurnaceModule(WrappedTileEntity tile)
    {
        super("New Furnace Module", CREATE | CANCEL, 150, 220);
        this.tile = tile;
        initControls();
    }

    private void initControls()
    {
        row("Name:");
        tbName = row(textBox());
        row("Input Slot:");
        nupInput = row(numericUpDown());
        row("Output Slot:");
        nupOutput = row(numericUpDown());
        row("Fuel Slot:");
        nupFuel = row(numericUpDown());
        row("Cook Time:");
        nupCookTime = row(numericUpDown());

        tbName.setValidityProvider(new NameValidator(tile, editingModule != null ? editingModule.name : null));
        nupCookTime.setValue(200);

        if (editingModule != null)
        {
            tbName.setText(editingModule.name);
            nupInput.setValue(editingModule.inputSlot);
            nupOutput.setValue(editingModule.outputSlot);
            nupFuel.setValue(editingModule.fuelSlot);
            nupCookTime.setValue(editingModule.cookTime);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnEdit)
        {
            editingModule.name = tbName.getText();
            editingModule.inputSlot = nupInput.getValue();
            editingModule.outputSlot = nupOutput.getValue();
            editingModule.fuelSlot = nupFuel.getValue();
            editingModule.cookTime = nupCookTime.getValue();

            tile.getPack().save();
            GuiBase.openPrevWindow();
        } else if (c == btnCreate)
        {
            FurnaceModule module = new FurnaceModule(tbName.getText(), nupInput.getValue(), nupOutput.getValue(), nupFuel.getValue(), nupCookTime.getValue());
            ((TileEntityInventoryAttributes) tile.container).furnaceModules.list.add(module);

            tile.getPack().save();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    public static class NameValidator implements IValidityProvider
    {
        private TileEntityInventoryAttributes container;
        private String current;

        public NameValidator(WrappedTileEntity tile, String current)
        {
            container = (TileEntityInventoryAttributes) tile.container;
            this.current = current;
        }

        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            String text = tb.getText().trim();
            if (text.length() == 0)
                message = "Enter something";
            else if (current == null || !text.equals(current))
            {
                for (FurnaceModule module : container.furnaceModules.list)
                {
                    if (module.name.equals(text))
                    {
                        message = "That name is already used.";
                        break;
                    }
                }
            }

            return message;
        }
    }

    ;
}
