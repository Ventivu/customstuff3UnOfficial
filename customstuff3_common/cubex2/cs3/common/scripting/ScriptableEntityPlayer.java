package cubex2.cs3.common.scripting;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.common.inventory.Inventory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ScriptableEntityPlayer extends ScriptableEntityLiving
{

    private EntityPlayer player;
    private Inventory inventory;

    public ScriptableEntityPlayer(EntityPlayer player)
    {
        super(player);
        this.player = player;
        inventory = new Inventory(player.inventory);
    }

    /**
     * Opens a gui for the player. DOESN'T WORK ATM.
     *
     * @param guiName
     *         The name of the gui used for the name-attribute in the gui-file
     * @param position
     *         The position of the block to open a gui for.
     */
    public void openGui(String guiName, ScriptablePosition position)
    {
        openGui(guiName, (int) position.x, (int) position.y, (int) position.z);
    }

    // TODO: make it work
    /**
     * Opens a gui for the player. DOESN'T WORK ATM.
     *
     * @param guiName
     *         The name of the gui used for the name-attribute in the gui-file
     * @param x
     *         The x-coordinate of the block
     * @param y
     *         The y-coordinate of the block
     * @param z
     *         The z-coordinate of the block
     */
    public void openGui(String guiName, int x, int y, int z)
    {
        /*
        if (player.worldObj.isRemote)
            return;
        GuiHandler.openGui(player, mod, guiName, player.worldObj, x, y, z);*/
    }

    /**
     * Sets the player's hunger
     *
     * @param hunger
     *         The hunger to set
     */
    public void setHunger(int hunger)
    {
        ReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(), hunger, 0);
    }

    /**
     * Gets the player's hunger
     *
     * @return The player's hunger
     */
    public int getHunger()
    {
        return player.getFoodStats().getFoodLevel();
    }

    /**
     * Sets the player's saturation
     *
     * @param saturation
     *         The saturation to set
     */
    public void setSaturation(double saturation)
    {
        player.getFoodStats().setFoodSaturationLevel((float) saturation);
    }

    /**
     * Gets the player's saturation
     *
     * @return The player's saturation
     */
    public double getSaturation()
    {
        return player.getFoodStats().getSaturationLevel();
    }

    /**
     * Sets how high this entity can step up when running into a block to try to get over it
     *
     * @param f
     *         The step height
     */
    public void setStepHeight(double f)
    {
        player.stepHeight = (float) f;
    }

    /**
     * Gets the step height
     *
     * @return The player's step height
     */
    public double getStepHeight()
    {
        return player.stepHeight;
    }

    /**
     * Displays a message for the player.
     *
     * @param message
     *         The message to display
     */
    public void sendMessage(String message)
    {
        if (!player.worldObj.isRemote)
        {
            player.addChatMessage(message);
        }
    }

    /**
     * Makes the player send a chat message as if the message was sent with the chat console. Commands will work this way.
     *
     * @param chat
     *         The chat message
     */
    public void sendChat(String chat)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc != null && !player.worldObj.isRemote)
        {
            mc.thePlayer.sendChatMessage(chat);
        }
    }

    /**
     * Sets the current item in use, so onUsing is called.
     */
    public void setItemInUse()
    {
        player.setItemInUse(player.getCurrentEquippedItem(), player.getCurrentEquippedItem().getItem().getMaxItemUseDuration(player.getCurrentEquippedItem()));
    }

    /**
     * Clears the player's inventory
     */
    public void clearInventory()
    {
        inventory.clear();
    }

    /**
     * Fills the player's inventory. Existing items won't be replaced.
     *
     * @param alias
     *         The alias name
     * @return The amount added to the inventory
     */
    public int fillInventory(String alias)
    {
        return inventory.fill(pack.aliasManager.getAlias(alias));
    }

    /**
     * Adds items to the player's iventory if there's space for them.
     *
     * @param alias
     *         The alias name
     * @param count
     *         The item's stack size
     * @return The amount added to the inventory
     */
    public int add(String alias, int count)
    {
        return inventory.add(pack.aliasManager.getAlias(alias), count, 35);
    }

    /**
     * Removes items from the player's inventory
     *
     * @param alias
     *         The alias name
     * @param count
     *         The item's stack size
     * @return The amount removed from the inventory
     */
    public int remove(String alias, int count)
    {
        return inventory.remove(pack.aliasManager.getAlias(alias), count);
    }

    /**
     * Clears an inventory slot
     *
     * @param slotId
     *         The slot's id
     */
    public void clearSlot(int slotId)
    {
        inventory.clearSlot(slotId);
    }

    /**
     * Fills an inventory slot if it isn't empty
     *
     * @param slotId
     *         The slot's id
     * @return The amount added to the slot
     */
    public int fillSlot(int slotId)
    {
        return inventory.fillSlot(slotId);
    }

    /**
     * Sets an inventory slot
     *
     * @param slotId
     *         The slot's id
     * @param alias
     *         The alias name
     * @param count
     *         The item's stack size
     * @return The amount added or removed if there is already the same item in this slot or count if the slot is empty or an other item is
     * in it
     */
    public int setSlot(int slotId, String alias, int count)
    {
        return inventory.setSlot(slotId, pack.aliasManager.getAlias(alias), count);
    }

    /**
     * Adds items to an inventory slot if it isn't empty
     *
     * @param slotId
     *         The slot's id
     * @param count
     *         The count to add
     * @return The amount added to the slot
     */
    public int addToSlot(int slotId, int count)
    {
        return inventory.addToSlot(slotId, count);
    }

    /**
     * Removes items from an inventory slot
     *
     * @param slotId
     *         The slot's id
     * @param count
     *         The count of items to remove
     * @return The amount removed from the slot
     */
    public int removeFromSlot(int slotId, int count)
    {
        return inventory.removeFromSlot(slotId, count);
    }

    /**
     * Damages an item in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @param count
     *         The damage to add to the item
     */
    public void damageItem(int slotId, int count)
    {
        inventory.damageItem(slotId, count);
    }

    /**
     * Fully repairs an item in the player's inventory
     *
     * @param slotId
     *         The slot's id
     */
    public void repairItem(int slotId)
    {
        inventory.repairItem(slotId);
    }

    /**
     * Repairs an item in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @param count
     *         The damage to remove from the item
     */
    public void repairItem(int slotId, int count)
    {
        inventory.repairItem(slotId, count);
    }

    /**
     * Gets the item's id in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @return The item's id or -1 if the slot is empty
     */
    public int getItemId(int slotId)
    {
        return inventory.getId(slotId);
    }

    /**
     * Gets the item's stack size in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @return The item's stack size or -1 if the slot is empty
     */
    public int getItemStackSize(int slotId)
    {
        return inventory.getStackSize(slotId);
    }

    /**
     * Gets the maximum stack size of an item in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @return The item's maximum stack size or -1 if the slot is empty
     */
    public int getItemMaxStackSize(int slotId)
    {
        return inventory.getMaxStackSize(slotId);
    }

    /**
     * Gets the item's metadata in the player's inventory
     *
     * @param slotId
     *         The slot's id
     * @return The item's metadata or -1 if the slot is empty
     */
    public int getItemMetadata(int slotId)
    {
        return inventory.getDamage(slotId);
    }

    /**
     * Checks if the itemstack in the given slot has any enchantment
     *
     * @param slotId
     *         The slot's id
     * @return ture if the itemstack has any enchantment
     */
    public boolean hasEnchantment(int slotId)
    {
        return inventory.hasEnchantment(slotId, -1);
    }

    /**
     * Checks if the itemstack has the given enchantment enchantment
     *
     * @param slotId
     *         The slot's id
     * @param id
     *         The id of the enchantment
     * @return true if the itemstack in the given slot has the given enchantment
     */
    public boolean hasEnchantment(int slotId, int id)
    {
        return inventory.hasEnchantment(slotId, id);
    }

    /**
     * Checks if the itemstack has the given enchantment enchantment
     *
     * @param slotId
     *         The slot's id
     * @param id
     *         The id of the enchantment
     * @param level
     *         The level of the enchantment
     * @return true if the itemstack in the given slot has the given enchantment and the given level
     */
    public boolean hasEnchantment(int slotId, int id, int level)
    {
        return inventory.hasEnchantment(slotId, id, level);
    }

    /**
     * Removes all enchantments from the itemstack in the given slot
     *
     * @param slotId
     *         The slot's id
     */
    public void clearEnchantments(int slotId)
    {
        inventory.clearEnchantments(slotId);
    }

    /**
     * Adds an enchantment to the itemstack in the given slot
     *
     * @param slotId
     *         The slot's id
     * @param id
     *         The id of the enchantment
     * @param level
     *         The level of the enchantment
     */
    public void addEnchantment(int slotId, int id, int level)
    {
        inventory.addEnchantment(slotId, id, level);
    }

    /**
     * Removes an enchantment from the itemstack in the given slot
     *
     * @param slotId
     *         The slot's id
     * @param id
     *         The id of the enchantment
     */
    public void removeEnchantment(int slotId, int id)
    {
        inventory.removeEnchantment(slotId, id);
    }

    /**
     * Gets the count of a item in the player's inventory
     *
     * @param alias
     *         The alias name
     * @return The count of the item
     */
    public int getItemCount(String alias)
    {
        return inventory.getItemCount(pack.aliasManager.getAlias(alias));
    }

    /**
     * Gets the id of the slot the player is currently using
     *
     * @return The current slot's id
     */
    public int getCurrentSlot()
    {
        return player.inventory.currentItem;
    }

    /**
     * Places a block
     *
     * @param position
     *         The position
     * @param side
     *         On what side at position the block should be added
     * @param id
     *         The block's id
     * @param metadata
     *         The block's metadata
     * @param flag
     *         If true the function will uses blocks from the inventory and won't place any block if the required block isn't in the
     *         player's inventory
     * @return True if the block has been placed, false otherwise
     */
    public boolean placeBlock(ScriptablePosition position, int side, int id, int metadata, boolean flag)
    {
        return placeBlock((int) position.x, (int) position.y, (int) position.z, side, id, metadata, flag);
    }

    // TODO: Make it work
    /**
     * Places a block. DOESN'T WORK ATM.
     *
     * @param x
     *         The x coordinate
     * @param y
     *         The y coordinate
     * @param z
     *         The z coordinate
     * @param side
     *         On what side at position the block should be added
     * @param id
     *         The block's id
     * @param metadata
     *         The block's metadata
     * @param flag
     *         If true the function will uses blocks from the inventory and won't place any block if the required block isn't in the
     *         player's inventory
     * @return True if the block has been placed, false otherwise
     */
    public boolean placeBlock(int x, int y, int z, int side, int id, int metadata, boolean flag /*
                                                                                                 * TODO : find name
                                                                                                 */)
    {
        return false;
        /*World world = player.worldObj;
        ItemStack stack;
        if (flag)
        {
            stack = inventory.findItem(id, metadata);
            if (stack == null)
                return false;
        }
        else
        {
            stack = new ItemStack(id, 1, metadata);
        }

        int blockID = world.getBlockId(x, y, z);

        if (blockID == Block.snow.blockID)
        {
            side = 1;
        }
        else if (blockID != Block.vine.blockID && blockID != Block.tallGrass.blockID && blockID != Block.deadBush.blockID && (Block.blocksList[blockID] == null || !Block.blocksList[blockID].isBlockReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (stack.stackSize == 0)
            return false;
        else if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;
        else if (y == 255 && Block.blocksList[id].blockMaterial.isSolid())
            return false;
        else if (world.canPlaceEntityOnSide(id, x, y, z, false, side, player, stack))
        {
            Block block1 = Block.blocksList[id];

            int meta = Block.blocksList[id].onBlockPlaced(world, x, y, z, side, 0, 0, 0, stack.getItemDamage());

            if (placeBlockAt(id, stack, player, world, x, y, z, side, meta))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block1.stepSound.getPlaceSound(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        }
        else
            return false;*/
    }

    private boolean placeBlockAt(int id, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, int metadata)
    {
        if (!world.setBlock(x, y, z, id, metadata, 3))
            return false;

        if (world.getBlockId(x, y, z) == id)
        {
            Block.blocksList[id].onBlockPlacedBy(world, x, y, z, player, stack);
            Block.blocksList[id].onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }

    private MovingObjectPosition getMop()
    {
        Vec3 vec1 = player.worldObj.getWorldVec3Pool().getVecFromPool(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 vec2 = player.getLookVec();
        Vec3 vec3 = vec1.addVector(vec2.xCoord * 1000, vec2.yCoord * 1000, vec2.zCoord * 1000);
        MovingObjectPosition mop = player.worldObj.clip(vec1, vec3);
        if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
            return mop;
        return null;
    }

    /**
     * Gets the x-coordinate of the block that the player is looking on.
     *
     * @return The x-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookX()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockX : -1;
    }

    /**
     * Gets the y-coordinate of the block that the player is looking on.
     *
     * @return The y-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookY()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockY : -1;
    }

    /**
     * Gets the z-coordinate of the block that the player is looking on.
     *
     * @return The z-coordinate or -1 if the player isn't looking on any block
     */
    public int getLookZ()
    {
        MovingObjectPosition mop = getMop();
        return mop != null ? mop.blockZ : -1;
    }

    /**
     * Adds experience to the player
     *
     * @param amount
     *         The amount of xp
     */
    public void addExperience(int amount)
    {
        player.addExperience(amount);
    }

    /**
     * Removes experience from the player
     *
     * @param amount
     *         The amount of xp to remove
     */
    public void removeExperience(int amount)
    {
        player.experienceTotal -= amount;
        if (player.experienceTotal < 0)
        {
            player.experienceTotal = 0;
        }
    }

    /**
     * Sets the player's experience
     *
     * @param amount
     *         The new xp of the player
     */
    public void setExperience(int amount)
    {
        player.experienceTotal = amount;
        if (player.experienceTotal < 0)
        {
            player.experienceTotal = 0;
        }
    }

    /**
     * Adds level to the player
     *
     * @param amount
     *         The amount of levels
     */
    public void addExperienceLevel(int amount)
    {
        player.addExperienceLevel(amount);
    }

    /**
     * Removes level from the player
     *
     * @param amount
     *         The amount of levels
     */
    public void removeExperienceLevel(int amount)
    {
        player.addExperienceLevel(-amount);
    }

    /**
     * Sets the player's level
     *
     * @param level
     *         The level
     */
    public void setExperienceLevel(int level)
    {
        player.experienceLevel = level;
    }

    /**
     * Gets the player's experience
     *
     * @return
     */
    public int getExperience()
    {
        return player.experienceTotal;
    }

    /**
     * Gets the player's level
     *
     * @return
     */
    public int getExperienceLevel()
    {
        return player.experienceLevel;
    }

    /**
     * Gets the player's horizontal look angle
     *
     * @return
     */
    public float getHorizontalAngle()
    {
        return MathHelper.wrapAngleTo180_float(player.rotationYaw);
    }

    /**
     * Gets the player's vertical look angle
     *
     * @return
     */
    public float getVerticalAngle()
    {
        return MathHelper.wrapAngleTo180_float(player.rotationPitch);
    }

    /**
     * Gets the player's username
     *
     * @return
     */
    public String getUsername()
    {
        return player.username;
    }

    /**
     * Check if the player is sneaking
     *
     * @return
     */
    public boolean isSneaking()
    {
        return player.isSneaking();
    }

    /**
     * Checks if the player is sprinting
     *
     * @return
     */
    public boolean isSprinting()
    {
        return player.isSprinting();
    }

    /**
     * Gets a stack's int data.
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @return The data or -1 if the slot is empty or the data doesn't exist.
     */
    public int getStackIntData(int slotId, String name)
    {
        return inventory.getIntData(slotId, name);
    }

    /**
     * Gets a stack's float data.
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @return The data or -1.0 if the slot is empty or the data doesn't exist.
     */
    public float getStackFloatData(int slotId, String name)
    {
        return inventory.getFloatData(slotId, name);
    }

    /**
     * Gets a stack's string data
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @return The data or null if the slot is empty or the data doesn't exist.
     */
    public String getStackStringData(int slotId, String name)
    {
        return inventory.getStringData(slotId, name);
    }

    /**
     * Sets the stack's int data
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @param data
     *         The data
     */
    public void setStackIntData(int slotId, String name, int data)
    {
        inventory.setIntData(slotId, name, data);
    }

    /**
     * Sets the stack's float data
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @param data
     *         The data
     */
    public void setStackFloatData(int slotId, String name, float data)
    {
        inventory.setFloatData(slotId, name, data);
    }

    /**
     * Sets the stack's string data
     *
     * @param slotId
     *         The slot's id
     * @param name
     *         The name of the data
     * @param data
     *         The data
     */
    public void setStackStringData(int slotId, String name, String data)
    {
        inventory.setStringData(slotId, name, data);
    }

    /**
     * Sets the player's max health
     *
     * @param value
     *         The new max health
     */
    public void setMaxHealth(int value)
    {
        // TODO maxHealth
        // player.maxHealth = value < 1 ? 1 : value;
    }

    /**
     * Gets the player's max health
     */
    @Override
    public float getMaxHealth()
    {
        return player.getMaxHealth();
    }

    /**
     * Checks if the itemstack int the given slot can efficiently break the given block. It actually checks if it can harvest the block and
     * if its efficiency against the block is higher than 1.0.
     *
     * @param slotId
     *         The slot's id
     * @param blockId
     *         The block's id
     * @param metadata
     *         The block's metadata
     * @return true if the block can efficiently be breaked.
     */
    public boolean canEfficientlyBreak(int slotId, int blockId, int metadata)
    {
        ItemStack stack = player.inventory.getStackInSlot(slotId);
        return stack != null ? stack.canHarvestBlock(Block.blocksList[blockId]) && stack.getItem().getStrVsBlock(stack, Block.blocksList[blockId], metadata) > 1.0f : false;
    }

    /**
     * Checks if the itemstack in the given slot can harvest the given block.
     *
     * @param slotId
     *         The slot's id
     * @param blockId
     *         The block's id
     * @param metadata
     *         The block's metadata
     * @return true if the block can efficiently be breaked.
     */
    public boolean canHarvest(int slotId, int blockId, int metadata)
    {
        ItemStack stack = player.inventory.getStackInSlot(slotId);
        return stack != null ? stack.canHarvestBlock(Block.blocksList[blockId]) : false;
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        player.swingItem();
    }

    /**
     * Get if the player is in creative mode
     *
     * @return true if the player is in creative mode false otherwise
     */
    public boolean isInCreative()
    {
        return player.capabilities.isCreativeMode;
    }

}
