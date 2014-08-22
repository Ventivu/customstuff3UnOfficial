package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.Stack;

public class GuiBase extends GuiScreen
{
    public static int dWheel = 0;
    public static final GuiBase instance = new GuiBase();
    public static final RenderItem itemRenderer = new RenderItem();
    public final Stack<Window> windowHistory = new Stack<Window>();
    private Window window;

    public static boolean devMode = false;
    public static Control activeDevControl = null;
    public static Control inputLockedControl = null;

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

    public static void lockInput(Control c)
    {
        inputLockedControl = c;
    }

    public static void releaseInput()
    {
        inputLockedControl = null;
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
        if (devMode)
        {
            if (button == 0)
            {
                activeDevControl = window.getControlAt(mouseX, mouseY);
            }
        } else
        {
            window.mouseClicked(mouseX, mouseY, button, intoWindow);
            if (intoWindow)
            {
                window.mouseDown(mouseX, mouseY, button);
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button)
    {
        if (devMode)
        {

        } else
        {
            window.mouseUp(mouseX, mouseY, button);
        }
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        if (i == Keyboard.KEY_ESCAPE)
        {
            mc.displayGuiScreen((GuiScreen) null);
            mc.setIngameFocus();
        } else if (i == Keyboard.KEY_F7)
        {
            devMode = !devMode;
            if (!devMode)
            {
                activeDevControl = null;
            }
        }

        if (devMode)
        {
            if (activeDevControl != null)
            {
                if (i == Keyboard.KEY_DOWN)
                {
                    activeDevControl.y += 1;
                    activeDevControl.updateRect();
                }
                else if (i == Keyboard.KEY_UP)
                {
                    activeDevControl.y -= 1;
                    activeDevControl.updateRect();
                }

                if (i == Keyboard.KEY_LEFT)
                {
                    activeDevControl.x -= 1;
                    activeDevControl.updateRect();
                }
                else if (i == Keyboard.KEY_RIGHT)
                {
                    activeDevControl.x += 1;
                    activeDevControl.updateRect();
                }

                if (i == Keyboard.KEY_PRIOR)
                {
                    activeDevControl = activeDevControl.getParent();
                }
            }
        } else
        {
            window.keyTyped(c, i);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderTick)
    {
        window.draw(mouseX, mouseY);
        window.drawForeground(mouseX, mouseY);

        if (devMode)
        {
            drawDevScreen(mouseX, mouseY);
        }
    }

    private void drawDevScreen(int mouseX, int mouseY)
    {
        mc.fontRenderer.drawString("Dev Mode", 5, 5, Color.RED);

        Control c = window.getControlAt(mouseX, mouseY);
        if (c != null)
        {
            List<String> list = Lists.newArrayList();
            list.add(c.getClass().getSimpleName());
            list.add(String.format("X: %d (%d) Y: %d (%d)", c.getX(), c.getRelX(), c.getY(), c.getRelY()));
            list.add("W: " + c.getWidth() + " H: " + c.getHeight());
            GuiHelper.drawHoveringText(list, mouseX, mouseY, mc.fontRenderer);
        }


        if (activeDevControl != null)
        {
            GuiHelper.drawBorder(activeDevControl.getX() - 1, activeDevControl.getY() - 1,
                    activeDevControl.getX() + activeDevControl.getWidth() + 1, activeDevControl.getY() + activeDevControl.getHeight() + 1,
                    Color.RED);
        }
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
