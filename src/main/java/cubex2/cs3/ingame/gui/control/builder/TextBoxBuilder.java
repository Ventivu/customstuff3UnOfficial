package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.TextBox;

public class TextBoxBuilder extends ControlBuilder<TextBox>
{
    public TextBoxBuilder(ControlContainer c)
    {
        super(c);
    }

    @Override
    protected TextBox newInstance()
    {
        return new TextBox(posX, posY, width, height, container);
    }
}
