package cubex2.cs3.item;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemCS extends Item
{
    protected BaseContentPack pack;
    private WrappedItem wrappedItem;

    public ItemCS(WrappedItem item)
    {
        super();
        this.pack = item.getPack();
        this.wrappedItem = item;
    }

    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return wrappedItem.getItemStackLimit(stack);
    }

    @Override
    public boolean isFull3D()
    {
        return wrappedItem.isFull3D();
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return wrappedItem.hasEffect(stack);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return wrappedItem.getCreativeTab();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return wrappedItem.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return wrappedItem.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return wrappedItem.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
    {
        wrappedItem.useItemOnEntity(stack, player, target);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
        return wrappedItem.onBlockDestroyed(stack, world, block, x, y, z, living);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase living1, EntityLivingBase living2)
    {
        return wrappedItem.hitEntity(stack, living1, living2);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return wrappedItem.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slotId, boolean isCurrentItem)
    {
        wrappedItem.onUpdate(stack, world, entity, slotId, isCurrentItem);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        wrappedItem.onCreated(stack, world, player);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int tickCount)
    {
        wrappedItem.onUsingTick(stack, player, tickCount);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int tickCount)
    {
        wrappedItem.onPlayerStoppedUsing(stack, world, player, tickCount);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        return wrappedItem.onDroppedByPlayer(stack, player);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
    {
        return wrappedItem.onBlockStartBreak(stack, x, y, z, player);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return wrappedItem.onEaten(stack, world, player);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return wrappedItem.getMaxItemUseDuration(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        wrappedItem.addInformation(stack, player, list, advanced);
    }
}
