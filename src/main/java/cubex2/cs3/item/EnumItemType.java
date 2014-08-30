package cubex2.cs3.item;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.common.client.renderer.CSItemRenderer;
import cubex2.cs3.item.attributes.ItemAttributes;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.Map;

public enum EnumItemType
{
    NORMAL("normal", ItemCS.class, ItemAttributes.class);

    public final String name;
    public final Class<? extends ItemCS> itemClass;
    public final Class<? extends ItemAttributes> attributesClass;

    private EnumItemType(String name, Class<? extends ItemCS> itemClass, Class<? extends ItemAttributes> attributesClass)
    {
        this.name = name;
        this.itemClass = itemClass;
        this.attributesClass = attributesClass;
    }

    public Item createItem(WrappedItem wrappedItem)
    {
        try
        {
            Item item = itemClass.getConstructor(WrappedItem.class).newInstance(wrappedItem);
            GameRegistry.registerItem(item, wrappedItem.getName());
            System.err.println("Item ID: " +  GameData.getItemRegistry().getId(item));

            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            {
                MinecraftForgeClient.registerItemRenderer(item, new CSItemRenderer(wrappedItem));
            }

            return item;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ItemAttributes createAttributeContainer(WrappedItem item)
    {
        try
        {
            return attributesClass.getConstructor(BaseContentPack.class).newInstance(item.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumItemType> map = Maps.newHashMap();

    public static EnumItemType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumItemType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }

    public static String[] getNames()
    {
        String[] names = new String[values().length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = values()[i].name;
        }

        return names;
    }
}
