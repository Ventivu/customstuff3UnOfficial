package cubex2.cs3.ingame.gui;

import cubex2.cs3.ingame.gui.control.TextField;

public class WindowTextField extends Window
{
    private TextField textField;

    public WindowTextField()
    {
        super("Text Editor", BACK, 300, 200);
    }

    @Override
    public void init()
    {
        super.init();

        textField = new TextField(7, 7, 300-14, 200-14, this);
        addControl(textField);
    }
}
