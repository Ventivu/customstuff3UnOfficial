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

    /**
     * Completely clear the inventory.
     */
    public void clear()
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            if (inv.getStackInSlot(i) != null)
            {
                inv.setInventorySlotContents(i, null);
            }
        }
    }

    /**
     * Fills up the inventory with the given alias. Existing items won't be replaced.
     * If the alias uses a wildcard damage value, 0 will be used.
     *
     * @param alias
     *         The alias to fill the inventory with.
     * @return The amount added to the inventory.
     */
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

    /**
     * Adds a specific amount of the alias to the inventory.
     * If the alias uses a wildcard damage value, 0 will be used.
     *
     * @param alias
     *         The alias to add to the inventory.
     * @param count
     *         The amount to add to the inventory.
     * @return The amount actually added to the inventory. This can be lower than the given count if there is not enough space for it.
     */
    public int add(Alias alias, int count)
    {
        return add(alias, count, inv.getSizeInventory() - 1);
    }

    /**
     * Adds a specific amount of the alias to the inventory.
     * If the alias uses a wildcard damage value, 0 will be used.
     *
     * @param alias
     *         The alias to add to the inventory.
     * @param count
     *         The amount to add to the inventory.
     * @param maxSlotId
     *         The maximum slot id to add items to.
     * @return The amount actually added to the inventory. This can be lower than the given count if there is not enough space for it.
     */
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

    /**
     * Removes a specific amount of items from the inventory. If the alias uses a wildcard damage value, items of all damage values will be removed.
     *
     * @param alias
     *         The alias of an item to remove from the inventory.
     * @param count
     *         The amount to remove from the inventory.
     * @return The amount actually removed from the inventory.
     */
    public int remove(Alias alias, int count)
    {
        int amount = 0;
        int slotId = 0;

        ItemStack aliasStack = alias.getItemStack();

        while (amount != count)
        {
            if (slotId > inv.getSizeInventory())
            {
                break;
            }

            ItemStack stack = inv.getStackInSlot(slotId);

            if (stack == null || ItemStackHelper.itemStackEqual(stack, aliasStack))
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

    /**
     * Clears a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     */
    public void clearSlot(int slotId)
    {
        inv.setInventorySlotContents(slotId, null);
    }

    /**
     * Fills up a specific slot. If there is no item in the slot, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @return The amount added to the slot.
     */
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

    /**
     * Sets the item in a specific slot. If the alias uses a wildcard damage value, 0 will be used.
     *
     * @param slotId
     *         The id of the slot.
     * @param alias
     *         The alias.
     * @param count
     *         The amount of the item.
     * @return The amount added to the slot.
     */
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

    /**
     * Increases the stack size of an item in a specific slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param count
     *         The amount to increase.
     * @return The amount actually added.
     */
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

    /**
     * Decreases the stack size of an item in a specific slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param count
     *         The amount to decrease.
     * @return The amount actually removed.
     */
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

    /**
     * Damages an item in a specific slot. If the slot is empty this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param count
     *         The amount of damage to add to the item.
     */
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

    /**
     * Removes all damage from an item in a specific slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     */
    public void repairItem(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
        {
            if (stack.isItemStackDamageable())
            {
                stack.setItemDamage(0);
            }
        }
    }

    /**
     * Repairs an item by a specific amount. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param count
     *         The amount of damage to remove from the item.
     */
    public void repairItem(int slotId, int count)
    {
        ItemStack stack = inv.getStackInSlot(slotId);

        if (stack != null)
        {
            if (stack.isItemStackDamageable())
            {
                int amount = Math.min(count, stack.getItemDamage());
                stack.setItemDamage(stack.getItemDamage() - amount);
            }
        }
    }

    /**
     * Gets the id of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @return The id or -1 if the slot is empty.
     */
    public int getId(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.itemID;
    }

    /**
     * Gets the stack size of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @return The stack size or -1 if the slot is empty.
     */
    public int getStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.stackSize;
    }

    /**
     * Gets the maximum stack size of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @return The maximum stack size or -1 if the slot is empty.
     */
    public int getMaxStackSize(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getMaxStackSize();
    }

    /**
     * Gets the damage value of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @return The damage value or -1 if the slot is empty.
     */
    public int getDamage(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        return stack == null ? -1 : stack.getItemDamage();
    }

    /**
     * Gets the amount of a specific item in the inventory. If the alias uses a wildcard damage value, the damage value
     * doesn't matter when searching for the item.
     *
     * @param alias
     *         The alias.
     * @return The amount in the inventory.
     */
    public int getItemCount(Alias alias)
    {
        int amount = 0;

        ItemStack stack1 = alias.getItemStack();
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null)
            {
                if (ItemStackHelper.itemStackEqual(stack, stack1))
                {
                    amount += stack.stackSize;
                }
            }
        }

        return amount;
    }

    /**
     * Moves an item from one slot to another. If the source slot is empty or the item in the destination slot doesn't
     * match the item in the source slot, this does nothing.
     *
     * @param from
     *         The source slot id.
     * @param to
     *         The destination slot id.
     */
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

    /**
     * Checks if the items in two slots are equal.
     *
     * @param slot1
     *         The id of the first slot.
     * @param slot2
     *         The id of the second slot.
     * @return True if the items are equal, false otherwise.
     */
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

    /**
     * Gets the first ItemStack that matches the given alias.
     *
     * @param alias
     *         The alias.
     * @return The ItemStack or null if nothing is found.
     */
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

    /**
     * Gets the index of the first item that matches the given alias.
     *
     * @param alias
     *         The alias.
     * @return The index or -1 if nothing is found.
     */
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

    /**
     * Checks if the item in the given slot has a specific enchantment.
     *
     * @param slotId
     *         The id of the slot.
     * @param id
     *         The id of the enchantment.
     * @return True if the item has the enchantment, false otherwise.
     */
    public boolean hasEnchantment(int slotId, int id)
    {
        return hasEnchantment(slotId, id, -1);
    }

    /**
     * Checks if the item in the given slot has a specific enchantment and level.
     *
     * @param slotId
     *         The id of the slot.
     * @param id
     *         The id of the enchantment.
     * @param level
     *         The level of the enchantment.
     * @return True if the item has the enchantment, false otherwise.
     */
    public boolean hasEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.hasEnchantment(stack, id, level);
        else
            return false;
    }

    /**
     * Removes all enchantments from the item in the given slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     */
    public void clearEnchantments(int slotId)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.clearEnchantments(stack);
        }
    }

    /**
     * Adds a specific enchantment to an item in a given slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param id
     *         The id of the enchantment.
     * @param level
     *         The level of the enchantment.
     */
    public void addEnchantment(int slotId, int id, int level)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.addEnchantment(stack, id, level);
        }
    }

    /**
     * Removes a specific enchantment form an item in a given slot. If the slot is empty, this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param id
     *         The id of the enchantment.
     */
    public void removeEnchantment(int slotId, int id)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.removeEnchantment(stack, id);
        }
    }

    /**
     * Gets an integer data of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @return The integer or -1 if the slot is empty or the data doesn't exist.
     */
    public int getIntData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getIntData(stack, name);
        return -1;
    }

    /**
     * Gets a float data of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @return The float or -1.0 if the slot is empty or the data doesn't exist.
     */
    public float getFloatData(int slotId, String name)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getFloatData(stack, name);
        return -1.0f;
    }

    /**
     * Gets a string data of an item in a specific slot.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @return The string or null if the slot is empty or the data doesn't exist.
     */
    public String getStringData(int slotId, String name)
    {

        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
            return ItemStackHelper.getStringData(stack, name);
        return null;
    }

    /**
     * Sets an integer data of an item in a specific slot. If the slot is empty this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @param data
     *         The data.
     */
    public void setIntData(int slotId, String name, int data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setIntData(stack, name, data);
        }
    }

    /**
     * Sets a float data of an item in a specific slot. If the slot is empty this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @param data
     *         The data.
     */
    public void setFloatData(int slotId, String name, float data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setFloatData(stack, name, data);
        }
    }

    /**
     * Sets a string data of an item in a specific slot. If the slot is empty this does nothing.
     *
     * @param slotId
     *         The id of the slot.
     * @param name
     *         The name of the data
     * @param data
     *         The data.
     */
    public void setStringData(int slotId, String name, String data)
    {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack != null)
        {
            ItemStackHelper.setStringData(stack, name, data);
        }
    }
}
