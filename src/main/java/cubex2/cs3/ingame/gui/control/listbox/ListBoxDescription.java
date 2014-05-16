package cubex2.cs3.ingame.gui.control.listbox;

import java.util.List;

public class ListBoxDescription<E>
{
    public ListBoxDescription()
    {
    }

    public ListBoxDescription(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int x = 7;
    public int y = 20;
    public int width = -1;
    public int height = -1;
    public int columns = 1;
    public int rows = -1;
    public int elementWidth = -1;
    public int elementHeight = 13;
    public int sliderWidth = 20;
    public int listBoxItemMeta = 0;
    public boolean canSelect = true;
    public boolean multiSelect = false;
    public boolean sorted = false;
    public List<E> elements;
}