package cubex2.cs3.gui;

import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.gui.attributes.GuiAttributes;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.JavaScriptHelper;

public class WindowNormal extends Window
{
    private final GuiAttributes container;
    private final WrappedGui gui;

    public WindowNormal(WrappedGui gui)
    {
        super(gui.container.width, gui.container.height);
        this.container = gui.container;
        this.gui = gui;

        for (ControlData data : container.guiData.controls)
        {
            data.addToWindow(this).controlTag = data;
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c.controlTag != null && c.controlTag instanceof ControlData)
        {
            if (c.controlTag instanceof ButtonData)
            {
                ButtonData data = (ButtonData) c.controlTag;
                if (data.onClicked != null && data.onClicked.script != null)
                {
                    ITriggerData trigger = new TriggerData("onClicked", TriggerType.GUI).setPlayer(mc.thePlayer);
                    JavaScriptHelper.executeTrigger(data.onClicked.script, trigger, gui.getPack());
                }
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        GuiBase.openPrevWindow();
    }
}
