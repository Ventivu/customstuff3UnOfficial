package cubex2.cs3.ingame.gui.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.CookProgressData;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.CookProgressBar;

public class WindowCookProgress extends WindowProgress<CookProgressBar, CookProgressData>
{
    public WindowCookProgress(WrappedGui gui, Window window, int x, int y, int width, int height)
    {
        super("Create Cook Progress", gui, window, x, y, width, height);
    }

    public WindowCookProgress(WrappedGui gui, Window window, CookProgressBar control, CookProgressData data)
    {
        super("Edit Cook Progress", gui, window, control, data);
    }

    @Override
    protected CookProgressData createData()
    {
        return new CookProgressData(nupX.getValue(), nupY.getValue(), nupWidth.getValue(), nupHeight.getValue(),
                tbName.getText(), tbTexture.getLocation(), nupU.getValue(), nupV.getValue(), dbDirection.getSelectedValue());
    }
}
