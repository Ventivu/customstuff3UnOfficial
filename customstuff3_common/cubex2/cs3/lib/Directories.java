package cubex2.cs3.lib;

import java.io.File;

public class Directories
{
    public static final String MAIN_DIRECTORY_NAME = "CustomStuff3";
    public static final String CONTENT_PACKS_DEFAULT_NAME = "ContentPacks";
    public static final String CONTENT_PACKS_INGAME_NAME = "IngameContentPacks";

    public static File MAIN_DIRECTORY;
    public static File CONTENT_PACKS_DEFAULT;
    public static File CONTENT_PACKS_INGAME;

    public static final void init(File baseDir)
    {
        MAIN_DIRECTORY = new File(baseDir, MAIN_DIRECTORY_NAME);
        CONTENT_PACKS_DEFAULT = new File(MAIN_DIRECTORY, CONTENT_PACKS_DEFAULT_NAME);
        CONTENT_PACKS_INGAME = new File(MAIN_DIRECTORY, CONTENT_PACKS_INGAME_NAME);

        MAIN_DIRECTORY.mkdirs();
        CONTENT_PACKS_DEFAULT.mkdirs();
        CONTENT_PACKS_INGAME.mkdirs();
    }
}
