package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;

public class WindowEditInteger extends Window
{
    private WrappedItem wrappedItem;
    private String fieldName;

    private TextBox textBox;

    public WindowEditInteger(String fieldName, WrappedItem item)
    {
        super(fieldName, EDIT | CANCEL, 150, 55);
        wrappedItem = item;
        this.fieldName = fieldName;
    }

    @Override
    public void init()
    {
        super.init();

        textBox = textBox().y(7).fillWidth(7).add();
        textBox.setValidityProvider(TextBoxValidators.POSITIVE_INTEGER);
        textBox.setText(String.valueOf(wrappedItem.container.getAttribute(fieldName)));
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnEdit)
        {
            wrappedItem.container.setAttriubte(fieldName, Integer.parseInt(textBox.getText()));
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
