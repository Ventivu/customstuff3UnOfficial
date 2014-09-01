package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditInteger extends Window
{
    protected AttributeContainer container;
    private String fieldName;

    private TextBox textBox;

    public WindowEditInteger(String fieldName, AttributeContainer container)
    {
        super(fieldName, EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = fieldName;
    }

    @Override
    public void init()
    {
        super.init();

        textBox = textBox().y(7).fillWidth(7).add();
        textBox.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        textBox.setText(String.valueOf(container.getAttribute(fieldName)));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnEdit)
        {
            container.setAttriubte(fieldName, Integer.parseInt(textBox.getText()));
            applyChangedValue();
            container.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    protected void applyChangedValue()
    {

    }
}
