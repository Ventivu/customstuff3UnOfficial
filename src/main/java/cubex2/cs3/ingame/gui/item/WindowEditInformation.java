package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.TextField;
import net.minecraft.item.ItemStack;

public class WindowEditInformation extends Window
{
    private WrappedItem wrappedItem;

    private TextField textField;
    private ItemDisplay display;

    public WindowEditInformation(WrappedItem item)
    {
        super("information", EDIT | BACK, 150, 170);
        wrappedItem = item;
    }

    @Override
    public void init()
    {
        super.init();

        textField = textField().y(7).fillWidth(7).height(100).add();
        textField.disableSyntaxHighlighting();
        textField.setText(wrappedItem.container.information);
        display = itemDisplay().below(textField).add();
        display.setItemStack(new ItemStack(wrappedItem.item));
        display.setDrawSlotBackground();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnEdit)
        {
            wrappedItem.container.information = getNewInfo();
            wrappedItem.getPack().save();

            GuiBase.openPrevWindow();
        }
        else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private String getNewInfo()
    {
        String info = textField.getText();
        if (info.length() == 0)
            info = null;
        return info;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        String currentInfo = wrappedItem.container.information;
        wrappedItem.container.information = getNewInfo();
        super.drawForeground(mouseX, mouseY);
        wrappedItem.container.information = currentInfo;
    }
}
