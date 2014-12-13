package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.VerticalSlider;

public class VerticalSliderBuilder extends ControlBuilder<VerticalSlider>
{
    private int maxValue;

    public VerticalSliderBuilder(int maxValue, ControlContainer c)
    {
        super(c);
        this.maxValue = maxValue;
    }

    @Override
    protected VerticalSlider newInstance()
    {
        return new VerticalSlider(maxValue, width, height, anchor, offsetX, offsetY, container);
    }
}
