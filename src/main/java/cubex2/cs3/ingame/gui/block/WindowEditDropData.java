package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.util.BlockDrop;

public class WindowEditDropData extends WindowEditBlockAttribute
{
    private BlockDrop.DropData drop;

    private TextBox tbMin;
    private TextBox tbMax;

    public WindowEditDropData(WrappedBlock block, BlockDrop.DropData drop)
    {
        super(block, "Edit drop", 150, 95);
        this.drop = drop;

        Label lblMin = label("Min").at(7, 7).add();

        tbMin = textBox().below(lblMin).fillWidth(7).add();
        tbMin.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        tbMin.setText(String.valueOf(drop.getMinCount()));

        Label lblMax = label("Max").below(tbMin).add();

        tbMax = textBox().below(lblMax).fillWidth(7).add();
        tbMax.setValidityProvider(new TextBoxValidators.MinMaxValidator(tbMin));
        tbMax.setText(String.valueOf(drop.getMaxCount()));
    }

    @Override
    protected void applyChanges()
    {
        drop.setAmount(Integer.parseInt(tbMin.getText()), Integer.parseInt(tbMax.getText()));
    }
}
