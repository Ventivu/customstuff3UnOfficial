package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.gui.data.ShiftClickRule;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.NumericUpDown;

public class WindowEditOrCreateShiftClickRule extends Window
{
    private ShiftClickRule editingRule;
    private WrappedGui gui;

    private CheckBox cbFromInv;
    private CheckBox cbToInv;
    private NumericUpDown nbFromStart;
    private NumericUpDown nbFromEnd;
    private NumericUpDown nbToStart;
    private NumericUpDown nbToEnd;
    private CheckBox cbFuelOnly;
    private CheckBox cbInputOnly;

    public WindowEditOrCreateShiftClickRule(WrappedGui gui, ShiftClickRule rule)
    {
        super("Edit Shift-Click Rule", EDIT | CANCEL, 150, 230);
        this.gui = gui;
        editingRule = rule;
        initControls();
    }

    public WindowEditOrCreateShiftClickRule(WrappedGui gui)
    {
        super("New Shift-Click Rule", CREATE | CANCEL, 150, 230);
        this.gui = gui;
        initControls();
    }

    private void initControls()
    {
        cbFromInv = row(checkBox("From Player Inventory"));
        row("From Start:");
        nbFromStart = row(numericUpDown());
        row("From End:");
        nbFromEnd = row(numericUpDown());
        cbToInv = row(checkBox("To Player Inventory"));
        row("To Start:");
        nbToStart = row(numericUpDown());
        row("To End:");
        nbToEnd = row(numericUpDown());
        cbFuelOnly = row(checkBox("Fuel Only"));
        cbInputOnly = row(checkBox("Furnace Input Only"));

        if (editingRule != null)
        {
            cbFromInv.setIsChecked(editingRule.fromInv);
            nbFromStart.setValue(editingRule.fromStart);
            nbFromEnd.setValue(editingRule.fromEnd);
            cbToInv.setIsChecked(editingRule.toInv);
            nbToStart.setValue(editingRule.toStart);
            nbToEnd.setValue(editingRule.toEnd);
            cbFuelOnly.setIsChecked(editingRule.fuelOnly);
            cbInputOnly.setIsChecked(editingRule.furnaceInputOnly);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnEdit)
        {
            editingRule.fromInv = cbFromInv.getIsChecked();
            editingRule.fromStart = nbFromStart.getValue();
            editingRule.fromEnd = nbFromEnd.getValue();
            editingRule.toInv = cbToInv.getIsChecked();
            editingRule.toStart = nbToStart.getValue();
            editingRule.toEnd = nbToEnd.getValue();
            editingRule.fuelOnly = cbFuelOnly.getIsChecked();
            editingRule.furnaceInputOnly = cbInputOnly.getIsChecked();

            gui.getPack().save();
            GuiBase.openPrevWindow();
        } else if (c == btnCreate)
        {
            ShiftClickRule rule = new ShiftClickRule(cbFromInv.getIsChecked(), nbFromStart.getValue(), nbFromEnd.getValue(),
                    cbToInv.getIsChecked(), nbToStart.getValue(), nbToEnd.getValue(), cbFuelOnly.getIsChecked(), cbInputOnly.getIsChecked());
            ((GuiContainerAttributes) gui.container).shiftClickRules.list.add(rule);

            gui.getPack().save();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
