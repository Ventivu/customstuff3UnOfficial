package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;

public class WindowEditPlacementRules extends Window
{
    private final WrappedBlock wrappedBlock;

    private CheckBox cbPlaceOnFloor;
    private CheckBox cbPlaceOnCeiling;
    private CheckBox cbPlaceOnWall;

    public WindowEditPlacementRules(WrappedBlock block)
    {
        super("placement", EDIT | CANCEL, 150, 100);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        cbPlaceOnFloor = checkBox().checked(wrappedBlock.container.canPlaceOnFloor).at(7, 7).add();
        label("Can place on floor").rightTo(cbPlaceOnFloor).add();

        cbPlaceOnCeiling = checkBox().checked(wrappedBlock.container.canPlaceOnCeiling).below(cbPlaceOnFloor, 7).add();
        label("Can place on ceiling").rightTo(cbPlaceOnCeiling).add();

        cbPlaceOnWall = checkBox().checked(wrappedBlock.container.canPlaceOnWall).below(cbPlaceOnCeiling, 7).add();
        label("Can place on wall").rightTo(cbPlaceOnWall).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedBlock.container.canPlaceOnFloor = cbPlaceOnFloor.getIsChecked();
            wrappedBlock.container.canPlaceOnCeiling = cbPlaceOnCeiling.getIsChecked();
            wrappedBlock.container.canPlaceOnWall = cbPlaceOnWall.getIsChecked();
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
