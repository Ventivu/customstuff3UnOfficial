package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ItemDisplay;

public class ItemDisplayBuilder extends ControlBuilder<ItemDisplay>
{
    public ItemDisplayBuilder(ControlContainer c)
    {
        super(c);
        width = 18;
        height = 18;
    }

    @Override
    protected ItemDisplay newInstance()
    {
        return new ItemDisplay(posX,posY,container);
    }
}
