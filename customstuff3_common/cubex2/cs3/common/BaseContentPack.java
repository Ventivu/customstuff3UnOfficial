package cubex2.cs3.common;

import com.google.common.collect.Maps;
import cubex2.cs3.api.IContentPack;
import cubex2.cs3.lib.ModInfo;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public abstract class BaseContentPack implements IContentPack
{
    public final String name;
    public final File directory;
    protected final Logger logger;
    protected final Map<Class<? extends Content>, ContentManager> contentManagers = Maps.newHashMap();
    private final Map<String, ContentManager> nameToManagerMap = Maps.newHashMap();

    public BaseContentPack(File directory)
    {
        this.directory = directory;
        this.name = directory.isDirectory() ? directory.getName() : directory.getName().substring(0, directory.getName().length() - 4);
        logger = Logger.getLogger(ModInfo.ID + "_" + name);

        registerContentManager(new AliasManager(this), Alias.class);
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

    public <T extends Content> ContentManager<T> getContentManager(T content)
    {
        return contentManagers.get(content.getClass());
    }

    public ContentManager getContentManager(String name)
    {
        return nameToManagerMap.get(name);
    }

    public <T extends Content> ContentManager<T> getContentManager(Class<T> clazz)
    {
        return contentManagers.get(clazz);
    }

    private void registerContentManager(ContentManager manager, Class<? extends Content>... classes)
    {
        for (Class<? extends Content> clazz : classes)
        {
            contentManagers.put(clazz, manager);
        }
        nameToManagerMap.put(manager.getName(), manager);
    }

    public void save(){}

    public void load(){}

    public abstract void prepare();

    public abstract void init();
}
