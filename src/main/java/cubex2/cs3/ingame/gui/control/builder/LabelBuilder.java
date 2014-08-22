package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.*;

public class LabelBuilder extends ControlBuilder<Label>
{
    private String text;

    public LabelBuilder(String text, ControlContainer c)
    {
        super(c);
        this.text = text;
    }

    @Override
    public ControlBuilder<Label> below(Control c)
    {
        if (c instanceof TextBox)
        {
            return below(c, 5);
        }
        return super.below(c);
    }

    @Override
    public ControlBuilder<Label> rightTo(Control c)
    {
        ControlBuilder<Label> cb = super.rightTo(c);
        if (c instanceof CheckBox)
        {
            posY += 1;
        }
        return cb;
    }

    @Override
    protected Label newInstance()
    {
        return new Label(text, posX, posY, container);
    }
}
