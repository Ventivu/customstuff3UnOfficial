package cubex2.cs3.basic;

import cubex2.cs3.asm.ICSMod;

import java.io.File;
import java.io.FilenameFilter;

public class ContentPackLoader
{
    private static final ContentPackLoader instance = new ContentPackLoader();

    private ContentPack[] contentPacks;

    private ContentPackLoader()
    {
    }

    public static ContentPackLoader instance()
    {
        return instance;
    }

    public void searchPacks()
    {
        /* TODO CustomStuff3.logger.info("Searching " + Directories.CONTENT_PACKS_DEFAULT.getAbsolutePath() + " for content packs...");

        File[] packFiles = Directories.CONTENT_PACKS_DEFAULT.listFiles(contentPackFilter);
        contentPacks = new ContentPack[packFiles.length];
        for (int i = 0; i < packFiles.length; i++)
        {
            contentPacks[i] = new ContentPack(packFiles[i]);
        }

        CustomStuff3.logger.info("Found " + contentPacks.length + " content packs");*/
    }

    public void preparePacks()
    {

    }

    public void initPacks()
    {

    }

    public void loadPack(ICSMod pack)
    {

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
