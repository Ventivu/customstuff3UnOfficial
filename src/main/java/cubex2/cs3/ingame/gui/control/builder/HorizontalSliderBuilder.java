package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.HorizontalSlider;
import cubex2.cs3.ingame.gui.control.VerticalSlider;

public class HorizontalSliderBuilder extends ControlBuilder<HorizontalSlider>
{
    private int maxValue;

    public HorizontalSliderBuilder(int maxValue, ControlContainer c)
    {
        super(c);
        this.maxValue = maxValue;
    }

    @Override
    protected HorizontalSlider newInstance()
    {
        return new HorizontalSlider(maxValue, width, height, anchor, offsetX, offsetY, container);
    }
}
