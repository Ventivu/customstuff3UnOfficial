package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;

public class WindowEditBoolean extends Window
{
    protected AttributeContainer container;
    private String fieldName;

    private CheckBox checkBox;

    private String text;

    public WindowEditBoolean(AttributeData attributeData, AttributeContainer container)
    {
        super(attributeData.getDisplayName(), EDIT | CANCEL, 150, 70);
        this.container = container;
        this.fieldName = attributeData.field.getName();

        text = attributeData.attribute.additionalInfo();

        checkBox = checkBox().centerVert(-16).left(7).add();
        checkBox.setIsChecked((Boolean)container.getAttribute(fieldName));

        if (text != null && !text.isEmpty())
        {
            label(text).rightTo(checkBox).add();
        }
    }

    @Override
    protected void handleEditButtonClicked()
    {
        container.setAttriubte(fieldName, checkBox.getIsChecked());
        applyChangedValue();
        container.getPack().save();

        GuiBase.openPrevWindow();
    }

    protected void applyChangedValue()
    {

    }
}
