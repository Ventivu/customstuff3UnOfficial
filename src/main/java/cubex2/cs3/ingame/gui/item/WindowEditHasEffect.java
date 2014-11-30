package cubex2.cs3.ingame.gui.item;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class WindowEditHasEffect extends WindowEditItemAttribute
{
    private CheckBox checkBox;
    private ItemDisplay itemDisplay;

    private boolean oldHasEffect;
    private boolean newHasEffect;

    public WindowEditHasEffect(WrappedItem item)
    {
        super(item, "hasEffect", 150, 100);
        oldHasEffect = newHasEffect = wrappedItem.container.hasEffect;
    }

    @Override
    public void init()
    {
        super.init();

        checkBox = checkBox().at(7, 7).add();
        checkBox.setIsChecked(newHasEffect);
        label("Has effect").rightTo(checkBox).add();

        itemDisplay = itemDisplay().below(checkBox).add();
        itemDisplay.setItemStack(new ItemStack(wrappedItem.item));
        itemDisplay.setDrawSlotBackground();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == checkBox)
        {
            newHasEffect = checkBox.getIsChecked();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    protected void applyChanges()
    {
        wrappedItem.container.hasEffect = newHasEffect;
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        wrappedItem.container.hasEffect = newHasEffect;
        super.draw(mouseX, mouseY, renderTick);
        wrappedItem.container.hasEffect = oldHasEffect;
    }
}
