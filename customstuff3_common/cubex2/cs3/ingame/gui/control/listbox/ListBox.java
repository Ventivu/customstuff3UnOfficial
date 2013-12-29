package cubex2.cs3.ingame.gui.control.listbox;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.*;

import java.util.Collections;
import java.util.List;

public class ListBox<T> extends ScrollContainer implements IVerticalSliderValueListener
{
    private static final int HORIZONTAL_GAP = 1;
    private static final int VERTICAL_GAP = 1;

    protected final List<T> elements;
    protected final List<Integer> selectedIndices = Lists.newArrayList();

    private final boolean multiSelect;
    private final boolean canSelect;
    private final int elementHeight;
    private final int elementWidth;
    private final int columns;
    private VerticalSlider slider;

    private IListBoxItemClickListener itemClickListener;

    public ListBox(ListBoxDescription desc, ControlContainer parent)
    {
        super(calculateVisibleHeight(desc), desc.x, desc.y, calculateWidth(desc), calculateHeight(desc.elements, desc.elementHeight, desc.columns), parent);
        elements = Lists.newArrayList(desc.elements);
        canSelect = desc.canSelect;
        multiSelect = desc.multiSelect;
        elementHeight = desc.elementHeight;
        elementWidth = desc.elementWidth == -1 ? (getWidth() - (desc.columns - 1) * HORIZONTAL_GAP) / desc.columns : desc.elementWidth;
        columns = desc.columns;

        createListBoxItems();

        slider = new VerticalSlider(getHeight() - getVisibleRect().getHeight(), desc.x + getWidth() + 3, desc.y, desc.sliderWidth, getVisibleRect().getHeight(), parent);
        slider.setValueListener(this);
        slider.setWheelScrollEverywhere(true);
        slider.setWheelScrollStep(elementHeight + VERTICAL_GAP);
        slider.setListBoxRendering(true);
        parent.addControl(slider);

        if (rootControl instanceof IListBoxItemClickListener)
        {
            itemClickListener = (IListBoxItemClickListener) rootControl;
        }
    }

    private static int calculateVisibleHeight(ListBoxDescription desc)
    {
        if (desc.rows == -1)
        {
            return desc.height;
        }
        else
        {
            return desc.rows * (desc.elementHeight + VERTICAL_GAP) - VERTICAL_GAP;
        }
    }

    private static int calculateWidth(ListBoxDescription desc)
    {
        if (desc.elementWidth == -1)
        {
            return desc.width - 3 - desc.sliderWidth;
        }
        else
        {
            return desc.columns * (desc.elementWidth + HORIZONTAL_GAP) - HORIZONTAL_GAP;
        }
    }

    private static int calculateHeight(List<?> elements, int elementHeight, int columns)
    {
        int numRows = elements.size() / columns + (elements.size() % columns != 0 ? 1 : 0);
        return numRows * elementHeight + (numRows - 1) * VERTICAL_GAP;
    }

    @Override
    public void updateRect()
    {
        super.updateRect();

        slider.setMaxValue(getHeight() - getVisibleRect().getHeight());
    }

    /**
     * Gets the selected index.
     *
     * @return The selected index or -1 if no item is selected.
     */
    public int getSelectedIndex()
    {
        return selectedIndices.size() != 0 ? selectedIndices.get(0) : -1;
    }

    /**
     * Gets the selected item.
     *
     * @return The selected item or null if no item is selected.
     */
    public T getSelectedItem()
    {
        return getSelectedIndex() != -1 ? elements.get(getSelectedIndex()) : null;
    }

    public List<Integer> getSelectedIndices()
    {
        return Collections.unmodifiableList(selectedIndices);
    }

    public List<T> getSelectedItems()
    {
        List<T> items = Lists.newArrayList();
        for (Integer i : getSelectedIndices())
        {
            items.add(elements.get(i));
        }
        return Collections.unmodifiableList(items);
    }

    public void updateElements(List<T> elements)
    {
        this.elements.clear();
        this.elements.addAll(elements);
        selectedIndices.clear();
        height = calculateHeight(this.elements, elementHeight, columns);
        if (height < getHeight())
            setCurrentScroll(height - getVisibleRect().getHeight());
        createListBoxItems();
        updateRect();
    }

    private void createListBoxItems()
    {
        controls.clear();
        for (int i = 0; i < elements.size(); i++)
        {
            int elementX = (elementWidth + HORIZONTAL_GAP) * (i % columns);
            int elementY = i / columns * (elementHeight + VERTICAL_GAP);

            addControl(ListBoxItemProvider.createListBoxItem(elements.get(i), i, elementX, elementY, elementWidth, elementHeight, this));
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c instanceof ListBoxItem)
        {
            ListBoxItem lbItem = (ListBoxItem) c;

            if (canSelect)
            {
                if (button == 0 && !selectedIndices.contains(lbItem.index))
                {
                    if (!multiSelect)
                    {
                        selectedIndices.clear();
                        for (int i = 0; i < controls.size(); i++)
                        {
                            ((ListBoxItem) controls.get(i)).setSelected(false);
                        }
                    }
                    selectedIndices.add(lbItem.index);
                    lbItem.setSelected(true);
                }
                else if (button == 1 && selectedIndices.contains(lbItem.index))
                {
                    selectedIndices.remove((Integer) lbItem.index);
                    lbItem.setSelected(false);
                }
                Collections.sort(selectedIndices);
            }

            if (itemClickListener != null)
            {
                itemClickListener.itemClicked(lbItem.value, this, button);
            }
        }
    }

    @Override
    public void valueChanged(VerticalSlider slider)
    {
        setCurrentScroll(slider.getValue());
    }
}
