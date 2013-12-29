package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.lib.Color;

public class Label extends Control
{
    protected String text;
    protected int color;
    protected boolean centered;
    private String[] lines;

    public Label(String text, int x, int y, Control parent)
    {
        this(text, Color.BLACK, x, y, parent);
    }

    public Label(String text, int color, int x, int y, Control parent)
    {
        super(x, y, 0, 9, parent);
        setText(text);
        this.color = color;
    }

    public Label setCentered()
    {
        centered = true;
        return this;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setText(String value)
    {
        text = value;
        if (text != null)
            lines = text.split("\n");
        else
            lines = new String[0];

        int width = 0;
        for (int i = 0; i < lines.length; i++)
        {
            int w = mc.fontRenderer.getStringWidth(lines[i]);
            if (w > width)
                width = w;
        }

        rect.setWidth(width);
        rect.setHeight(lines.length * 9 + (lines.length - 1) * 4);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        for (int i = 0; i < lines.length; i++)
        {
            int y = rect.getY() + i * 13;
            if (centered)
            {
                mc.fontRenderer.drawString(lines[i], rect.getX() + (rect.getWidth() - mc.fontRenderer.getStringWidth(text)) / 2, y, color);
            }
            else
            {
                mc.fontRenderer.drawString(lines[i], rect.getX(), y, color);
            }
        }
    }
}
