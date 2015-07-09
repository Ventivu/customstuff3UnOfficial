package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.ingame.docs.ParsedDocFile;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Button;

public class WindowDocs extends Window
{
    public WindowDocs(String title, String path)
    {
        super(title, BACK, 256, 200);

        ParsedDocFile.fromPath(path).add(this);
    }

    public Button getBackButton()
    {
        return btnBack;
    }
}
