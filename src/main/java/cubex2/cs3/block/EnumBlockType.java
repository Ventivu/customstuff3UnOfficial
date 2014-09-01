package cubex2.cs3.block;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.item.ItemCSBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Map;

public enum EnumBlockType
{
    NORMAL("normal", BlockCS.class, BlockAttributes.class);

    public final String name;
    public final Class<? extends BlockCS> blockClass;
    public final Class<? extends BlockAttributes> attributesClass;
    public final Class<? extends Item> itemClass;

    private EnumBlockType(String name, Class<? extends BlockCS> blockClass, Class<? extends BlockAttributes> attributeClass)
    {
        this(name, blockClass, attributeClass, ItemCSBlock.class);
    }

    private EnumBlockType(String name, Class<? extends BlockCS> blockClass, Class<? extends BlockAttributes> attributesClass, Class<? extends Item> itemClass)
    {
        this.name = name;
        this.blockClass = blockClass;
        this.attributesClass = attributesClass;
        this.itemClass = itemClass;
    }

    /**
     * Creates a block and its item.
     *
     * @param wrappedBlock The wrappedBlock.
     * @return The created block.
     */
    public Block createBlock(WrappedBlock wrappedBlock)
    {
        try
        {
            Block block = blockClass.getConstructor(WrappedBlock.class).newInstance(wrappedBlock);
            if (ItemBlock.class.isAssignableFrom(itemClass))
            {
                Class<? extends ItemBlock> clazz = (Class<? extends ItemBlock>) itemClass;
                GameRegistry.registerBlock(block, clazz, wrappedBlock.getName(), wrappedBlock);
            } else
            {
                GameRegistry.registerBlock(block, wrappedBlock.getName());
                Item item = itemClass.getConstructor(WrappedBlock.class).newInstance(wrappedBlock);
                GameRegistry.registerItem(item, wrappedBlock.getName());
            }

            return block;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public BlockAttributes createAttributeContainer(WrappedBlock wrappedBlock)
    {
        try
        {
            return attributesClass.getConstructor(BaseContentPack.class).newInstance(wrappedBlock.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumBlockType> map = Maps.newHashMap();

    public static EnumBlockType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumBlockType e : values())
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
