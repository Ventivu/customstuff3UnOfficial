package cubex2.cs3.ingame;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.asm.ICSMod;
import cubex2.cs3.asm.ModGenData;
import cubex2.cs3.asm.ModGenerator;
import cubex2.cs3.lib.Directories;

import java.io.File;
import java.util.List;
import java.util.Map;

public class IngameContentPackLoader
{
    private static final IngameContentPackLoader instance = new IngameContentPackLoader();

    private List<IngameContentPack> contentPacks = Lists.newArrayList();
    private Map<ICSMod, IngameContentPack> contentPackMap = Maps.newHashMap();

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

    public void createContentPack(String name, String id)
    {
        File directory = new File(Directories.MODS, id);
        directory.mkdirs();

        File textures = new File(directory, "assets/" + id.toLowerCase() + "/" + Directories.TEXTURES);
        textures.mkdirs();
        new File(textures, Directories.ARMOR_TEXTURES).mkdirs();
        new File(textures, Directories.BLOCK_TEXTURES).mkdir();
        new File(textures, Directories.GUI_TEXTURES).mkdir();
        new File(textures, Directories.ITEM_TEXTURES).mkdir();
        new File(textures, Directories.CHEST_TEXTURES).mkdirs();

        ModGenData data = new ModGenData();
        data.modClassName = id.replace(" ", "");
        data.modId = id;
        data.modName = name;
        data.modVersion = "1.0.0";
        data.isIngamePack = true;

        new ModGenerator(data, directory);
    }

    public void onPreInitPack(ICSMod pack)
    {
        IngameContentPack ipack = new IngameContentPack(new File(Directories.MODS, pack.getId()), pack.getName(), pack.getId());
        contentPacks.add(ipack);
        contentPackMap.put(pack,ipack);
    }

    public void onInitPack(ICSMod pack)
    {
        contentPackMap.get(pack).init();
    }

    public void onPostInitPack(ICSMod pack)
    {
        contentPackMap.get(pack).postponeHandler.executePostponedTasks();
    }
}
