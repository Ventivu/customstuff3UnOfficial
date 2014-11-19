package cubex2.cs3.block;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockCS extends Block
{
    protected BaseContentPack pack;
    private WrappedBlock wrappedBlock;

    public BlockCS(WrappedBlock block)
    {
        super(block.container.material);
        pack = block.getPack();
        wrappedBlock = block;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        wrappedBlock.updateTick(world, x, y, z, rand);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willDestroy)
    {
        boolean removed = world.setBlockToAir(x, y, z);
        wrappedBlock.removedByPlayer(world, player, x, y, z);
        return removed;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        wrappedBlock.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        wrappedBlock.onBlockAdded(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        wrappedBlock.onBlockBreak(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        return wrappedBlock.blockActivated(world, x, y, z, player, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        wrappedBlock.onEntityWalking(world, x, y, z, entity);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int metadata)
    {
        wrappedBlock.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ);
        return metadata;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        wrappedBlock.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        wrappedBlock.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        wrappedBlock.onBlockPlacedBy(world, x, y, z, living);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        wrappedBlock.onFallenUpon(world, x, y, z, entity, fallDistance);
    }

    public boolean onBonemeal(World world, int x, int y, int z, EntityPlayer player)
    {
        return wrappedBlock.onBonemeal(world, x, y, z, player);
    }

    @Override
    public int tickRate(World world)
    {
        return wrappedBlock.tickRate(world);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return wrappedBlock.getLightValue(world, x, y, z);
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return wrappedBlock.getFlammability(world, x, y, z, face);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return wrappedBlock.getFireSpreadSpeed(world, x, y, z, face);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return wrappedBlock.getCreativeTabToDisplayOn();
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return wrappedBlock.getIcon(side, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        wrappedBlock.registerBlockIcons(iconRegister);
    }
}
