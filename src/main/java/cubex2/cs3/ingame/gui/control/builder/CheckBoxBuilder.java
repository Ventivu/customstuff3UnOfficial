package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.CheckBox;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public class CheckBoxBuilder extends ControlBuilder<CheckBox>
{
    private boolean isChecked = false;

    public CheckBoxBuilder(ControlContainer c)
    {
        super(c);
    }

    public CheckBoxBuilder checked(boolean isChecked)
    {
        this.isChecked = isChecked;
        return this;
    }

    @Override
    protected CheckBox newInstance()
    {
        CheckBox cb =  new CheckBox(posX, posY, container);
        cb.setIsChecked(isChecked);
        return cb;
    }
}
