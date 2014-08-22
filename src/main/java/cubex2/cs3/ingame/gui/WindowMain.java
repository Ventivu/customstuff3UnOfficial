package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;

public class WindowMain extends Window
{
    private Button btnClose;
    private Button btnContentPacks;
    private Button btnTextEditor;

    public WindowMain()
    {
        super("Custom Stuff", 114, 200);
    }

    @Override
    public void init()
    {
        super.init();

        btnClose = button("Close").y(-7).fillWidth(7).add();
        btnContentPacks = button("Content Packs").y(7).fillWidth(7).add();
        btnTextEditor = button("Text Editor").below(btnContentPacks, 7).fillWidth(7).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == btnClose)
        {
            GuiBase.closeGui();
        } else if (c == btnContentPacks)
        {
            GuiBase.openWindow(new WindowContentPacks());
        } else if (c == btnTextEditor)
        {
            GuiBase.openWindow(new WindowTextField());
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
