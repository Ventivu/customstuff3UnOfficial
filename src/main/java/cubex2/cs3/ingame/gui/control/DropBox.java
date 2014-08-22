package cubex2.cs3.ingame.gui.control;

import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.Rectangle;

public class DropBox extends Control
{
    private final String[] values;
    private String selectedValue = null;
    private boolean isExpanded = false;

    private int offset = 0;
    private int maxShowedValues = 6;

    public DropBox(String[] values, int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
        this.values = values;
    }

    public void setSelectedValue(String value)
    {
        selectedValue = value;
    }

    public String getSelectedValue()
    {
        return selectedValue;
    }

    @Override
    public void update()
    {
        int wheel = GuiBase.dWheel;
        if (wheel != 0 && isExpanded)
        {
            offset = MathHelper.clamp_int(offset - wheel / 120, 0, values.length - maxShowedValues);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        int borderColor = getRect().contains(mouseX,mouseY) && !isExpanded ? Color.WHITE : Color.DARK_GREY;
        GuiHelper.drawOutlinedRect(getRect(), borderColor, Color.LIGHT_GREY);

        if (selectedValue != null)
        {
            mc.fontRenderer.drawString(selectedValue, rect.getX() + 3, rect.getY() + 3, Color.BLACK);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (isExpanded)
        {
            for (int i = 0; i < Math.min(values.length, maxShowedValues); i++)
            {
                int index = i + offset;

                int rx = getX() + 10;
                int ry = getY() + (i + 1) * height;
                Rectangle rect = new Rectangle(rx, ry, width, height);

                int borderColor = rect.contains(mouseX, mouseY) ? Color.WHITE : Color.DARK_GREY;
                GuiHelper.drawOutlinedRect(rect, borderColor, Color.LIGHT_GREY);

                mc.fontRenderer.drawString(values[index], rx + 3, ry + 3, Color.BLACK);
            }
        }
    }

    @Override
    public void mouseDown(int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            isExpanded = !isExpanded;
            if (isExpanded)
            {
                GuiBase.lockInput(this);
            }
            else
            {
                GuiBase.releaseInput();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        if (isExpanded && !intoControl)
        {
            Rectangle expandedRect = new Rectangle(getX() + 10, getY() + height, width, height * values.length);
            if (expandedRect.contains(mouseX, mouseY))
            {
                int index = (mouseY - expandedRect.getY()) / height + offset;
                selectedValue = values[index];
            }
            isExpanded = false;
            GuiBase.releaseInput();
        }
    }


}
