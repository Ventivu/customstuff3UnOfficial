package cubex2.cs3.common.inventory;

import cubex2.cs3.common.Alias;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class Inventory
{
    private IInventory inv;

    public Inventory(IInventory inventory)
    {
        inv = inventory;
    }

    public void clear()
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
            if (inv.getStackInSlot(i) != null)
            {
                inv.setInventorySlotContents(i, null);
            }
    }

    public int fill(Alias alias)
    {
        int amount = 0;
        int maxStack = alias.item.getItemStackLimit();

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            ItemStack itemstack1 = alias.getItemStackForInventory(maxStack);
            if (itemstack == null)
            {
                inv.setInventorySlotContents(i, itemstack1);
                amount += maxStack;
            }
            else if (ItemStackHelper.itemStackEqual(itemstack, itemstack1))
            {
                amount += maxStack - itemstack.stackSize;
                inv.setInventorySlotContents(i, itemstack1);
            }
        }

        return amount;
    }

    public int add(Alias alias, int count)
    {
        return add(alias, count, inv.getSizeInventory() - 1);
    }

    public int add(Alias alias, int count, int maxSlotId)
    {
        int amount = 0;
        int slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory() || slotId > maxSlotId)
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack == null || !ItemStackHelper.itemStackEqual(stack, alias.getItemStackForInventory(1)) || stack.stackSize == stack.getMaxStackSize())
            {
                slotId++;
                continue;
            }

            stack.stackSize++;
            amount++;
        }

        slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory() || slotId > maxSlotId)
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack != null && (!ItemStackHelper.itemStackEqual(stack, alias.getItemStackForInventory(1)) || stack.stackSize == stack.getMaxStackSize()))
            {
                slotId++;
                continue;
            }
            if (stack == null)
            {
                inv.setInventorySlotContents(slotId, alias.getItemStackForInventory(1));
                amount++;
                continue;
            }
            stack.stackSize++;
            amount++;
        }

        return amount;
    }

    public int remove(Alias alias, int count)
    {
        int amount = 0;
        int slotId = 0;

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory())
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack == null || ItemStackHelper.itemStackEqual(stack, alias.getItemStackForInventory(1)))
            {
                slotId++;
                continue;
            }
            if (stack.stackSize == 1)
            {
                inv.setInventorySlotContents(slotId, null);
                amount++;
                continue;
            }
            stack.stackSize--;
            amount++;
        }

        return amount;
    }

    public void clearSlot(int slotId)
    {
        inv.setInventorySlotContents(slotId, null);
    }

    public int fillSlot(int slotId)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = stack.getMaxStackSize() - stack.stackSize;
            inv.setInventorySlotContents(slotId, new ItemStack(stack.itemID, stack.getMaxStackSize(), stack.getItemDamage()));
        }

        return amount;
    }

    public int setSlot(int slotId, Alias alias, int count)
    {
        int amount = 0;
        int maxStack = Math.min(count, alias.item.getItemStackLimit());

        ItemStack stack = inv.getStackInSlot(slotId);
        ItemStack stack1 = alias.getItemStackForInventory(count);
        if (stack == null || !ItemStackHelper.itemStackEqual(stack, stack1))
        {
            inv.setInventorySlotContents(slotId, stack1);
            amount = maxStack;
        }
        else if (ItemStackHelper.itemStackEqual(stack, stack1))
        {
            amount = maxStack - stack.stackSize;
            inv.setInventorySlotContents(slotId, stack1);
        }

        return amount;
    }

    public int addToSlot(int slotId, int count)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = Math.min(stack.getMaxStackSize() - stack.stackSize, count);
            inv.setInventorySlotContents(slotId, new ItemStack(stack.itemID, stack.stackSize + amount, stack.getItemDamage()));
        }

        return amount;
    }

    public int removeFromSlot(int slotId, int count)
    {
        int amount = 0;

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            amount = Math.min(count, stack.stackSize);
            if (amount == stack.stackSize)
            {
                inv.setInventorySlotContents(slotId, null);
            }
            else
            {
                inv.setInventorySlotContents(slotId, new ItemStack(stack.itemID, stack.stackSize - amount, stack.getItemDamage()));
            }

        }

        return amount;
    }

    public void damageItem(int slotId, int count)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                stack.setItemDamage(stack.getItemDamage() + count);

                if (stack.getItemDamage() > stack.getMaxDamage())
                {

                    --stack.stackSize;

                    if (stack.stackSize < 0)
                    {
                        stack.stackSize = 0;
                    }

                    stack.setItemDamage(0);
                }
            }
    }

    public void repairItem(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                stack.setItemDamage(0);
            }
    }

    public void repairItem(int slotId, int count)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
            if (stack.isItemStackDamageable())
            {
                int amount = Math.min(count, stack.getItemDamage());
                stack.setItemDamage(stack.getItemDamage() - amount);
            }
    }

    public int getId(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.itemID;
    }

    public int getStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.stackSize;
    }

    public int getMaxStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getMaxStackSize();
    }

    public int getDamage(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getItemDamage();
    }

    public int getItemCount(Alias alias)
    {
        int amount = 0;

        ItemStack stack1 = alias.getItemStackForInventory(1);
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                {
                    amount += stack.stackSize;
                }
        }

        return amount;
    }

    public void moveStack(int from, int to)
    {
        if (inv.getStackInSlot(from) == null)
            return;
        if (inv.getStackInSlot(to) == null)
        {
            ItemStack stack = inv.getStackInSlot(from);
            inv.setInventorySlotContents(from, null);
            inv.setInventorySlotContents(to, stack);
        }
        else if (inv.getStackInSlot(from).isItemEqual(inv.getStackInSlot(to)))
        {
            ItemStack stackFrom = inv.getStackInSlot(from);
            ItemStack stackTo = inv.getStackInSlot(to);

            while (stackTo.stackSize < stackTo.getMaxStackSize() && stackFrom.stackSize > 0)
            {
                stackTo.stackSize++;
                stackFrom.stackSize--;
            }

            if (stackFrom.stackSize == 0)
            {
                inv.setInventorySlotContents(from, null);
            }
        }
    }

    public boolean isItemEqual(int slot1, int slot2)
    {
        ItemStack stack1 = inv.getStackInSlot(slot1);
        ItemStack stack2 = inv.getStackInSlot(slot2);

        if (stack1 == null && stack2 == null)
            return true;
        else if (stack1 == null ^ stack2 == null)
            return false;
        else
            return stack1.isItemEqual(stack2);
    }

    public ItemStack findItem(Alias alias)
    {
        ItemStack stack1 = alias.getItemStackForInventory(1);
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                    return stack;
        }

        return null;
    }

    public int findItemIndex(Alias alias)
    {
        ItemStack stack1 = alias.getItemStackForInventory(1);
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                    return i;
        }

        return -1;
    }

    public boolean hasEnchantment(int slotId, int id)
    {
        return hasEnchantment(slotId, id, -1);
    }

    public boolean hasEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.hasEnchantment(stack, id, level);
        else
            return false;
    }

    public void clearEnchantments(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.clearEnchantments(stack);
        }
    }

    public void addEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.addEnchantment(stack, id, level);
        }
    }

    public void removeEnchantment(int slotId, int id)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.removeEnchantment(stack, id);
        }
    }

    public int getIntData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getIntData(stack, name);
        return -1;
    }

    public float getFloatData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getFloatData(stack, name);
        return -1.0f;
    }

    public String getStringData(int slotId, String name)
    {

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getStringData(stack, name);
        return null;
    }

    public void setIntData(int slotId, String name, int data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setIntData(stack, name, data);
        }
    }

    public void setFloatData(int slotId, String name, float data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setFloatData(stack, name, data);
        }
    }

    public void setStringData(int slotId, String name, String data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setStringData(stack, name, data);
        }
    }

}
