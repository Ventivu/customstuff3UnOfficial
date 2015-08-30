package cubex2.cs3.gui;

import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.gui.data.ButtonData;
import cubex2.cs3.gui.data.ControlData;
import cubex2.cs3.ingame.gui.WindowContainer;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.JavaScriptHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class WindowContainerNormal extends WindowContainer
{
    public final GuiContainerAttributes container;
    public final WrappedGui gui;
    private final IInventory slotInv;

    public WindowContainerNormal(WrappedGui gui, IInventory slotInv)
    {
        super(gui.container.width, gui.container.height);
        this.container = (GuiContainerAttributes) gui.container;
        this.gui = gui;
        this.slotInv = slotInv;

        for (ControlData data : container.guiData.controls)
        {
            data.addToWindow(this).controlTag = data;
        }
    }

    // TODO move this into another class, see WindowNormal
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
    public Container getContainer()
    {
        return new ContainerBasic(gui, mc.thePlayer, slotInv);
    }
}
