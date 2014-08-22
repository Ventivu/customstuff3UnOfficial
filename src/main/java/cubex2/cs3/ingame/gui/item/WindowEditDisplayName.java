package cubex2.cs3.ingame.gui.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.TextBox;

import java.util.Map;
import java.util.Properties;

public class WindowEditDisplayName extends Window implements IValidityProvider
{
    private WrappedItem wrappedItem;

    private TextBox textBox;

    public WindowEditDisplayName(WrappedItem item)
    {
        super("displayName", EDIT | CANCEL, 150, 60);
        this.wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        textBox = textBox().at(7, 7).height(16).fillWidth(7).add();
        textBox.setText(wrappedItem.container.displayName);
        textBox.setValidityProvider(this);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedItem.container.displayName = textBox.getText().trim();
            wrappedItem.getPack().save();

            Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
            Properties p = modLangData.get("en_US");
            p.put("item." + wrappedItem.getName() + ".name", wrappedItem.container.displayName);

            mc.refreshResources();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tb.getText().trim();
        if (text.length() == 0)
            message = "Enter something";

        btnEdit.setEnabled(message == null);
        return message;
    }
}
