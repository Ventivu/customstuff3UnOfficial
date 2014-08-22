package cubex2.cs3.common;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.item.EnumItemType;
import cubex2.cs3.item.attributes.ItemAttributes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
}
