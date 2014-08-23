package cubex2.cs3.common.scripting;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.inventory.Inventory;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.ArrayList;
import java.util.Random;

public class ScriptableWorld
{
    private World world;

    public ScriptableWorld(World world)
    {
        super();
        this.world = world;
    }

    public void setMod(BaseContentPack mod)
    {
    }

    /**
     * Sets the id of the block at the given position
     *
     * @param position  The position
     * @param blockName The name of the block
     */
    public void setBlock(ScriptablePosition position, String blockName)
    {
        setBlock((int) position.x, (int) position.y, (int) position.z, blockName);
    }

    /**
     * Sets the id of the block at the given coordinates
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param z         The z coordinate
     * @param blockName The name of the block
     */
    public void setBlock(int x, int y, int z, String blockName)
    {
        world.setBlock(x, y, z, GeneralHelper.getBlock(blockName));
    }

    /**
     * Sets the damage value of a block
     *
     * @param position The position
     * @param metadata The damage value of the block
     */
    public void setBlockMetadata(ScriptablePosition position, int metadata)
    {
        setBlockMetadata((int) position.x, (int) position.y, (int) position.z, metadata);
    }

    /**
     * Sets the damage value of a block
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param metadata The damage value of the block
     */
    public void setBlockMetadata(int x, int y, int z, int metadata)
    {
        world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
        world.markBlockForUpdate(x, y, z);
    }

    /**
     * Sets the id and damage value of a block
     *
     * @param position  The position
     * @param blockName The block's name
     * @param metadata  The block's damage value
     */
    public void setBlockAndMetadata(ScriptablePosition position, String blockName, int metadata)
    {
        setBlockAndMetadata((int) position.x, (int) position.y, (int) position.z, blockName, metadata);
    }

    /**
     * Sets the id and damage value of a block
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param z         The z coordinate
     * @param blockName The block's name
     * @param metadata  The block's damage value
     */
    public void setBlockAndMetadata(int x, int y, int z, String blockName, int metadata)
    {
        world.setBlock(x, y, z, GeneralHelper.getBlock(blockName), metadata, 3);
    }

    /**
     * Harvests a block
     *
     * @param position The position
     */
    public void harvestBlock(ScriptablePosition position)
    {
        harvestBlock((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Harvests a block
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void harvestBlock(int x, int y, int z)
    {
        new Random();
        Block block = world.getBlock(x, y, z);
        int damage = world.getBlockMetadata(x, y, z);
        ArrayList<ItemStack> is = block.getDrops(world, x, y, z, damage, 0);

        world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        world.setBlockToAir(x, y, z);
        float f = 0.7F;
        double d = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        if (is.size() > 0)
        {
            for (ItemStack is1 : is)
                if (is1.stackSize != 0)
                {
                    if (!world.isRemote)
                    {
                        EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, new ItemStack(is1.getItem(), is1.stackSize, is1.getItemDamage()));
                        entityitem.delayBeforeCanPickup = 10;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
        }
    }

    /**
     * Sets the time
     *
     * @param time The time
     */
    public void setTime(long time)
    {
        world.setWorldTime(time);
    }

    /**
     * Gets the world time
     *
     * @return The world time
     */
    public long getTime()
    {
        return world.getWorldTime();
    }

    /**
     * Creates a explosion
     *
     * @param position The position
     * @param strength The strength of the explosion
     * @param flaming  True if the explosion is a flaming explosion
     */
    public void createExplosion(ScriptablePosition position, float strength, boolean flaming)
    {
        createExplosion(position.x, position.y, position.z, strength, flaming);
    }

    /**
     * Creates a explosion
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param strength The strength of the explosion
     * @param flaming  True if the explosion is a flaming explosion
     */
    public void createExplosion(double x, double y, double z, float strength, boolean flaming)
    {
        world.newExplosion(null, x, y, z, strength, flaming, /* Huge explosion */true);
    }

    /**
     * Creates a thunderbolt
     *
     * @param position The position
     */
    public void createThunderbolt(ScriptablePosition position)
    {
        createThunderbolt(position.x, position.y, position.z);
    }

    /**
     * Creates a thunderbolt
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void createThunderbolt(double x, double y, double z)
    {
        EntityLightningBolt entitylightningbolt = new EntityLightningBolt(world, x, y, z);
        world.spawnEntityInWorld(entitylightningbolt);
    }

    /**
     * Spawns a mob
     *
     * @param position The position
     * @param name     The name of the mob
     */
    public void spawnMob(ScriptablePosition position, String name)
    {
        spawnMob(position.x, position.y, position.z, name);
    }

    /**
     * Spawns a mob
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param name The name of the mob
     */
    public void spawnMob(double x, double y, double z, String name)
    {
        if (!world.isRemote)
        {
            Entity entity = EntityList.createEntityByName(name, world);
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntityInWorld(entity);
        }
    }

    /**
     * Spawns an item
     *
     * @param position The position
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param damage   The item's damage value
     */
    public void spawnItem(ScriptablePosition position, String itemName, int count, int damage)
    {
        spawnItem(position.x, position.y, position.z, itemName, count, damage);
    }

    /**
     * Spawns an item
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param damage   The item's damage value
     */
    public void spawnItem(double x, double y, double z, String itemName, int count, int damage)
    {
        if (world.isRemote)
            return;
        float f = 0.7F;
        double d = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, new ItemStack(GeneralHelper.getItem(itemName), count, damage));
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    /**
     * Sets the weather
     *
     * @param weather  sun, rain or thundering
     * @param duration The duration
     */
    public void setWeather(String weather, int duration)
    {
        if (weather.equals("sun"))
        {
            world.getWorldInfo().setRaining(false);
            world.getWorldInfo().setRainTime(0);
            world.getWorldInfo().setThundering(false);
            world.getWorldInfo().setThunderTime(0);
        } else if (weather.equals("rain"))
        {
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setRainTime(duration);
            world.getWorldInfo().setThundering(false);
            world.getWorldInfo().setThunderTime(0);
        } else if (weather.equals("thundering"))
        {
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setRainTime(duration);
            world.getWorldInfo().setThundering(true);
            world.getWorldInfo().setThunderTime(duration);
        }
    }

    /**
     * Check if it is raining.
     *
     * @return true if it is raining, false otherwise
     */
    public boolean isRaining()
    {
        return world.getWorldInfo().isRaining();
    }

    /**
     * Check if it is thundering.
     *
     * @return true if it is thundering, false otherwise
     */
    public boolean isThundering()
    {
        world.isThundering();
        return world.getWorldInfo().isThundering();
    }

    /**
     * Fills a container. Existing items won't be replaced.
     *
     * @param position The position
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The amount added to the container or -1 if there is no valid container
     */
    public int fillContainer(ScriptablePosition position, String itemName, int metadata)
    {
        return fillContainer((int) position.x, (int) position.y, (int) position.z, itemName, metadata);
    }

    /**
     * Fills a container. Existing items won't be replaced.
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The amount added to the container or -1 if there is no valid container
     */
    public int fillContainer(int x, int y, int z, String itemName, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).fill(GeneralHelper.getItem(itemName), metadata);
        }
        return -1;
    }

    /**
     * Clears a container
     *
     * @param position The position
     */
    public void clearContainer(ScriptablePosition position)
    {
        clearContainer((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Clears a container
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void clearContainer(int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).clear();
        }
    }

    /**
     * Adds items to a container if there's space for them.
     *
     * @param position The position
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount added to the container or -1 if there is no valid container
     */
    public int addToContainer(ScriptablePosition position, String itemName, int count, int metadata)
    {
        return addToContainer((int) position.x, (int) position.y, (int) position.z, itemName, count, metadata);
    }

    /**
     * Adds items to a container if there's space for them.
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount added to the container or -1 if there is no valid container
     */
    public int addToContainer(int x, int y, int z, String itemName, int count, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).add(GeneralHelper.getItem(itemName), count, metadata);
        }
        return -1;
    }

    /**
     * Removes items from a container
     *
     * @param position The position
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount removed from the container or -1 if there is no valid container
     */
    public int removeFromContainer(ScriptablePosition position, String itemName, int count, int metadata)
    {
        return removeFromContainer((int) position.x, (int) position.y, (int) position.z, itemName, count, metadata);
    }

    /**
     * Removes items from a container
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount removed from the container or -1 if there is no valid container
     */
    public int removeFromContainer(int x, int y, int z, String itemName, int count, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).remove(GeneralHelper.getItem(itemName), count, metadata);
        }
        return -1;
    }

    /**
     * Clears a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     */
    public void clearContainerSlot(ScriptablePosition position, int slotId)
    {
        clearContainerSlot((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Clears a container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     */
    public void clearContainerSlot(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).clearSlot(slotId);
        }
    }

    /**
     * Fills a container slot if it isn't empty
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return The amount added to the slot or -1 if there is no valid container
     */
    public int fillContainerSlot(ScriptablePosition position, int slotId)
    {
        return fillContainerSlot((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Fills a container slot if it isn't empty
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @return The amount added to the slot or -1 if there is no valid container
     */
    public int fillContainerSlot(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).fillSlot(slotId);
        }
        return -1;
    }

    /**
     * Sets a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount added or removed if there is already the same item in this slot or count if the slot is empty or an other item is
     * in it or -65 if there is no valid container
     */
    public int setContainerSlot(ScriptablePosition position, int slotId, String itemName, int count, int metadata)
    {
        return setContainerSlot((int) position.x, (int) position.y, (int) position.z, slotId, itemName, count, metadata);
    }

    /**
     * Sets a container slot
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param slotId   The slot's id
     * @param itemName The item's name
     * @param count    The item's stack size
     * @param metadata The item's damage value
     * @return The amount added or removed if there is already the same item in this slot or count if the slot is empty or an other item is
     * in it or -65 if there is no valid container
     */
    public int setContainerSlot(int x, int y, int z, int slotId, String itemName, int count, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).setSlot(slotId, GeneralHelper.getItem(itemName), count, metadata);
        }
        return -65;
    }

    /**
     * Removes items from a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param count    The count of items to remove
     * @return The amount removed from the slot or -1 if there is no valid container
     */
    public int removeFromContainerSlot(ScriptablePosition position, int slotId, int count)
    {
        return removeFromContainerSlot((int) position.x, (int) position.y, (int) position.z, slotId, count);
    }

    /**
     * Removes items from a container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param count  The count of items to remove
     * @return The amount removed from the slot or -1 if there is no valid container
     */
    public int removeFromContainerSlot(int x, int y, int z, int slotId, int count)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).removeFromSlot(slotId, count);
        }
        return -1;
    }

    /**
     * Adds items to a container slot if it isn't empty
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param count    The count to add
     * @return The amount added to the slot or -1 if there is no valid container
     */
    public int addToContainerSlot(ScriptablePosition position, int slotId, int count)
    {
        return addToContainerSlot((int) position.x, (int) position.y, (int) position.z, slotId, count);
    }

    /**
     * Adds items to a container slot if it isn't empty
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param count  The count to add
     * @return The amount added to the slot or -1 if there is no valid container
     */
    public int addToContainerSlot(int x, int y, int z, int slotId, int count)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).addToSlot(slotId, count);
        }
        return -1;
    }

    /**
     * Damages an item in a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param count    The damage to add to the item
     */
    public void damageItem(ScriptablePosition position, int slotId, int count)
    {
        damageItem((int) position.x, (int) position.y, (int) position.z, slotId, count);
    }

    /**
     * Damages an item in a container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param count  The damage to add to the item
     */
    public void damageItem(int x, int y, int z, int slotId, int count)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).damageItem(slotId, count);
        }
    }

    /**
     * Fully repairs an item in a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     */
    public void repairItem(ScriptablePosition position, int slotId)
    {
        repairItem((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Fully repairs an item in a container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     */
    public void repairItem(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).repairItem(slotId);
        }
    }

    /**
     * Repairs an item in a container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param count    The damage to remove from the item
     */
    public void repairItem(ScriptablePosition position, int slotId, int count)
    {
        repairItem((int) position.x, (int) position.y, (int) position.z, slotId, count);
    }

    /**
     * Repairs an item in a container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param count  The damage to remove from the item
     */
    public void repairItem(int x, int y, int z, int slotId, int count)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).repairItem(slotId, count);
        }
    }

    /**
     * Checks if the itemstack in the given container slot has any enchantment
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return true if the itemstack has any enchantment
     */
    public boolean hasEnchantment(ScriptablePosition position, int slotId)
    {
        return hasEnchantment((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Checks if the itemstack in the fiven container slot has any enchantment
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @return true if the itemstack has any enchantment
     */
    public boolean hasEnchantment(int x, int y, int z, int slotId)
    {
        return hasEnchantment(x, y, z, slotId, -1);
    }

    /**
     * Checks if the itemstack in the given container slot has the given enchantment
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param id       The id of the enchantment
     * @return true if the itemstack has the given enchantment
     */
    public boolean hasEnchantment(ScriptablePosition position, int slotId, int id)
    {
        return hasEnchantment((int) position.x, (int) position.y, (int) position.z, slotId, id);
    }

    /**
     * Checks if the itemstack in the given container slot has the given enchantment
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param id     The id of the enchantment
     * @return true if the itemstack has the given enchantment
     */
    public boolean hasEnchantment(int x, int y, int z, int slotId, int id)
    {
        return hasEnchantment(x, y, z, slotId, slotId, -1);
    }

    /**
     * Checks if the itemstack in the given container slot has the given enchantment with the given level
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param id       The id of the enchantment
     * @param level    The level of the enchantment
     * @return true if the itemstack has the given enchantment
     */
    public boolean hasEnchantment(ScriptablePosition position, int slotId, int id, int level)
    {
        return hasEnchantment((int) position.x, (int) position.y, (int) position.z, slotId, id, level);
    }

    /**
     * Checks if the itemstack in the given container slot has the given enchantment with the given level
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param id     The id of the enchantment
     * @param level  The level of the enchantment
     * @return true if the itemstack has the given enchantment
     */
    public boolean hasEnchantment(int x, int y, int z, int slotId, int id, int level)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).hasEnchantment(slotId, id, level);
        }
        return false;
    }

    /**
     * Removes all enchantments from the itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     */
    public void clearEnchantments(ScriptablePosition position, int slotId)
    {
        clearEnchantments((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Removes all enchantments from the itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     */
    public void clearEnchantments(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).clearEnchantments(slotId);
        }
    }

    /**
     * Adds an enchantment to the itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param id       The id of the enchantment
     * @param level    The level of the enchantment
     */
    public void addEnchantment(ScriptablePosition position, int slotId, int id, int level)
    {
        addEnchantment((int) position.x, (int) position.y, (int) position.z, slotId, id, level);
    }

    /**
     * Adds an enchantment to the itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param id     The id of the enchantment
     * @param level  The level of the enchantment
     */
    public void addEnchantment(int x, int y, int z, int slotId, int id, int level)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).addEnchantment(slotId, id, level);
        }
    }

    /**
     * Removes an enchantment from the itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param id       The id of the enchantment
     */
    public void removeEnchantment(ScriptablePosition position, int slotId, int id)
    {
        removeEnchantment((int) position.x, (int) position.y, (int) position.z, slotId, id);
    }

    /**
     * Removes an enchantment from the itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param id     The id of the enchantment
     */
    public void removeEnchantment(int x, int y, int z, int slotId, int id)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).removeEnchantment(slotId, id);
        }
    }

    /**
     * Gets the int data of an itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param name     The data's name
     * @return The data or -1 if there is no container or the data doesn't exist
     */
    public int getStackIntData(ScriptablePosition position, int slotId, String name)
    {
        return getStackIntData((int) position.x, (int) position.y, (int) position.z, slotId, name);
    }

    /**
     * Gets the int data of an itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param name   The data's name
     * @return The data or -1 if there is no container or the data doesn't exist
     */
    public int getStackIntData(int x, int y, int z, int slotId, String name)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).getIntData(slotId, name);
        }
        return -1;
    }

    /**
     * Gets the string data of an itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param name     The data's name
     * @return The data or null if there is no container or the data doesn't exist
     */
    public String getStackStringData(ScriptablePosition position, int slotId, String name)
    {
        return getStackStringData((int) position.x, (int) position.y, (int) position.z, slotId, name);
    }

    /**
     * Gets the string data of an itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param name   The data's name
     * @return The data or null if there is no container or the data doesn't exist
     */
    public String getStackStringData(int x, int y, int z, int slotId, String name)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).getStringData(slotId, name);
        }
        return null;
    }

    /**
     * Sets the int data of an itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param name     The data's name
     * @param data     The data
     */
    public void setStackIntData(ScriptablePosition position, int slotId, String name, int data)
    {
        setStackIntData((int) position.x, (int) position.y, (int) position.z, slotId, name, data);
    }

    /**
     * Sets the int data of an itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param name   The data's name
     * @param data   The data
     */
    public void setStackIntData(int x, int y, int z, int slotId, String name, int data)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).setIntData(slotId, name, data);
        }
    }

    /**
     * Sets the string data of an itemstack in the given container slot
     *
     * @param position The position
     * @param slotId   The slot's id
     * @param name     The data's name
     * @param data     The data
     */
    public void setStackStringData(ScriptablePosition position, int slotId, String name, String data)
    {
        setStackStringData((int) position.x, (int) position.y, (int) position.z, slotId, name, data);
    }

    /**
     * Sets the string data of an itemstack in the given container slot
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @param name   The data's name
     * @param data   The data
     */
    public void setStackStringData(int x, int y, int z, int slotId, String name, String data)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            new Inventory(iinventory).setStringData(slotId, name, data);
        }
    }

    /**
     * Gets the block name
     *
     * @param position The position
     * @return The block's name
     */
    public String getBlockName(ScriptablePosition position)
    {
        return getBlockName((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the block name
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The block's name
     */
    public String getBlockName(int x, int y, int z)
    {
        return GeneralHelper.getBlockName(world.getBlock(x, y, z));
    }

    /**
     * Gets the block's damage value
     *
     * @param position The position
     * @return The block's damage value
     */
    public int getBlockMetadata(ScriptablePosition position)
    {
        return getBlockMetadata((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the block's damage value
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The block's damage value
     */
    public int getBlockMetadata(int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    /**
     * Gets the name of an item in a slot of a container
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return The item's name or null if the slot is empty
     */
    public String getContainerSlotItemName(ScriptablePosition position, int slotId)
    {
        return getContainerSlotItemName((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Gets the name of an item in a slot of a container
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId
     * @return The item's name or null if the slot is empty
     */
    public String getContainerSlotItemName(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iiventory = (IInventory) tile;
            return GeneralHelper.getItemName(new Inventory(iiventory).getItem(slotId));
        }
        return null;
    }

    /**
     * Gets the damage value of an item in a slot of a container
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return The item's damage value or -1 if the slot is empty
     */
    public int getContainerSlotMetadata(ScriptablePosition position, int slotId)
    {
        return getContainerSlotMetadata((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Gets the damage value of an item in a slot of a container
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId The slot's id
     * @return The item's damage value or -1 if the slot is empty
     */
    public int getContainerSlotMetadata(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iiventory = (IInventory) tile;
            return new Inventory(iiventory).getDamage(slotId);
        }
        return -1;
    }

    /**
     * Gets the stack size of an item in a slot of a container
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return The item's stack size or -1 if the slot is empty
     */
    public int getContainerSlotStackSize(ScriptablePosition position, int slotId)
    {
        return getContainerSlotStackSize((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Gets the stack size of an item in a slot of a container
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId slotId The slot's id
     * @return The item's stack size or -1 if the slot is empty
     */
    public int getContainerSlotStackSize(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).getStackSize(slotId);
        }
        return -1;
    }

    /**
     * Gets the maximum stack size of an item in a slot of a container.
     *
     * @param position The position
     * @param slotId   The slot's id
     * @return The item's maximum stack size or -1 if the slot is empty
     */
    public int getContainerSlotMaxStackSize(ScriptablePosition position, int slotId)
    {
        return getContainerSlotMaxStackSize((int) position.x, (int) position.y, (int) position.z, slotId);
    }

    /**
     * Gets the maximum stack size of an item in a slot of a container.
     *
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     * @param slotId slotId The slot's id
     * @return The item's maximum stack size or -1 if the slot is empty
     */
    public int getContainerSlotMaxStackSize(int x, int y, int z, int slotId)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).getMaxStackSize(slotId);
        }
        return -1;
    }

    /**
     * Gets the count of a item in a container
     *
     * @param position The position
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The count of the item
     */
    public int getContainerItemCount(ScriptablePosition position, String itemName, int metadata)
    {
        return getContainerItemCount((int) position.x, (int) position.y, (int) position.z, itemName, metadata);
    }

    /**
     * Gets the count of a item in a container
     *
     * @param x        The x coordinate
     * @param y        The y coordinate
     * @param z        The z coordinate
     * @param itemName The item's name
     * @param metadata The item's damage value
     * @return The count of the item
     */
    public int getContainerItemCount(int x, int y, int z, String itemName, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IInventory)
        {
            IInventory iinventory = (IInventory) tile;
            return new Inventory(iinventory).getItemCount(GeneralHelper.getItem(itemName), metadata);
        }
        return -1;
    }

    /**
     * @param position The position
     * @param slot     The slot's id
     * @return true if the item in the slot is fuel for the vanilla furnace false otherwise
     */
    public boolean isFuelInContainerSlot(ScriptablePosition position, int slot)
    {
        return isFuelInContainerSlot((int) position.x, (int) position.y, (int) position.z, slot);
    }

    /**
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param slot The slot's id
     * @return true if the item in the slot is fuel for the vanilla furnace false otherwise
     */
    public boolean isFuelInContainerSlot(int x, int y, int z, int slot)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null || !(tile instanceof IInventory))
            return false;
        IInventory invContainer = (IInventory) tile;
        ItemStack is = invContainer.getStackInSlot(slot);
        return TileEntityFurnace.isItemFuel(is);
    }

    /**
     * Gets the biome
     *
     * @param position The position
     * @return The name of the biome
     */
    public String getBiome(ScriptablePosition position)
    {
        return getBiome((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the biome
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The name of the biome
     */
    public String getBiome(int x, int y, int z)
    {
        return world.getWorldChunkManager().getBiomeGenAt(x, z).biomeName;
    }

    /**
     * Shears a block if it can be sheared.
     *
     * @param position The position
     */
    public void shear(ScriptablePosition position)
    {
        shear((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Shears a block if it can be sheared.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public void shear(int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block instanceof IShearable)
        {
            IShearable target = (IShearable) block;
            if (target.isShearable(null, world, x, y, z))
            {
                ArrayList<ItemStack> drops = target.onSheared(null, world, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, null));
                Random rand = new Random();
                for (ItemStack stack : drops)
                {
                    float f = 0.7F;
                    double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(world, x + d, y + d1, z + d2, stack);
                    entityitem.delayBeforeCanPickup = 10;
                    world.spawnEntityInWorld(entityitem);
                }
            }
        }
    }

    /**
     * Gets the amount of entities within the given cube with the given radius.
     *
     * @param pos      The position
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The amount of entities
     */
    public int countEntities(ScriptablePosition pos, float radius, String entities)
    {
        return countEntities((int) pos.x, (int) pos.y, (int) pos.z, radius, entities);
    }

    /**
     * Gets the amount of entities within the given cube with the given radius.
     *
     * @param x        The x-coordinate
     * @param y        The y-coordinate
     * @param z        The z-coordinate
     * @param radius   The radius of the lookup cube
     * @param entities The entities to search. Allowed values are 'hostile', 'animal', 'mob', 'player', 'item', 'all', any entityID and any
     *                 entity name. You can give multiple values by dividing them with a ','
     * @return The amount of entities
     */
    public int countEntities(int x, int y, int z, float radius, String entities)
    {
        int amount = 0;
        for (String entity : entities.split(","))
        {
            Class<? extends Entity> mobClass = null;
            if (entity.equals("hostile"))
            {
                mobClass = EntityMob.class;
            } else if (entity.equals("animal"))
            {
                mobClass = EntityAnimal.class;
            } else if (entity.equals("mob"))
            {
                mobClass = EntityLiving.class;
            } else if (entity.equals("player"))
            {
                mobClass = EntityPlayer.class;
            } else if (entity.equals("item"))
            {
                mobClass = EntityItem.class;
            } else if (entity.equals("all"))
            {
                mobClass = null;
            } else
            {
                if (entity.matches("[0-9]+"))
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(EntityList.getStringFromID(Integer.parseInt(entity)));
                } else
                {
                    mobClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(entity);
                }
            }
            AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(x + 0.5 - radius, y + 0.5 - radius, z + 0.5 - radius, x + 0.5 + radius, y + 0.5 + radius, z + 0.5 + radius);
            amount += mobClass == null ? world.getEntitiesWithinAABBExcludingEntity((Entity) null, axis).size() : world.getEntitiesWithinAABB(mobClass, axis).size();
        }
        return amount;
    }

    /**
     * Gets the light level of the block at the given position.
     *
     * @param position The block's position
     * @return
     */
    public int getBlockLightLevel(ScriptablePosition position)
    {
        return getBlockLightLevel((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the light level of the block at the given position.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     * @return
     */
    public int getBlockLightLevel(int x, int y, int z)
    {
        return world.getBlockLightValue(x, y, z);
    }

    /**
     * Sends a chat message to all players in the world.
     *
     * @param message The message to send.
     */
    public void sendMessageToAllPlayers(String message)
    {
        if (!world.isRemote)
        {
            for (Object o : world.playerEntities)
            {
                if (o instanceof EntityPlayer)
                {
                    ((EntityPlayer) o).addChatComponentMessage(new ChatComponentText(message));
                }
            }
        }
    }

    /**
     * Sends a chat message to the given player.
     *
     * @param player  The player's username
     * @param message The message to send
     */
    public void sendMessageToPlayer(String player, String message)
    {
        if (!world.isRemote)
        {
            EntityPlayer entityPlayer = world.getPlayerEntityByName(player);
            if (entityPlayer != null)
            {
                entityPlayer.addChatComponentMessage(new ChatComponentText(message));
            }
        }
    }

    /**
     * Gets the type of the block with the given id. This works for all CS2 blocks and for vanilla buttons, chests, doors, fences, fence
     * gates, fluids, furnaces, ladders, panes, pressure plates, slabs, torches, trap doors and walls. It may work some blocks from other
     * non-CS2 mods.
     *
     * @param position The position
     * @return The type of the block or unknown if the block doesn't exist or isn't supported. The types equal to the CS2 block types.
     */
    public String getBlockType(ScriptablePosition position)
    {
        return getBlockType((int) position.x, (int) position.y, (int) position.z);
    }

    /**
     * Gets the type of the block with the given id. This works for all CS2 blocks and for vanilla buttons, carpets, chests, doors, fences, fence
     * gates, fluids, furnaces, ladders, panes, pressure plates, slabs, torches, trap doors and walls. It may work some blocks from other
     * non-CS2 mods.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     * @return The type of the block or unknown if the block doesn't exist or isn't supported. The types equal to the CS2 block types.
     */
    public String getBlockType(int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block == null)
            return "unknown";
        //else if (block instanceof ICSBlock)
        //    return ((ICSBlock) block).getAttributes().type.name;
        else if (block instanceof BlockButton)
            return "button";
        else if (block instanceof BlockCarpet)
            return "carpet";
        else if (block instanceof BlockChest)
            return "chest";
        else if (block instanceof BlockDoor)
            return "door";
        else if (block instanceof BlockFence)
            return "fence";
        else if (block instanceof BlockFenceGate)
            return "fenceGate";
        else if (block instanceof BlockFluidBase)
            return "fluid";
        else if (block instanceof BlockFurnace)
            return "furnace";
        else if (block instanceof BlockLadder)
            return "ladder";
        else if (block instanceof BlockPane)
            return "pane";
        else if (block instanceof BlockBasePressurePlate)
            return "pressurePlate";
        else if (block instanceof BlockSlab)
            return "slab";
        else if (block instanceof BlockTorch)
            return "torch";
        else if (block instanceof BlockTrapDoor)
            return "trapDoor";
        else if (block instanceof BlockWall)
            return "wall";

        return "unknown";
    }

    /**
     * Spawns a particle at the given position and the given velocity
     *
     * @param name     The name of the particle: bubble, suspended, depthsuspend, townaura, crit, magicCrit, smoke,
     *                 mobSpell, mobSpellAmbient, spell, instantSpell, witchMagic, note, portal, enchantmenttable,
     *                 explode, flame, lava, footstep, splash, wake, largesmoke, cloud, reddust, snowballpoof, dripWater,
     *                 dripLava, snowshovel, slime, heart, angryVillager, happyVillager
     * @param position The position
     * @param velX     The x velocity
     * @param velY     The y velocity
     * @param velZ     The z velocity
     */
    public void spawnParticle(String name, ScriptablePosition position, double velX, double velY, double velZ)
    {
        spawnParticle(name, position.x, position.y, position.z, velX, velY, velZ);
    }

    /**
     * Spawns a particle at the given position and the given velocity
     *
     * @param name The name of the particle: bubble, suspended, depthsuspend, townaura, crit, magicCrit, smoke,
     *             mobSpell, mobSpellAmbient, spell, instantSpell, witchMagic, note, portal, enchantmenttable,
     *             explode, flame, lava, footstep, splash, wake, largesmoke, cloud, reddust, snowballpoof, dripWater,
     *             dripLava, snowshovel, slime, heart, angryVillager, happyVillager
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param velX The x velocity
     * @param velY The y velocity
     * @param velZ The z velocity
     */
    public void spawnParticle(String name, double x, double y, double z, double velX, double velY, double velZ)
    {
        world.spawnParticle(name, x, y, z, velX, velY, velZ);
    }

}

