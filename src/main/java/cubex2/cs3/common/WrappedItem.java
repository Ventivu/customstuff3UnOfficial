package cubex2.cs3.common;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.api.scripting.ITriggerData;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.common.scripting.TriggerData;
import cubex2.cs3.item.EnumItemType;
import cubex2.cs3.item.attributes.ItemAttributes;
import cubex2.cs3.util.GeneralHelper;
import cubex2.cs3.util.JavaScriptHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WrappedItem extends BaseContent
{
    public Item item;
    public ItemAttributes container;

    private String name;
    private EnumItemType type;


    public WrappedItem(String name, EnumItemType type, BaseContentPack pack)
    {
        super(pack);
        this.name = name;
        this.type = type;
    }

    public WrappedItem(BaseContentPack pack)
    {
        super(pack);
    }

    public String getName()
    {
        return name;
    }

    public EnumItemType getType()
    {
        return type;
    }

    private void initItem()
    {
        item.setUnlocalizedName(name);
        if (container.icon != null)
        {
            item.setTextureName(container.icon.iconString);
        }

        Map<String, Properties> modLangData = ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), "modLanguageData");
        Properties p = modLangData.get("en_US");
        p.put("item." + name + ".name", container.displayName);
    }

    @Override
    public void apply()
    {
        if (item != null)
        {
            initItem();
        }

        super.apply();
    }

    @Override
    public void edit()
    {
        super.edit();
    }

    @Override
    public void remove()
    {
        super.remove();
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
    public void readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        type = EnumItemType.get(compound.getString("Type"));

        item = type.createItem(this);

        container = type.createAttributeContainer(this);
        container.loadFromNBT(compound.getCompoundTag("Attributes"));
    }

    /* Item methods */


    public int getItemStackLimit(ItemStack stack)
    {
        return container.maxStack;
    }


    public boolean isFull3D()
    {
        return container.full3d;
    }

    public boolean hasEffect(ItemStack stack)
    {
        return container.hasEffect || stack.isItemEnchanted();
    }

    public CreativeTabs getCreativeTab()
    {
        return container.creativeTab;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (container.onUse != null && container.onUse.script != null)
        {
            ITriggerData data = new TriggerData("onUse", TriggerType.ITEM, world, player, stack).setPosition(x, y, z).setSideAndHit(side, hitX, hitY, hitZ);
            JavaScriptHelper.executeTrigger(container.onUse.script, data, pack);
        }
        return false;
    }


    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (container.onRightClick != null && container.onRightClick.script != null)
        {
            ITriggerData data = new TriggerData("onRightClick", TriggerType.ITEM, world, player, stack);
            JavaScriptHelper.executeTrigger(container.onRightClick.script, data, pack);
        }
        return stack;
    }


    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (container.onUseFirst != null && container.onUseFirst.script != null)
        {
            ITriggerData data = new TriggerData("onUseFirst", TriggerType.ITEM, world, player, stack).setPosition(x, y, z).setSideAndHit(side, hitX, hitY, hitZ);
            JavaScriptHelper.executeTrigger(container.onUseFirst.script, data, pack);
        }
        return false;
    }


    public boolean useItemOnEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
    {
        if (target instanceof EntityPlayer && container.onUseOnPlayer != null && container.onUseOnPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onUseOnPlayer", TriggerType.ITEM, player.worldObj, player, stack).setInteractPlayer((EntityPlayer) target);
            JavaScriptHelper.executeTrigger(container.onUseOnPlayer.script, data, pack);
        } else if (container.onUseOnEntity != null && container.onUseOnEntity.script != null)
        {
            ITriggerData data = new TriggerData("onUseOnEntity", TriggerType.ITEM, player.worldObj, player, stack).setLiving(target);
            JavaScriptHelper.executeTrigger(container.onUseOnEntity.script, data, pack);
        }
        return false;
    }


    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
        if (container.onBlockDestroyed != null && container.onBlockDestroyed.script != null && living instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) living;
            ITriggerData data = new TriggerData("onBlockDestroyed", TriggerType.ITEM, world, player, stack).setPosition(x, y, z).setBlockName(GeneralHelper.getBlockName(block));
            JavaScriptHelper.executeTrigger(container.onBlockDestroyed.script, data, pack);
        }
        return false;
    }


    public boolean hitEntity(ItemStack stack, EntityLivingBase living1, EntityLivingBase living2)
    {
        if (container.onHitEntity != null && container.onHitEntity.script != null && living2 instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) living2;
            ITriggerData data = new TriggerData("onHitEntity", TriggerType.ITEM, player.worldObj, player, stack).setLiving(living1);
            JavaScriptHelper.executeTrigger(container.onHitEntity.script, data, pack);
        }
        return false;
    }


    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (container.onLeftClickLiving != null && container.onLeftClickLiving.script != null && entity instanceof EntityLiving)
        {
            EntityLiving living = (EntityLiving) entity;
            ITriggerData data = new TriggerData("onLeftClickLiving", TriggerType.ITEM, player.worldObj, player, stack).setLiving(living);
            JavaScriptHelper.executeTrigger(container.onLeftClickLiving.script, data, pack);
        } else if (container.onLeftClickPlayer != null && container.onLeftClickPlayer.script != null && entity instanceof EntityPlayer)
        {
            EntityPlayer interactPlayer = (EntityPlayer) entity;
            ITriggerData data = new TriggerData("onLeftClickPlayer", TriggerType.ITEM, player.worldObj, player, stack).setInteractPlayer(interactPlayer);
            JavaScriptHelper.executeTrigger(container.onLeftClickPlayer.script, data, pack);
        }
        return false;
    }


    public void onUpdate(ItemStack stack, World world, Entity entity, int slotId, boolean isCurrentItem)
    {
        if (container.onUpdate != null && container.onUpdate.script != null && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            ITriggerData data = new TriggerData("onUpdate", TriggerType.ITEM, player.worldObj, player, stack).setSlotId(slotId).setIsCurrentItem(isCurrentItem);
            JavaScriptHelper.executeTrigger(container.onUpdate.script, data, pack);
        }
    }


    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        if (container.onCreated != null && container.onCreated.script != null)
        {
            ITriggerData data = new TriggerData("onCreated", TriggerType.ITEM, player.worldObj, player, stack);
            JavaScriptHelper.executeTrigger(container.onCreated.script, data, pack);
        }
    }


    public void onUsingTick(ItemStack stack, EntityPlayer player, int tickCount)
    {
        if (container.onUsing != null && container.onUsing.script != null)
        {
            ITriggerData data = new TriggerData("onUsing", TriggerType.ITEM, player.worldObj, player, stack).setTickCount(getMaxItemUseDuration(stack) - tickCount);
            JavaScriptHelper.executeTrigger(container.onUsing.script, data, pack);
        }
    }


    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int tickCount)
    {
        if (container.onStoppedUsing != null && container.onStoppedUsing.script != null)
        {
            ITriggerData data = new TriggerData("onStoppedUsing", TriggerType.ITEM, player.worldObj, player, stack).setTickCount(getMaxItemUseDuration(stack) - tickCount);
            JavaScriptHelper.executeTrigger(container.onStoppedUsing.script, data, pack);
        }
    }


    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        if (container.onDroppedByPlayer != null && container.onDroppedByPlayer.script != null)
        {
            ITriggerData data = new TriggerData("onDroppedByPlayer", TriggerType.ITEM, player.worldObj, player, stack);
            JavaScriptHelper.executeTrigger(container.onDroppedByPlayer.script, data, pack);
        }
        return true;
    }


    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
    {
        if (container.onBlockStartBreak != null && container.onBlockStartBreak.script != null)
        {
            ITriggerData data = new TriggerData("onBlockStartBreak", TriggerType.ITEM, player.worldObj, player, stack).setPosition(x, y, z);
            JavaScriptHelper.executeTrigger(container.onBlockStartBreak.script, data, pack);
        }
        return false;
    }


    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (container.onRightClick != null && container.onRightClick.script != null)
        {
            ITriggerData data = new TriggerData("onEaten", TriggerType.ITEM, player.worldObj, player, stack);
            JavaScriptHelper.executeTrigger(container.onEaten.script, data, pack);
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return container.maxUsingDuration;
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
