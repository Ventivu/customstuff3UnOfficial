package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
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
        super(null, EDIT | CANCEL, Math.min(GuiBase.INSTANCE.width, 504), Math.min(GuiBase.INSTANCE.height, 504));
        this.scriptName = scriptName;
        this.container = container;
        scriptWrapper = container.getAttribute(scriptName);
        if (scriptWrapper == null)
        {
            scriptWrapper = new ScriptWrapper("");
        }

        textField = textField().top(7).bottom(34).fillWidth(7).add();
        textField.setText(scriptWrapper.getSource());

        lblError = label("Script has syntax errors!").rightTo(btnEdit).add();
        lblError.setColor(Color.RED);
        lblError.setVisible(false);
    }

    @Override
    public void onParentResized()
    {
        width = Math.min(GuiBase.INSTANCE.width, 504);
        height = Math.min(GuiBase.INSTANCE.height, 504);

        super.onParentResized();
    }

    @Override
    protected void handleEditButtonClicked()
    {
        boolean hasErrors = false;
        try
        {
            scriptWrapper.setSource(textField.getText());
        } catch (Exception e)
        {
            hasErrors = true;
        }

        if (!hasErrors)
        {
            container.setAttriubte(scriptName, scriptWrapper);
            container.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            lblError.setVisible(true);
        }
    }
}
