package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.IPlayerDisplayPlayerModifier;
import cubex2.cs3.ingame.gui.control.PlayerDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditFull3D extends WindowEditItemAttribute implements IPlayerDisplayPlayerModifier
{
    private PlayerDisplay display;
    private CheckBox checkBox;

    private boolean oldFull3D;
    private boolean newFull3D;

    public WindowEditFull3D(WrappedItem item)
    {
        super(item, "full3d", 150, 120);
        oldFull3D = item.container.full3d;
        newFull3D = item.container.full3d;

        display = playerDisplay().at(7, 7).size(50, 80).add();
        display.setPlayerModifier(this);
        display.setEquippedStack(new ItemStack(wrappedItem.item));
        checkBox = checkBox().rightTo(display).add();
        checkBox.setIsChecked(newFull3D);

        label("Draw full 3D").rightTo(checkBox).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == checkBox)
        {
            newFull3D = checkBox.getIsChecked();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        wrappedItem.container.full3d = newFull3D;
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
