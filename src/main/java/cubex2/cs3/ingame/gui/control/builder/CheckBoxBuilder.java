package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public class CheckBoxBuilder extends ControlBuilder<CheckBox>
{
    public CheckBoxBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected CheckBox newInstance()
    {
        return new CheckBox(posX, posY, container);
    }
}
