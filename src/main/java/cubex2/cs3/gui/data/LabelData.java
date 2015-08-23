package cubex2.cs3.gui.data;

import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;

public class LabelData extends ControlData
{
    public String text;

    public LabelData(int x, int y,  String text)
    {
        super(x, y, 0, 0);
        this.text = text;
    }

    public LabelData()
    {
    }

    @Override
    public boolean isSizeable()
    {
        return false;
    }

    @Override
    public Control addToWindow(Window window)
    {
        return window.label(text).at(0,0).offset(x, y).add();
    }

    @Override
    protected String getControlType()
    {
        return "label";
    }
}
