package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditExpDrop extends WindowEditBlockAttribute
{
    private TextBox tbMin;
    private TextBox tbMax;

    public WindowEditExpDrop(WrappedBlock block)
    {
        super(block, "expDrop", 150, 95);

        Label lblMin = label("Min").at(7, 7).add();

        tbMin = textBox().below(lblMin).fillWidth(7).add();
        tbMin.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        tbMin.setText(String.valueOf(container.expDropMin));

        Label lblMax = label("Max").below(tbMin).add();

        tbMax = textBox().below(lblMax).fillWidth(7).add();
        tbMax.setValidityProvider(new TextBoxValidators.MinMaxValidator(tbMin));
        tbMax.setText(String.valueOf(container.expDropMax));
    }

    @Override
    protected void applyChanges()
    {
        container.expDropMin = Integer.parseInt(tbMin.getText());
        container.expDropMax = Integer.parseInt(tbMax.getText());
    }
}
