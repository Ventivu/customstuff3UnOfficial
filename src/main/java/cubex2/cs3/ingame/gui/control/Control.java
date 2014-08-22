package cubex2.cs3.ingame.gui.control;

import cpw.mods.fml.client.FMLClientHandler;
import cubex2.cs3.ingame.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.util.Rectangle;

public abstract class Control
{
    protected Control rootControl;
    protected Control parent;
    protected Rectangle rect;
    protected boolean isEnabled = true;
    protected boolean isVisible = true;
    protected Minecraft mc;
    protected float zLevel = 0F;
    protected final boolean useIntersectRect;

    public int x;
    public int y;
    protected int width;
    protected int height;
    private final ScrollContainer scrollContainer;

    /**
     * @param x      The x-position relative to the parent. Or absolute if the parent is null.
     * @param y      The y-position relative to the parent. Or absolute if the parent is null.
     * @param width  The control's width
     * @param height The control's height
     * @param parent The parent control. Can be null.
     */
    public Control(int x, int y, int width, int height, Control parent)
    {
        this.parent = parent;
        mc = FMLClientHandler.instance().getClient();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (parent != null)
        {
            if (x < 0)
            {
                x += parent.rect.getWidth() - width;
            }
            if (y < 0)
            {
                y += parent.rect.getHeight() - height;
            }
            x += parent.rect.getX();
            y += parent.rect.getY();
        }
        rect = new Rectangle(x, y, width, height);

        useIntersectRect = parent != null ? parent.useIntersectRect || parent instanceof ScrollContainer : false;
        if (this instanceof ScrollContainer)
            scrollContainer = (ScrollContainer) this;
        else if (useIntersectRect)
            scrollContainer = parent.scrollContainer;
        else
            scrollContainer = null;

        rootControl = this;
        while (rootControl.parent != null)
        {
            rootControl = rootControl.parent;
        }
    }

    public boolean isMouseOverControl(int mouseX, int mouseY)
    {
        if (useIntersectRect)
        {
            if (mouseY >= scrollContainer.getVisibleRect().getY() &&
                    mouseY <= scrollContainer.getVisibleRect().getY() + scrollContainer.getVisibleRect().getHeight())
            {
                return rect.contains(mouseX, mouseY);
            }
            return false;
        }
        return rect.contains(mouseX, mouseY);
    }

    public void updateRect()
    {
        int absX = x;
        int absY = y;
        if (parent != null)
        {
            if (absX < 0)
            {
                absX += parent.rect.getWidth() - width;
            }
            if (absY < 0)
            {
                absY += parent.rect.getHeight() - height;
            }
            absX += parent.rect.getX();
            absY += parent.rect.getY();
        } else // Windows
        {
            absX = (GuiBase.instance.width - width) / 2;
            absY = (GuiBase.instance.height - height) / 2;
        }
        rect.setBounds(absX, absY, width, height);
    }

    public final boolean canHandleInput()
    {
        return GuiBase.inputLockedControl == null || GuiBase.inputLockedControl == this;
    }

    public Control getParent()
    {
        return parent;
    }

    /**
     * Gets the control's rectangle.
     *
     * @return The rectangle in absolute positions.
     */
    public Rectangle getRect()
    {
        return rect;
    }

    public int getX()
    {
        return rect.getX();
    }

    public int getY()
    {
        return rect.getY();
    }

    public int getWidth()
    {
        return rect.getWidth();
    }

    public int getHeight()
    {
        return rect.getHeight();
    }

    /**
     * Gets the controls x-position relative to its parent.
     *
     * @return
     */
    public int getRelX()
    {
        return x;
    }

    /**
     * Gets the controls y-position relative to its parent.
     *
     * @return
     */
    public int getRelY()
    {
        return y;
    }

    public void setEnabled(boolean value)
    {
        isEnabled = value;
    }

    public boolean isEnabled()
    {
        return isEnabled;
    }

    public void setVisible(boolean value)
    {
        isVisible = value;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    /**
     * This is called every tick (0.05 seconds)
     */
    public void update()
    {
    }

    /**
     * This is called when the user clicks anywhere on the screen but only if the control is enabled and visible.
     *
     * @param mouseX      The absolute mouse x-position
     * @param mouseY      The absolute mouse y-position
     * @param button      The mouse button with that has been clicked.
     * @param intoControl true if the user has clicked into this control.
     */
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {

    }

    /**
     * This is called when the user clicks into this control but only if the control is enabled and visible.
     *
     * @param mouseX The absolute mouse x-position
     * @param mouseY The absolute mouse y-position
     * @param button The mouse button with that has been clicked.
     */
    public void mouseDown(int mouseX, int mouseY, int button)
    {
    }

    /**
     * This is called when the user releases a mouse button.
     *
     * @param mouseX The absolute mouse x-position
     * @param mouseY The absolute mouse y-position
     * @param button 0: left button, 1: right button
     */
    public void mouseUp(int mouseX, int mouseY, int button)
    {
    }

    /**
     * This is called when the user typed a key on the keyboard.
     *
     * @param c   The key as char.
     * @param key The key as int.
     */
    public void keyTyped(char c, int key)
    {
    }

    /**
     * @param mouseX The absolute mouse x-position.
     * @param mouseY The absolute mouse y-position.
     */
    public void draw(int mouseX, int mouseY)
    {
    }

    /**
     * Draw the foreground, i.e. hover text
     *
     * @param mouseX The absolute mouse x-position.
     * @param mouseY The absolute mouse y-position.
     */
    public void drawForeground(int mouseX, int mouseY)
    {

    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    public void drawString(String text, int x, int y, int color)
    {
        mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public void drawCenteredString(String text, int x, int y, int color)
    {
        mc.fontRenderer.drawStringWithShadow(text, x - mc.fontRenderer.getStringWidth(text) / 2, y, color);
    }
}
