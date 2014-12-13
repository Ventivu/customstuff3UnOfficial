package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class ListBoxBuilder<T> extends ControlBuilder<ListBox<T>>
{
    private ListBoxDescription<T> desc;

    public ListBoxBuilder(ListBoxDescription<T> desc, ControlContainer c)
    {
        super(c);
        this.desc = desc;
    }

    @Override
    public ListBox<T> add()
    {
        ListBox<T> res = super.add();
        res.createSlider(container);
        return res;
    }

    @Override
    protected ListBox<T> newInstance()
    {
        return new ListBox<T>(desc, anchor, offsetX, offsetY, container);
    }
}
