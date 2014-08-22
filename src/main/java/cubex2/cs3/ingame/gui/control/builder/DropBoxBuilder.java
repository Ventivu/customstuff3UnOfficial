package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.DropBox;

public class DropBoxBuilder extends ControlBuilder<DropBox>
{
    private String[] values;

    public DropBoxBuilder(String[] values, ControlContainer c)
    {
        super(c);
        this.values = values;
        height = 15;
    }

    @Override
    protected DropBox newInstance()
    {
        return new DropBox(values, posX, posY, width, height, container);
    }
}
