package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditFloat extends Window
{
    protected AttributeContainer container;
    private String fieldName;

    private TextBox textBox;

    public WindowEditFloat(String fieldName, AttributeContainer container)
    {
        super(fieldName, EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = fieldName;

        textBox = textBox().top(7).fillWidth(7).add();
        textBox.setValidityProvider(TextBoxValidators.FLOAT);
        textBox.setText(String.valueOf(container.getAttribute(fieldName)));
    }

    @Override
    protected void handleEditButtonClicked()
    {
        container.setAttribute(fieldName, Float.parseFloat(textBox.getText()));
        applyChangedValue();
        container.getPack().save();

        GuiBase.openPrevWindow();
    }

    protected void applyChangedValue()
    {

    }
}
