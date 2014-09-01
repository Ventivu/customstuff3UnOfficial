package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.attribute.Attribute;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.ingame.gui.control.TextField;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.ScriptWrapper;

public class WindowEditScript extends Window
{
    private AttributeContainer container;
    private ScriptWrapper scriptWrapper;
    private String scriptName;

    private TextField textField;
    private Label lblError;


    public WindowEditScript(String scriptName, AttributeContainer container)
    {
        super(null, EDIT | CANCEL, Math.min(GuiBase.instance.width, 504), Math.min(GuiBase.instance.height, 504));
        this.scriptName = scriptName;
        this.container = container;
        scriptWrapper = container.getAttribute(scriptName);
        if (scriptWrapper == null)
        {
            scriptWrapper = new ScriptWrapper("");
        }
    }

    @Override
    public void updateRect()
    {
        width = Math.min(GuiBase.instance.width, 504);
        height = Math.min(GuiBase.instance.height, 504);

        super.updateRect();
    }

    @Override
    protected void adjustSize(Control c)
    {
        if (c == textField)
        {
            textField.width = getWidth() - 14;
            textField.height = getHeight() - 34;
        }
    }

    @Override
    public void init()
    {
        super.init();

        textField = textField().y(7).fillWidth(7).height(getHeight()- 34).add();
        textField.setText(scriptWrapper.getSource());

        lblError = label("Script has syntax errors!").rightTo(btnEdit).add();
        lblError.setColor(Color.RED);
        lblError.setVisible(false);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnEdit)
        {
            boolean hasErrors = false;
            try
            {
                scriptWrapper.setSource(textField.getText());
            } catch (Exception e)
            {
                hasErrors = true;
            }

            if(!hasErrors)
            {
                container.setAttriubte(scriptName, scriptWrapper);
                container.getPack().save();

                GuiBase.openPrevWindow();
            }
            else
            {
                lblError.setVisible(true);
            }
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }
}
