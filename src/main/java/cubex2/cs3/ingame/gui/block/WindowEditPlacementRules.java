package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.CheckBox;

public class WindowEditPlacementRules extends WindowEditBlockAttribute
{
    private CheckBox cbPlaceOnFloor;
    private CheckBox cbPlaceOnCeiling;
    private CheckBox cbPlaceOnWall;

    public WindowEditPlacementRules(WrappedBlock block)
    {
        super(block, "placement", 150, 100);

        cbPlaceOnFloor = checkBox().checked(wrappedBlock.container.canPlaceOnFloor).at(7, 7).add();
        label("Can place on floor").rightTo(cbPlaceOnFloor).add();

        cbPlaceOnCeiling = checkBox().checked(wrappedBlock.container.canPlaceOnCeiling).below(cbPlaceOnFloor, 7).add();
        label("Can place on ceiling").rightTo(cbPlaceOnCeiling).add();

        cbPlaceOnWall = checkBox().checked(wrappedBlock.container.canPlaceOnWall).below(cbPlaceOnCeiling, 7).add();
        label("Can place on wall").rightTo(cbPlaceOnWall).add();
    }

    @Override
    protected void applyChanges()
    {
        wrappedBlock.container.canPlaceOnFloor = cbPlaceOnFloor.getIsChecked();
        wrappedBlock.container.canPlaceOnCeiling = cbPlaceOnCeiling.getIsChecked();
        wrappedBlock.container.canPlaceOnWall = cbPlaceOnWall.getIsChecked();
    }
}
