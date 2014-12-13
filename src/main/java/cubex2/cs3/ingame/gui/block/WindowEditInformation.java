package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.TextField;
import net.minecraft.item.ItemStack;

public class WindowEditInformation extends WindowEditBlockAttribute
{
    private TextField textField;
    private ItemDisplay display;

    public WindowEditInformation(WrappedBlock block)
    {
        super(block, "information", EDIT | BACK, 150, 170);

        textField = textField().top(7).fillWidth(7).height(100).add();
        textField.disableSyntaxHighlighting();
        textField.setText(wrappedBlock.container.information);
        display = itemDisplay().below(textField).add();
        display.setItemStack(new ItemStack(wrappedBlock.block));
        display.setDrawSlotBackground();
    }

    @Override
    protected void applyChanges()
    {
        wrappedBlock.container.information = getNewInfo();
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
        String currentInfo = wrappedBlock.container.information;
        wrappedBlock.container.information = getNewInfo();
        super.drawForeground(mouseX, mouseY);
        wrappedBlock.container.information = currentInfo;
    }
}
