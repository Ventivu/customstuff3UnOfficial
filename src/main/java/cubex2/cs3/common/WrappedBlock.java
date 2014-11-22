package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.block.EnumBlockType;
import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.util.JavaScriptHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class WrappedBlock extends BaseContent
{
    public Block block;
    public Item blockItem;
    public BlockAttributes container;

    private String name;
    private EnumBlockType type;

    public WrappedBlock(String name, EnumBlockType type, BaseContentPack pack)
    {
        super(pack);
        this.name = name;
        this.type = type;
    }

    public WrappedBlock(BaseContentPack pack)
    {
        super(pack);
    }

    public String getName()
    {
        return name;
    }

    public EnumBlockType getType()
    {
        return type;
    }

    private void initBlock()
    {
        block.setBlockName(name);
        block.setLightOpacity(container.opacity);
        blockItem.setMaxStackSize(container.maxStack);
        block.setStepSound(container.stepSound);
        block.slipperiness = container.slipperiness;


        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        p.put("tile." + getName() + ".name", container.displayName);
    }

    @Override
    public void apply()
    {
        if (block != null)
        {
            initBlock();
        }

        super.apply();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setString("Type", type.name);

        NBTTagCompound attributesCompound = new NBTTagCompound();
        if (container != null)
        {
            container.writeToNBT(attributesCompound);
        }
        compound.setTag("Attributes", attributesCompound);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumBlockType.get(compound.getString("Type"));

        container = type.createAttributeContainer(this);
        container.loadFromNBT(compound.getCompoundTag("Attributes"));

        block = type.createBlock(this);
        blockItem = GameData.getItemRegistry().getObject(pack.id + ":" + getName());

        return true;
    }

    /* Block methods */
    public boolean isOpaqueCube()
    {
        return !container.transparent && !container.semiTransparent;
    }

    public int getRenderBlockPass()
    {
        return container.semiTransparent ? 1 : 0;
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        if (!container.tileTransparent)
        {
            Block block = world.getBlock(x, y, z);
            return block != this.block && shouldSideBeRenderedDefault(world, x, y, z, side);
        }
        return shouldSideBeRenderedDefault(world, x, y, z, side);
    }

    private boolean shouldSideBeRenderedDefault(IBlockAccess world, int x, int y, int z, int side)
    {
        return side == 0 && block.getBlockBoundsMinY() > 0.0D ? true : side == 1 && block.getBlockBoundsMaxY() < 1.0D ? true : side == 2 && block.getBlockBoundsMinZ() > 0.0D ? true : side == 3 && block.getBlockBoundsMaxZ() < 1.0D ? true : side == 4 && block.getBlockBoundsMinX() > 0.0D ? true : side == 5 && block.getBlockBoundsMaxX() < 1.0D ? true : !world.getBlock(x, y, z).isOpaqueCube();
    }

    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (container.onUpdate != null && container.onUpdate.script != null)
        {
            ITriggerData data = new TriggerData("onUpdate", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onUpdate.script, data, pack);
            world.scheduleBlockUpdate(x, y, z, block, block.tickRate(world));
        }
    }

    public void removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if (container.onDestroyedByPlayer != null && container.onDestroyedByPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onDestroyedByPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onDestroyedByPlayer.script, data, pack);
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        if (container.onNeighborChange != null && container.onNeighborChange.script != null)
        {
            ITriggerData data = new TriggerData("onNeighborChange", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onNeighborChange.script, data, pack);
        }
        if (container.onRedstoneSignal != null && container.onRedstoneSignal.script != null)
        {
            TriggerData data = new TriggerData("onRedstoneSignal", TriggerType.BLOCK, world, x, y, z);
            if (world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                data.setRedstoneSignal(true);
            } else
            {
                data.setRedstoneSignal(false);
            }
            JavaScriptHelper.executeTrigger(container.onRedstoneSignal.script, data, pack);
        }
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        /*if (container.hasTileEntity)
        {
            world.setTileEntity(x, y, z, block.createTileEntity(world, meta));
        }*/

        if (container.onAdded != null && container.onAdded.script != null)
        {
            ITriggerData data = new TriggerData("onAdded", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onAdded.script, data, pack);
        }
        if (container.onUpdate != null && container.onUpdate.script != null)
        {
            world.scheduleBlockUpdate(x, y, z, block, block.tickRate(world));
        }
        if (container.onRedstoneSignal != null && container.onRedstoneSignal.script != null)
        {
            TriggerData data = new TriggerData("onRedstoneSignal", TriggerType.BLOCK, world, x, y, z);
            if (world.isBlockIndirectlyGettingPowered(x, y, z))
            {
                data.setRedstoneSignal(true);
            } else
            {
                data.setRedstoneSignal(false);
            }
            JavaScriptHelper.executeTrigger(container.onRedstoneSignal.script, data, pack);
        }
    }

    public void onBlockBreak(World world, int x, int y, int z, Block block, int meta)
    {
        if (container.onBreak != null && container.onBreak.script != null)
        {
            ITriggerData data = new TriggerData("onBreak", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onBreak.script, data, pack);
        }
        /*if (container.hasTileEntity)
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof IInventory)
            {
                GeneralHelper.dropItems((IInventory) te, world, x, y, z);
            }
            world.removeTileEntity(x, y, z);
        }*/
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ)
    {
        if (container.onActivated != null && container.onActivated.script != null)
        {
            ITriggerData data = new TriggerData("onActivated", TriggerType.BLOCK, world, x, y, z).setPlayer(player).setSideAndHit(facing, hitX, hitY, hitZ);
            return JavaScriptHelper.executeTrigger(container.onActivated.script, data, pack, false);
        }
        return false;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityPlayer && container.onWalkingPlayer != null && container.onWalkingPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onWalkingPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer((EntityPlayer) entity);
            JavaScriptHelper.executeTrigger(container.onWalkingPlayer.script, data, pack);
        } else if (entity instanceof EntityLiving && container.onWalkingLiving != null && container.onWalkingLiving.script != null)
        {
            ITriggerData data = new TriggerData("onWalkingLiving", TriggerType.BLOCK, world, x, y, z).setLiving((EntityLiving) entity);
            JavaScriptHelper.executeTrigger(container.onWalkingLiving.script, data, pack);
        } else if (container.onWalking != null && container.onWalking.script != null)
        {
            ITriggerData data = new TriggerData("onWalking", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onWalking.script, data, pack);
        }
    }

    public void onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ)
    {
        if (container.onPlaced != null && container.onPlaced.script != null)
        {
            ITriggerData data = new TriggerData("onPlaced", TriggerType.BLOCK, world, x, y, z);
            JavaScriptHelper.executeTrigger(container.onPlaced.script, data, pack);
        }
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        if (container.onClicked != null && container.onClicked.script != null)
        {
            ITriggerData data = new TriggerData("onClicked", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onClicked.script, data, pack);
        }
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityPlayer && container.onCollidedWithPlayer != null && container.onCollidedWithPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onCollidedWithPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer((EntityPlayer) entity);
            JavaScriptHelper.executeTrigger(container.onCollided.script, data, pack);
        } else if (entity instanceof EntityLivingBase && container.onCollidedWithLiving != null && container.onCollidedWithLiving.script != null)
        {
            ITriggerData data = new TriggerData("onCollidedWithLiving", TriggerType.BLOCK, world, x, y, z).setLiving((EntityLivingBase) entity);
            JavaScriptHelper.executeTrigger(container.onCollided.script, data, pack);
        } else if (container.onCollided != null && container.onCollided.script != null)
        {
            ITriggerData data = new TriggerData("onCollided", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onCollided.script, data, pack);
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living)
    {
        if (living instanceof EntityPlayer && container.onPlacedByPlayer != null && container.onPlacedByPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onPlacedByPlayer", TriggerType.BLOCK, world, x, y, z).setPlayer((EntityPlayer) living);
            JavaScriptHelper.executeTrigger(container.onPlacedByPlayer.script, data, pack);
        } else if (container.onPlacedBy != null && container.onPlacedBy.script != null)
        {
            ITriggerData data = new TriggerData("onPlacedBy", TriggerType.BLOCK, world, x, y, z).setLiving(living);
            JavaScriptHelper.executeTrigger(container.onPlacedBy.script, data, pack);
        }
    }

    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        if (container.onFallenUpon != null && container.onFallenUpon.script != null)
        {
            ITriggerData data = new TriggerData("onFallenUpon", TriggerType.BLOCK, world, x, y, z).setEntity(entity);
            JavaScriptHelper.executeTrigger(container.onFallenUpon.script, data, pack);
        }
    }

    public boolean onBonemeal(World world, int x, int y, int z, EntityPlayer player)
    {
        if (container.onBonemeal != null && container.onBonemeal.script != null)
        {
            ITriggerData data = new TriggerData("onBonemeal", TriggerType.BLOCK, world, x, y, z).setPlayer(player);
            JavaScriptHelper.executeTrigger(container.onBonemeal.script, data, pack);
        }
        return false;
    }

    public int tickRate(World world)
    {
        return container.tickrate;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return container.light;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return container.flammability;
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return container.fireSpreadSpeed;
    }


    /* Methods from block item */

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return !container.leaveContainerItem;
    }

    public ItemStack getContainerItem(ItemStack stack)
    {
        return container.containerItem.copy();
    }

    public IIcon getIcon(int side, int meta)
    {
        return container.getTexture(side).icon;
    }

    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < 6; i++)
        {
            container.getTexture(i).setIcon(iconRegister);
        }
    }

    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return container.creativeTab;
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        if (side == 0 && !container.canPlaceOnCeiling)
            return false;
        if (side == 1 && !container.canPlaceOnFloor)
            return false;

        return !(side > 1 && !container.canPlaceOnWall);
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        if (container.information == null) return;

        String[] split = container.information.split("\n");
        for (int i = 0; i < split.length; i++)
        {
            list.add(split[i]);
        }
    }
}
