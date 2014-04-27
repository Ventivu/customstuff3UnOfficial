package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.IPurpuseStringProvider;
import cubex2.cs3.util.StringProviderPurpose;

public class ListBoxItemLabel<T> extends ListBoxItem<T>
{
    private Label label;

    public ListBoxItemLabel(T value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        String text = value instanceof IPurpuseStringProvider ? ((IPurpuseStringProvider) value).getStringForPurpose(StringProviderPurpose.LIST_BOX_ITEM_LABEl)
                : value.toString();
        label = new Label(text, 3, (getHeight() - 9) / 2 + 1, this);
        addControl(label);
    }

    @Override
    public void selectionChanged()
    {
        label.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
