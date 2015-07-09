package cubex2.cs3.common.scripting;

import cubex2.cs3.common.inventory.Inventory;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

public class ScriptableInventory
{
    private Inventory inv;

    public ScriptableInventory(IInventory inventory)
    {
        inv = new Inventory(inventory);
    }

    /**
     * Clear the inventory.
     */
    public void clear()
    {
        inv.clear();
    }

    /**
     * Fill up the inventory with the given item.
     *
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The amount added to the inventory
     */
    public int fill(String itemName, int metadata)
    {
        return inv.fill(GeneralHelper.getItem(itemName), metadata);
    }

    /**
     * Add items the the inventory.
     *
     * @param itemName The item's name
     * @param count    The amount to add to the inventory.
     * @param metadata The item's damage value
     * @return The amount added to the inventory
     */
    public int add(String itemName, int count, int metadata)
    {
        return inv.add(GeneralHelper.getItem(itemName), count, metadata);
    }

    /**
     * Remove items from the inventory.
     *
     * @param itemName The item's name
     * @param count    The amount to remove from the inventory
     * @param metadata The item's damage value
     * @return The amount removed from the inventory
     */
    public int remove(String itemName, int count, int metadata)
    {
        return inv.remove(GeneralHelper.getItem(itemName), count, metadata);
    }

    /**
     * Clear the slot with the given id.
     *
     * @param slotId The slot's id
     */
    public void clearSlot(int slotId)
    {
        inv.clearSlot(slotId);
    }

    /**
     * Fill up the slot with the given id. If the slot is empty, this does nothing.
     *
     * @param slotId The slot's id
     * @return The amount added to the slot
     */
    public int fillSlot(int slotId)
    {
        return inv.fillSlot(slotId);
    }

    /**
     * Set the contents of the slot with the given id.
     *
     * @param slotId   The slot's id
     * @param itemName The item's name
     * @param count    The amount of the item
     * @param metadata The item's damage value
     * @return The given count if the slot's content doesn't match with the item or the amount added or removed if the slot's content
     * matches with the item
     */
    public int setSlot(int slotId, String itemName, int count, int metadata)
    {
        return inv.setSlot(slotId, GeneralHelper.getItem(itemName), count, metadata);
    }

    /**
     * Add the given amount to a slot with the given id. If the slot is empty, this does nothing.
     *
     * @param slotId The slot's id
     * @param count  The amount to add to the slot
     * @return The amount added to the slot
     */
    public int addToSlot(int slotId, int count)
    {
        return inv.addToSlot(slotId, count);
    }

    /**
     * Remove the given amount from a slot with the given id. If the slot is empty, this does nothing.
     *
     * @param slotId The slot's id
     * @param count  The amount to remove from the slot
     * @return The amount removed from the slot
     */
    public int removeFromSlot(int slotId, int count)
    {
        return inv.removeFromSlot(slotId, count);
    }

    /**
     * Damage the item in the slot with the given id. If the slot is empty or the item in the slot isn't damageable, this does nothing.
     *
     * @param slotId The slot's id
     * @param count  The amount of damage to add to the item
     */
    public void damageItem(int slotId, int count)
    {
        inv.damageItem(slotId, count);
    }

    /**
     * Repair the item in the slot with the given id. If the slot is empty or the item in the slot isn't damageable, this does nothing
     *
     * @param slotId The slot's id
     */
    public void repairItem(int slotId)
    {
        inv.repairItem(slotId);
    }

    /**
     * Repair the item in the slot with the given id by a certain amount. If the slot is empty or the item in the slot isn't damageable,
     * this does nothing
     *
     * @param slotId The slot's id
     * @param count  The amount of damage to remove from the item
     */
    public void repairItem(int slotId, int count)
    {
        inv.repairItem(slotId, count);
    }

    /**
     * Get the name of the item in the slot with the given id.
     *
     * @param slotId The slot's id
     * @return The item's name or null if the slot doesn't exist or the slot is empty
     */
    public String getItemName(int slotId)
    {
        Item item = inv.getItem(slotId);
        return item == null ? null : GeneralHelper.getItemName(item);
    }

    /**
     * Get the stack size of the item in the slot with the given id.
     *
     * @param slotId The slot's id
     * @return The item's stack size or -1 if the slot doesn't exist or the slot is empty
     */
    public int getStackSize(int slotId)
    {
        return inv.getStackSize(slotId);
    }

    /**
     * Get the maximum stack size of the item in the slot with the given id.
     *
     * @param slotId The slot's id
     * @return The item's maximum stack size or -1 if the slot doesn't exist or the slot is empty
     */
    public int getMaxStackSize(int slotId)
    {
        return inv.getMaxStackSize(slotId);
    }

    /**
     * Get the damage value of the item in the slot with the given id.
     *
     * @param slotId The slot's id
     * @return The item's damage value or -1 if the slot doesn't exist or the slot is empty
     */
    public int getDamage(int slotId)
    {
        return inv.getDamage(slotId);
    }

    /**
     * Get the amount of an item in the inventory.
     *
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The amount of the given item in the inventory
     */
    public int getItemCount(String itemName, int metadata)
    {
        return inv.getItemCount(GeneralHelper.getItem(itemName), metadata);
    }

    /**
     * Move as many items as possible from one slot to another.
     *
     * @param from The destination slot's id
     * @param to   The id of the slot to move the items to
     */
    public void moveStack(int from, int to)
    {
        inv.moveStack(from, to);
    }

    /**
     * Check if the items in two slots are equal
     *
     * @param slot1 The first slot's id
     * @param slot2 The second slot's id
     * @return True if the items are equal, false otherwise
     */
    public boolean isItemEqual(int slot1, int slot2)
    {
        return inv.isItemEqual(slot1, slot2);
    }

    /**
     * Check if the item in a slot has a certain enchantment.
     *
     * @param slotId The slot's id
     * @param id     The enchantment's id
     * @return True if the item has the enchantment, false otherwise
     */
    public boolean hasEnchantment(int slotId, int id)
    {
        return inv.hasEnchantment(slotId, id);
    }

    /**
     * Check if the item in a slot has a certain enchantment.
     *
     * @param slotId The slot's id
     * @param id     The enchantment's id
     * @param level  The enchantment's level
     * @return True if the item has the enchantment, false otherwise
     */
    public boolean hasEnchantment(int slotId, int id, int level)
    {
        return inv.hasEnchantment(slotId, id, level);
    }

    /**
     * Clear the enchantments of the item in a slot.
     *
     * @param slotId The slot's id
     */
    public void clearEnchantments(int slotId)
    {
        inv.clearEnchantments(slotId);
    }

    /**
     * Add a certain enchantment to the item in a slot.
     *
     * @param slotId The slot's id
     * @param id     The enchantment's id
     * @param level  The enchantment's level
     */
    public void addEnchantment(int slotId, int id, int level)
    {
        inv.addEnchantment(slotId, id, level);
    }

    /**
     * Remove a certain enchantment from the item in a slot.
     *
     * @param slotId The slot's id
     * @param id     The enchantment's id
     */
    public void removeEnchantment(int slotId, int id)
    {
        inv.removeEnchantment(slotId, id);
    }

    /**
     * Get a stack's integer data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @return The data or -1 if the slot is empty or the data doesn't exist.
     */
    public int getIntData(int slotId, String name)
    {
        return inv.getIntData(slotId, name);
    }

    /**
     * Get a stack's float data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @return The data or -1.0 if the slot is empty or the data doesn't exist.
     */
    public float getFloatData(int slotId, String name)
    {
        return inv.getFloatData(slotId, name);
    }

    /**
     * Get a stack's string data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @return The data or null if the slot is empty or the data doesn't exist.
     */
    public String getStringData(int slotId, String name)
    {
        return inv.getStringData(slotId, name);
    }

    /**
     * Set a stack's integer data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @param data   The data
     */
    public void setIntData(int slotId, String name, int data)
    {
        inv.setIntData(slotId, name, data);
    }

    /**
     * Set a stack's float data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @param data   The data
     */
    public void setFloatData(int slotId, String name, float data)
    {
        inv.setFloatData(slotId, name, data);
    }

    /**
     * Set a stack's string data.
     *
     * @param slotId The slot's id
     * @param name   The data's name
     * @param data   The data
     */
    public void setStringData(int slotId, String name, String data)
    {
        inv.setStringData(slotId, name, data);
    }
}
