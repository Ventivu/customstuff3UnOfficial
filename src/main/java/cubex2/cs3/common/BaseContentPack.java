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
    public final File directory;
    protected final Logger logger;
    protected final List<ContentRegistry> contentRegistryList = Lists.newArrayList();
    protected final Map<Class<? extends Content>, ContentRegistry> contentRegistry = Maps.newHashMap();
    private final Map<String, ContentRegistry> nameToRegistryMap = Maps.newHashMap();

    public final AliasRegistry aliasRegistry;

    public BaseContentPack(File directory)
    {
        this.directory = directory;
        this.name = directory.isDirectory() ? directory.getName() : directory.getName().substring(0, directory.getName().length() - 4);
        logger = Logger.getLogger(ModInfo.ID + "_" + name);

        aliasRegistry = new AliasRegistry(this);
        registerContentRegistry(aliasRegistry, Alias.class);
        registerContentRegistry(new OreDictEntryRegistry(this), OreDictionaryEntry.class);
        registerContentRegistry(new FuelRegistry(this), Fuel.class);
        registerContentRegistry(new SmeltingRecipeRegistry(this), SmeltingRecipe.class);
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
