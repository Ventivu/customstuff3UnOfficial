package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditExpDrop extends Window
{
    private WrappedBlock wrappedBlock;

    private TextBox tbMin;
    private TextBox tbMax;

    public WindowEditExpDrop(WrappedBlock block)
    {
        super("expDrop", EDIT | CANCEL, 150, 95);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        Label lblMin = label("Min").at(7, 7).add();

        tbMin = textBox().below(lblMin).fillWidth(7).add();
        tbMin.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        tbMin.setText(String.valueOf(wrappedBlock.container.expDropMin));

        Label lblMax = label("Max").below(tbMin).add();

        tbMax = textBox().below(lblMax).fillWidth(7).add();
        tbMax.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        tbMax.setText(String.valueOf(wrappedBlock.container.expDropMax));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedBlock.container.expDropMin = Integer.parseInt(tbMin.getText());
            wrappedBlock.container.expDropMax = Integer.parseInt(tbMax.getText());
            wrappedBlock.container.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
