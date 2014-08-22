package cubex2.cs3.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.api.IContentPack;
import cubex2.cs3.lib.ModInfo;
import cubex2.cs3.registry.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class BaseContentPack implements IContentPack
{
    public final String name;
    public final String id;
    public final File directory;
    protected final Logger logger;
    protected final List<ContentRegistry> contentRegistryList = Lists.newArrayList();
    protected final Map<Class<? extends Content>, ContentRegistry> contentRegistry = Maps.newHashMap();
    private final Map<String, ContentRegistry> nameToRegistryMap = Maps.newHashMap();

    public BaseContentPack(File directory, String name, String id)
    {
        this.directory = directory;
        this.name = name;
        this.id = id;
        logger = Logger.getLogger(ModInfo.ID + "_" + name);

        registerContentRegistry(new OreDictEntryRegistry(this), OreDictionaryEntry.class);
        registerContentRegistry(new FuelRegistry(this), Fuel.class);
        registerContentRegistry(new SmeltingRecipeRegistry(this), SmeltingRecipe.class);
        registerContentRegistry(new ShapedRecipeRegistry(this), ShapedRecipe.class);
        registerContentRegistry(new ShapelessRecipeRegistry(this), ShapelessRecipe.class);
        registerContentRegistry(new ItemRegistry(this), WrappedItem.class);
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public File getDirectory()
    {
        return directory;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public <T extends Content> ContentRegistry<T> getContentRegistry(T content)
    {
        return contentRegistry.get(content.getClass());
    }

    public ContentRegistry getContentRegistry(String name)
    {
        return nameToRegistryMap.get(name);
    }

    public <T extends Content> ContentRegistry<T> getContentRegistry(Class<T> clazz)
    {
        return contentRegistry.get(clazz);
    }

    private void registerContentRegistry(ContentRegistry registry, Class<? extends Content>... classes)
    {
        for (Class<? extends Content> clazz : classes)
        {
            contentRegistry.put(clazz, registry);
        }
        nameToRegistryMap.put(registry.getName(), registry);
        contentRegistryList.add(registry);
    }

    public void save()
    {
    }

    public void load()
    {
    }

    public abstract void prepare();

    public abstract void init();
}
