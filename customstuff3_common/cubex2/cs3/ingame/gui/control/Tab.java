package cubex2.cs3.ingame.gui.control;

public class Tab extends ControlContainer
{
    public final String title;

    public Tab(String title, int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
        this.title = title;
    }
}
