package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.attribute.AttributeData;
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

    private int minValue = 0;
    private int maxValue = Integer.MAX_VALUE;

    public WindowEditInteger(AttributeData attributeData, AttributeContainer container)
    {
        super(attributeData.getDisplayName(), EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = attributeData.field.getName();

        String info = attributeData.attribute.additionalInfo();
        if (info.length() > 0)
        {
            minValue = Integer.parseInt(info.split("-")[0]);
            maxValue = Integer.parseInt(info.split("-")[1]);
        }
    }

    public WindowEditInteger(String fieldName, int min, int max, AttributeContainer container)
    {
        super(fieldName, EDIT | CANCEL, 150, 55);
        this.container = container;
        this.fieldName = fieldName;
        minValue = min;
        maxValue = max;
    }

    @Override
    public void init()
    {
        super.init();

        textBox = textBox().y(7).fillWidth(7).add();
        textBox.setValidityProvider(new TextBoxValidators.IntegerValidator(minValue, maxValue));
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
