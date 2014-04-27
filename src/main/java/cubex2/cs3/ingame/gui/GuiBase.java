package cubex2.cs3.ingame.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.Stack;

public class GuiBase extends GuiScreen
{
    public static int dWheel = 0;
    public static final GuiBase instance = new GuiBase();
    public static final RenderItem itemRenderer = new RenderItem();
    public final Stack<Window> windowHistory = new Stack<Window>();
    private Window window;

    private GuiBase()
    {
        window = new WindowMain();
        window.init();
    }

    public static void closeGui()
    {
        instance.mc.displayGuiScreen((GuiScreen) null);
        instance.mc.setIngameFocus();
    }

    public static void openWindow(Window window)
    {
        openWindow(window, null);
    }

    public static void openWindow(Window window, String tag)
    {
        window.tag = tag;
        instance.windowHistory.push(instance.window);
        instance.window = window;
        instance.window.init();
        instance.window.updateRect();
    }

    public static void openPrevWindow()
    {
        Window currentWindow = instance.window;
        instance.window = instance.windowHistory.pop();
        instance.window.updateRect();
        if (instance.window instanceof IWindowClosedListener)
            ((IWindowClosedListener) instance.window).windowClosed(currentWindow);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        window.updateRect();
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen()
    {
        dWheel = Mouse.getDWheel();
        window.update();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        boolean intoWindow = window.getRect().contains(mouseX, mouseY);
        window.mouseClicked(mouseX, mouseY, button, intoWindow);
        if (intoWindow)
        {
            window.mouseDown(mouseX, mouseY, button);
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button)
    {
        window.mouseUp(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        if (i == Keyboard.KEY_ESCAPE)
        {
            mc.displayGuiScreen((GuiScreen) null);
            mc.setIngameFocus();
        }
        window.keyTyped(c, i);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderTick)
    {
        window.draw(mouseX, mouseY);
        window.drawForeground(mouseX, mouseY);
    }

    @Override
    public void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2)
    {
        super.drawGradientRect(x1, y1, x2, y2, color1, color2);
    }

    public void setZLevel(float value)
    {
        zLevel = value;
    }


}
