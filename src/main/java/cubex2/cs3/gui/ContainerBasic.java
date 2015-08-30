package cubex2.cs3.gui;

import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiContainerAttributes;
import cubex2.cs3.gui.data.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBasic extends Container
{
    private final WrappedGui gui;
    private final EntityPlayer player;
    private final IInventory slotInv;
    private final GuiContainerAttributes container;

    public ContainerBasic(WrappedGui gui, EntityPlayer player, IInventory slotInv)
    {
        this.gui = gui;
        this.container = (GuiContainerAttributes) gui.container;
        this.player = player;
        this.slotInv = slotInv;

        final boolean isStackInv = slotInv instanceof InventoryItemStack;

        GuiData data = gui.container.guiData;
        int nextSlot = 0;
        for (int iter = 0; iter < 2; iter++)
        {
            for (ControlData cData : data.controls)
            {
                if (iter == 0 && cData instanceof SlotData)
                {
                    addSlotToContainer(new Slot(slotInv, nextSlot++, cData.x + 1, cData.y + 1));
                } else if (iter == 1 && cData instanceof PlayerInventoryData)
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
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            boolean ruleApplied = false;
            for (ShiftClickRule rule : container.shiftClickRules.rules)
            {
                int start = rule.fromStart;
                int end = rule.fromEnd;
                if (rule.fromInv)
                {
                    start += slotInv.getSizeInventory();
                    end += slotInv.getSizeInventory();
                }

                if (slotIndex >= start && slotIndex <= end)
                {
                    start = rule.toStart;
                    end = rule.toEnd;
                    if (rule.toInv)
                    {
                        start += slotInv.getSizeInventory();
                        end += slotInv.getSizeInventory();
                    }

                    ruleApplied = true;
                    boolean reverse = start > end;
                    if (!mergeItemStack(itemstack1, reverse ? end : start, reverse ? start + 1 : end + 1, reverse))
                    {
                        return null;
                    }
                }
            }

            if (!ruleApplied)
            {
                return null;
            }

            /*if (slotIndex < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
            {
                return null;
            }*/

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
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
        return slotInv.isUseableByPlayer(player);
    }
}
