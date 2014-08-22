package cubex2.cs3.ingame.gui.control.builder;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;

public abstract class ControlBuilder<T extends Control>
{
    protected ControlContainer container;

    protected int posX = 0;
    protected int posY = 0;
    protected int width = 0;
    protected int height = 0;

    public ControlBuilder(ControlContainer c)
    {
        container = c;
    }

    public ControlBuilder<T> below(Control c)
    {
        return below(c, 3);
    }

    public ControlBuilder<T> below(Control c, int gap)
    {
        posX = c.getRelX();
        posY = c.getRelY() + c.getHeight() + gap;
        return this;
    }

    public ControlBuilder<T> rightTo(Control c)
    {
        return rightTo(c, 3);
    }

    public ControlBuilder<T> rightTo(Control c, int gap)
    {
        posX = c.getRelX() + c.getWidth() + gap;
        posY = c.getRelY();
        return this;
    }

    public ControlBuilder<T> at(int x, int y)
    {
        posX = x;
        posY = y;
        return this;
    }

    public ControlBuilder<T> x(int x)
    {
        posX = x;
        return this;
    }

    public ControlBuilder<T> y(int y)
    {
        posY = y;
        return this;
    }

    public ControlBuilder<T> centerHor()
    {
        return centerHor(0);
    }

    public ControlBuilder<T> centerHor(int offset)
    {
        posX = (container.getWidth() - width) / 2 + offset;
        return this;
    }

    public ControlBuilder<T> size(int w, int h)
    {
        width = w;
        height = h;
        return this;
    }

    public ControlBuilder<T> width(int w)
    {
        width = w;
        return this;
    }

    public ControlBuilder<T> fillWidth(int gap)
    {
        posX = gap;
        width = container.getWidth() - gap * 2;
        return this;
    }

    public ControlBuilder<T> height(int h)
    {
        height = h;
        return this;
    }

    public T add()
    {
        T t = newInstance();
        container.addControl(t);
        return t;
    }

    protected abstract T newInstance();
}
