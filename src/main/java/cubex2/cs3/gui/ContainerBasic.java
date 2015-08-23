package cubex2.cs3.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.data.ControlData;
import cubex2.cs3.gui.data.GuiData;
import cubex2.cs3.gui.data.PlayerInventoryData;
import cubex2.cs3.gui.data.SlotData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerBasic extends Container
{
    private final WrappedGui gui;
    private final EntityPlayer player;
    private final IInventory slotInv;

    public ContainerBasic(WrappedGui gui, EntityPlayer player, IInventory slotInv)
    {
        this.gui = gui;
        this.player = player;
        this.slotInv = slotInv;

        final boolean isStackInv = slotInv instanceof InventoryItemStack;

        GuiData data = gui.container.guiData;
        int nextSlot = 0;
        for (ControlData cData : data.controls)
        {

            if (cData instanceof SlotData)
            {
                addSlotToContainer(new Slot(slotInv, nextSlot++, cData.x + 1, cData.y + 1));
            } else if (cData instanceof PlayerInventoryData)
            {
                for (int i = 0; i < 3; ++i)
                {
                    for (int j = 0; j < 9; ++j)
                    {
                        this.addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, cData.x + 1 + j * 18, cData.y + 1 + i * 18));
                    }
                }

                for (int i = 0; i < 9; ++i)
                {
                    this.addSlotToContainer(new Slot(player.inventory, i, cData.x + 1 + i * 18, cData.y + 59)
                    {
                        @Override
                        public boolean canTakeStack(EntityPlayer player)
                        {
                            return !isStackInv || player.inventory.getCurrentItem() != getStack();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        slotInv.markDirty();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
