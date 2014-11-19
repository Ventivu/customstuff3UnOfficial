package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.control.Control;

public class ListBoxItemAttributeData extends ListBoxItemLabel<AttributeData>
{
    public ListBoxItemAttributeData(AttributeData value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);
    }
}
