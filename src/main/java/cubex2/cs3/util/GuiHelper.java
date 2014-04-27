package cubex2.cs3.util;

import cubex2.cs3.ingame.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Rectangle;

import java.util.Iterator;
import java.util.List;

public class GuiHelper
{
    public static void drawOutlinedRect(int x1, int y1, int x2, int y2, int color1, int color2)
    {
        Gui.drawRect(x1, y1, x2, y2, color1);
        Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, color2);
    }

    public static void drawOutlinedRect(Rectangle rect, int color1, int color2)
    {
        drawRect(rect, color1);
        Gui.drawRect(rect.getX() + 1, rect.getY() + 1, rect.getX() + rect.getWidth() - 1, rect.getY() + rect.getHeight() - 1, color2);
    }

    public static void drawRect(int x1, int y1, int x2, int y2, int color)
    {
        Gui.drawRect(x1, y1, x2, y2, color);
    }

    public static void drawRect(Rectangle rect, int color)
    {
        Gui.drawRect(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), color);
    }

    public static void drawHoveringText(List<String> list, int x, int y, FontRenderer font)
    {
        if (!list.isEmpty())
        {
            GuiBase gui = GuiBase.instance;

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            int k = 0;
            Iterator<String> iterator = list.iterator();

            while (iterator.hasNext())
            {
                String s = iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;

            if (list.size() > 1)
            {
                k1 += 2 + (list.size() - 1) * 10;
            }

            if (i1 + k > gui.width)
            {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > gui.height)
            {
                j1 = gui.height - k1 - 6;
            }

            gui.setZLevel(300.0F);
            GuiBase.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            gui.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            gui.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            gui.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            gui.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            gui.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            gui.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            gui.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            GL11.glDisable(GL11.GL_DEPTH_TEST);

            for (int k2 = 0; k2 < list.size(); ++k2)
            {
                String s1 = list.get(k2);

                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            gui.setZLevel(0.0F);
            GuiBase.itemRenderer.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            RenderHelper.enableStandardItemLighting();
            RenderHelper.disableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
