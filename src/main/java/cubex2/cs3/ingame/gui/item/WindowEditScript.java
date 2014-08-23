package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.TextField;
import cubex2.cs3.util.ScriptWrapper;

public class WindowEditScript extends Window
{
    private WrappedItem wrappedItem;
    private ScriptWrapper scriptWrapper;
    private String scriptName;

    private TextField textField;


    public WindowEditScript(String scriptName, WrappedItem item)
    {
        super(scriptName, EDIT | CANCEL, 300, 200);
        this.scriptName = scriptName;
        wrappedItem = item;
        scriptWrapper = item.container.getAttribute(scriptName);
        if (scriptWrapper == null)
        {
            scriptWrapper = new ScriptWrapper("");
        }
    }

    @Override
    public void init()
    {
        super.init();

        textField = textField().y(7).fillWidth(7).height(170).add();
        textField.setText(scriptWrapper.getSource());
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnEdit)
        {
            scriptWrapper.setSource(textField.getText());
            wrappedItem.container.setAttriubte(scriptName, scriptWrapper);
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
