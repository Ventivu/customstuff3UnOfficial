package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IPlayerDisplayPlayerModifier;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditFull3D extends Window implements IPlayerDisplayPlayerModifier
{
    private WrappedItem wrappedItem;

    private PlayerDisplay display;
    private CheckBox checkBox;

    private boolean oldFull3D;
    private boolean newFull3D;

    public WindowEditFull3D(WrappedItem item)
    {
        super("full3d", EDIT | CANCEL, 150, 120);
        wrappedItem = item;
        oldFull3D = item.container.full3d;
        newFull3D = item.container.full3d;
    }

    @Override
    public void init()
    {
        super.init();

        display = playerDisplay().at(7,7).size(50, 80).add();
        display.setPlayerModifier(this);
        display.setEquippedStack(new ItemStack(wrappedItem.item));
        checkBox = checkBox().rightTo(display).add();
        checkBox.setIsChecked(newFull3D);

        label("Draw full 3D").rightTo(checkBox).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == checkBox)
        {
            newFull3D = checkBox.getIsChecked();
        }
        else if (c == btnEdit)
        {
            wrappedItem.container.full3d = newFull3D;
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
        wrappedItem.container.full3d = newFull3D;
    }

    @Override
    public void postRender(PlayerDisplay display)
    {
        wrappedItem.container.full3d = oldFull3D;
    }
}
