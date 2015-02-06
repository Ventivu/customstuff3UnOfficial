package cubex2.cs3.ingame.gui.control.listbox;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.util.ScissorHelper;

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
    private final boolean isSorted;
    private final int elementHeight;
    private final int elementWidth;
    private final int columns;
    private final int listBoxItemMeta;
    private VerticalSlider slider;
    private int sliderWidth;

    private IListBoxItemClickListener<T> itemClickListener;

    public ListBox(ListBoxDescription<T> desc, Anchor anchor, int offsetX, int offsetY, ControlContainer parent)
    {
        super(calculateVisibleHeight(desc), calculateWidth(desc), calculateHeight(desc.elements, desc.elementHeight, desc.columns), anchor, offsetX, offsetY, parent);
        elements = Lists.newArrayList(desc.elements);
        canSelect = desc.canSelect;
        multiSelect = desc.multiSelect;
        isSorted = desc.sorted;
        elementHeight = desc.elementHeight;
        elementWidth = desc.elementWidth == -1 ? (getWidth() - (desc.columns - 1) * HORIZONTAL_GAP) / desc.columns : desc.elementWidth;
        columns = desc.columns;
        listBoxItemMeta = desc.listBoxItemMeta;

        if (isSorted)
            Collections.sort((List<Comparable>) elements);

        createListBoxItems();

        sliderWidth = desc.sliderWidth;

        if (rootControl instanceof IListBoxItemClickListener)
        {
            itemClickListener = (IListBoxItemClickListener) rootControl;
        }
    }

    public VerticalSlider getSlider()
    {
        return slider;
    }

    public void createSlider(ControlContainer c)
    {
        slider = c.verticalSlider(getHeight() - getVisibleRect().getHeight()).size(sliderWidth, getVisibleRect().getHeight()).rightTo(this, 3).add();
        slider.setValueListener(this);
        slider.setWheelScrollEverywhere(true);
        slider.setWheelScrollStep(elementHeight + VERTICAL_GAP);
        slider.setListBoxRendering(true);
    }

    private static int calculateVisibleHeight(ListBoxDescription desc)
    {
        if (desc.rows == -1)
        {
            return desc.height;
        } else
        {
            return desc.rows * (desc.elementHeight + VERTICAL_GAP) - VERTICAL_GAP;
        }
    }

    private static int calculateWidth(ListBoxDescription desc)
    {
        if (desc.elementWidth == -1)
        {
            return desc.width - 3 - desc.sliderWidth;
        } else
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
    public void onParentResized()
    {
        slider.offsetY = currentScroll;

        super.onParentResized();

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

    public void removeSelection()
    {
        selectedIndices.clear();
        for (Control control : controls)
        {
            ((ListBoxItem) control).setSelected(false);
        }
    }

    public void updateElements(List<T> newElements)
    {
        elements.clear();
        elements.addAll(newElements);

        if (isSorted)
            Collections.sort((List<Comparable>) elements);

        selectedIndices.clear();
        height = calculateHeight(elements, elementHeight, columns);
        if (height < getHeight())
            setCurrentScroll(Math.max(height - getVisibleRect().getHeight(), 0));
        createListBoxItems();
        onParentResized();
    }

    private void createListBoxItems()
    {
        controls.clear();
        for (int i = 0; i < elements.size(); i++)
        {
            int elementX = (elementWidth + HORIZONTAL_GAP) * (i % columns);
            int elementY = i / columns * (elementHeight + VERTICAL_GAP);

            Anchor anchor = new Anchor(elementX, -1, elementY, -1);
            anchor.controlLeft = this;
            anchor.controlTop = this;
            anchor.sameSideLeft = true;
            anchor.sameSideTop = true;
            addControl(ListBoxItemProvider.createListBoxItem(elements.get(i), i, listBoxItemMeta, elementWidth, elementHeight, anchor, 0, 0, this));
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c instanceof ListBoxItem)
        {
            ListBoxItem<T> lbItem = (ListBoxItem<T>) c;

            if (canSelect)
            {
                if (button == 0 && !selectedIndices.contains(lbItem.index))
                {
                    if (!multiSelect)
                    {
                        selectedIndices.clear();
                        for (Control control : controls)
                        {
                            ((ListBoxItem) control).setSelected(false);
                        }
                    }
                    selectedIndices.add(lbItem.index);
                    lbItem.setSelected(true);
                } else if (button == 1 && selectedIndices.contains(lbItem.index))
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
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        boolean wasLocked = GuiBase.inputLockedControl != null;

        int firstVisible = currentScroll / (elementHeight + VERTICAL_GAP) * columns;
        int numVisible = getVisibleRect().getHeight() / elementHeight * columns + columns;

        for (int i = firstVisible; i < firstVisible + numVisible && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isEnabled() && c.isVisible())
            {
                if (!c.canHandleInput())
                    continue;

                boolean clickedControl = c.isMouseOverControl(mouseX, mouseY);// c.bounds.contains(mouseX, mouseY);
                c.mouseClicked(mouseX, mouseY, button, clickedControl);
                if (clickedControl)
                {
                    c.mouseDown(mouseX, mouseY, button);
                    controlClicked(c, mouseX, mouseY, button);
                    if (button == 0)
                        controlClicked(c, mouseX, mouseY);
                }

                boolean isLocked = GuiBase.inputLockedControl != null;
                if (wasLocked && !isLocked) // control released input
                    break; // make sure this click isn't received by other controls
            }
        }
    }

    @Override
    public void onUpdate()
    {
        int firstVisible = currentScroll / (elementHeight + VERTICAL_GAP) * columns;
        int numVisible = getVisibleRect().getHeight() / elementHeight * columns + columns;

        for (int i = firstVisible; i < firstVisible + numVisible && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            c.onUpdate();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        //super.draw(mouseX, mouseY, renderTick);
        ScissorHelper.startScissor(mc, getVisibleRect().getX(), getVisibleRect().getY(), getVisibleRect().getWidth(), getVisibleRect().getHeight());

        int firstVisible = currentScroll / (elementHeight + VERTICAL_GAP) * columns;
        int numVisible = getVisibleRect().getHeight() / elementHeight * columns + columns;

        for (int i = firstVisible; i < firstVisible + numVisible && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible())
            {
                c.draw(mouseX, mouseY, renderTick);
            }
        }

        ScissorHelper.endScissor();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        int firstVisible = currentScroll / (elementHeight + VERTICAL_GAP) * columns;
        int numVisible = getVisibleRect().getHeight() / elementHeight * columns + columns;

        for (int i = firstVisible; i < firstVisible + numVisible && i < controls.size(); i++)
        {
            Control c = controls.get(i);
            if (c.isVisible())
            {
                c.drawForeground(mouseX, mouseY);
            }
        }
    }

    @Override
    public void valueChanged(VerticalSlider slider)
    {
        setCurrentScroll(slider.getValue());
    }
}
