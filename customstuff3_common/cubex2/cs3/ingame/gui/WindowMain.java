package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.*;

public class WindowMain extends Window
{
    private Button btnClose;
    private Button btnContentPacks;
    private BlockDisplay display;
    private VerticalSlider slider;
    private HorizontalSlider slider1;

    public WindowMain()
    {
        super("Custom Stuff", 114, 200);
    }

    @Override
    public void init()
    {
        super.init();

        btnClose = new Button("Close", 7, -7, 100, this);
        addControl(btnClose);

        btnContentPacks = new Button("Content Packs", 7, 7, 100, this);
        addControl(btnContentPacks);

        display = new BlockDisplay(7, 30, 77, 100, this);
        addControl(display);

        slider = new VerticalSlider(2, -7, 30, 20, 100, this);
        addControl(slider);

        slider1 = new HorizontalSlider(2, 7, 133, 77, 20, this);
        addControl(slider1);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnClose)
        {
            GuiBase.closeGui();
        }
        else if (c == btnContentPacks)
        {
            GuiBase.openWindow(new WindowContentPacks());
        }
    }

    @Override
    public void update()
    {
        super.update();

        //display.translateY = slider.getValueFloat() * 10;
        //display.scale = slider.getValueFloat() + 1;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        super.draw(mouseX, mouseY);
    }
}
