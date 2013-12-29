package cubex2.cs3.ingame;

import com.google.common.collect.Lists;
import cubex2.cs3.CustomStuff3;
import cubex2.cs3.lib.Directories;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class IngameContentPackLoader
{
    private static final IngameContentPackLoader instance = new IngameContentPackLoader();

    private List<IngameContentPack> contentPacks;

    private IngameContentPackLoader()
    {
    }

    public static IngameContentPackLoader instance()
    {
        return instance;
    }

    public List<IngameContentPack> getContentPacks()
    {
        return contentPacks;
    }

    public IngameContentPack newContentPack(String name)
    {
        File directory = new File(Directories.CONTENT_PACKS_INGAME, name);
        directory.mkdirs();
        IngameContentPack pack = new IngameContentPack(directory);
        contentPacks.add(pack);
        pack.prepare();
        pack.init();
        return pack;
    }

    public void searchPacks()
    {
        CustomStuff3.logger.info("Searching " + Directories.CONTENT_PACKS_INGAME.getAbsolutePath() + " for ingame content packs...");

        File[] packFiles = Directories.CONTENT_PACKS_INGAME.listFiles(contentPackFilter);
        contentPacks = Lists.newArrayListWithCapacity(packFiles.length);
        for (int i = 0; i < packFiles.length; i++)
        {
            contentPacks.add(new IngameContentPack(packFiles[i]));
        }

        CustomStuff3.logger.info("Found " + contentPacks.size() + " ingame content packs");
    }

    public void preparePacks()
    {
        for (IngameContentPack pack : contentPacks)
        {
            pack.prepare();
        }
    }

    public void initPacks()
    {
        for (IngameContentPack pack : contentPacks)
        {
            pack.init();
        }
    }

    private final FilenameFilter contentPackFilter = new FilenameFilter()
    {
        @Override
        public boolean accept(File dir, String name)
        {
            return dir.isDirectory() || name.endsWith(".zip");
        }
    };
}
