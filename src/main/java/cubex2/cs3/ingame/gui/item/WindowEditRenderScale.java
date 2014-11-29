package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IPlayerDisplayPlayerModifier;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.TextBoxValidators;
import net.minecraft.item.ItemStack;

public class WindowEditRenderScale extends Window implements IPlayerDisplayPlayerModifier
{
    private WrappedItem wrappedItem;

    private PlayerDisplay display;
    private TextBox tbScale;

    private float oldScale;
    private float newScale;

    public WindowEditRenderScale(WrappedItem item)
    {
        super("renderScale", EDIT | CANCEL, 150, 120);
        wrappedItem = item;
        oldScale = item.container.renderScale;
        newScale = item.container.renderScale + 0.5f;
    }

    @Override
    public void init()
    {
        super.init();

        display = playerDisplay().at(7,7).size(50,80).add();
        display.setPlayerModifier(this);
        display.setEquippedStack(new ItemStack(wrappedItem.item));

        tbScale = textBox().rightTo(display).width(150 - 14- 50).add();
        tbScale.setText(String.valueOf(newScale));
        tbScale.setValidityProvider(TextBoxValidators.FLOAT);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);

        if (tbScale.hasValidValue())
        {
            newScale = Float.parseFloat(tbScale.getText());
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedItem.container.renderScale = newScale;
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public void preRender(PlayerDisplay display)
    {
        wrappedItem.container.renderScale = newScale;
    }

    @Override
    public void postRender(PlayerDisplay display)
    {
        wrappedItem.container.renderScale = oldScale;
    }
}
