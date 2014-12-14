package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Strings;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditHarvesting extends WindowEditBlockAttribute
{
    private TextBox tbToolClass;
    private TextBox tbHarvestLevel;

    public WindowEditHarvesting(WrappedBlock block)
    {
        super(block, "harvesting", 150, 100);

        Label lblToolClass = label("Tool class:").at(7, 7).add();
        infoButton(Strings.INFO_TOOL_CLASS).rightTo(lblToolClass).add();

        tbToolClass = textBox().top(lblToolClass, 2).fillWidth(7).add();
        if (block.container.toolClass != null)
            tbToolClass.setText(block.container.toolClass);

        Label lblHarvestLevel = label("Harvest level:").below(tbToolClass).add();
        infoButton(Strings.INFO_HARVEST_LEVEL).rightTo(lblHarvestLevel).add();

        tbHarvestLevel = textBox().top(lblHarvestLevel, 2).fillWidth(7).add();
        tbHarvestLevel.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        tbHarvestLevel.setText(String.valueOf(block.container.harvestLevel));
    }

    @Override
    protected void applyChanges()
    {
        String toolClass = tbToolClass.getText().length() != 0 ? tbToolClass.getText() : null;
        wrappedBlock.container.toolClass = toolClass;
        wrappedBlock.container.harvestLevel = toolClass != null ? Integer.parseInt(tbHarvestLevel.getText()) : 0;

        wrappedBlock.block.setHarvestLevel(wrappedBlock.container.toolClass, wrappedBlock.container.harvestLevel);
    }
}
