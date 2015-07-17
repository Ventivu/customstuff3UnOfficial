package cubex2.cs3.util;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameData;
import cubex2.cs3.ClientProxy;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.lib.Directories;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClientHelper
{
    public static void refreshResources(Minecraft mc)
    {
        mc.getSoundHandler().pauseSounds();
        mc.refreshResources();
        mc.getSoundHandler().resumeSounds();
    }

    public static String loadDocFile(String path)
    {
        try
        {
            InputStream stream = ClientProxy.resPack.getInputStream(new ResourceLocation("cs3", "docs/" + path));
            String res = IOUtils.toString(stream, Charsets.UTF_8).replace("\r", "");
            stream.close();
            return res;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return "TEXT --{\n[FILE NOT FOUND: " + path + " ]\n}--";
    }

    private static List<ResourceLocation> cachedBlockIcons = null;
    private static List<ResourceLocation> cachedItemIcons = null;

    public static List<ResourceLocation> getAllIcons(String subFolder)
    {
        if (subFolder.equals("blocks"))
            return getAllBlockIcons();
        else if (subFolder.equals("items"))
            return getAllItemIcons();

        return Lists.newArrayList();
    }

    public static List<ResourceLocation> getAllBlockIcons()
    {
        if (cachedBlockIcons != null)
            return cachedBlockIcons;

        cachedBlockIcons = Lists.newArrayList();
        List<String> names = Lists.newArrayList();

        for (Object o : GameData.getBlockRegistry().getKeys())
        {
            Block block = (Block) GameData.getBlockRegistry().getObject(o);

            for (int meta = 0; meta < 16; meta++)
            {
                for (int side = 0; side < 6; side++)
                {
                    try
                    {
                        IIcon icon = block.getIcon(side, meta);
                        addIcon(icon, names);
                    } catch (Exception e)
                    {
                        // do nothing
                    }
                }
            }
        }

        return createLocations(names, cachedBlockIcons, "blocks");
    }

    public static List<ResourceLocation> getAllItemIcons()
    {
        if (cachedItemIcons != null)
            return cachedItemIcons;

        cachedItemIcons = Lists.newArrayList();
        List<String> names = Lists.newArrayList();

        for (Object o : GameData.getItemRegistry().getKeys())
        {
            Item item = (Item) GameData.getItemRegistry().getObject(o);
            if (item instanceof ItemBlock)
                continue;
            List<ItemStack> subTypes = ItemStackHelper.getSubTypes(item);
            for (ItemStack stack : subTypes)
            {
                try
                {
                    IIcon icon = stack.getIconIndex();
                    addIcon(icon, names);
                } catch (Exception e)
                {
                    // do nothing
                }
            }
        }

        return createLocations(names, cachedItemIcons, "items");
    }

    private static List<ResourceLocation> createLocations(List<String> names, List<ResourceLocation> locations, String subFolder)
    {
        Collections.sort(names);
        for (String name : names)
        {
            String[] split = name.split(":");
            locations.add(new ResourceLocation(split[0], "textures/" + subFolder + "/" + split[1] + ".png"));
        }

        return locations;
    }

    private static void addIcon(IIcon icon, List<String> names)
    {
        String iconName = icon.getIconName();
        if (iconName.length() == 0)
            return;
        if (!iconName.contains(":"))
            iconName = "minecraft:" + iconName;

        if (!names.contains(iconName))
        {
            names.add(iconName);
        }
    }

    public static List<ResourceLocation> getPackIcons(BaseContentPack pack, String subFolder)
    {
        List<ResourceLocation> icons = Lists.newArrayList();

        List<String> files = listPathsInPath("assets/" + pack.id.toLowerCase() + "/" + Directories.TEXTURES + "/" + subFolder, pack);
        for (int i = 0; i < files.size(); i++)
        {
            files.set(i, pack.id.toLowerCase() + ":" + files.get(i).substring(files.get(i).indexOf(pack.id.toLowerCase()) + pack.id.length() + 1));
        }
        Collections.sort(files);

        for (String file : files)
        {
            icons.add(new ResourceLocation(file));
        }

        return icons;
    }

    public static List<String> listPathsInPath(String path, BaseContentPack pack)
    {
        if (pack.isZipped())
        {
            return listPathsInZip(pack.directory, path);

        } else
        {
            return listPathsInDir(pack.directory, path);
        }
    }

    public static List<String> listPathsInDir(File modDir, String dir)
    {
        File file = new File(modDir, dir);
        File[] files = file.listFiles();
        List<String> ret = Lists.newArrayList();
        if (files != null)
        {
            for (File f : files)
            {
                ret.add(f.getAbsolutePath().substring(modDir.getAbsolutePath().length() + 1).replace('\\', '/'));
            }
        }
        return ret;
    }

    public static List<String> listPathsInZip(File zipFile, String dir)
    {
        List<String> ret = Lists.newArrayList();
        ZipFile zip = null;
        try
        {
            zip = new ZipFile(zipFile);
            List<? extends ZipEntry> entries = Collections.list(zip.entries());
            for (ZipEntry entry : entries)
            {
                File file = new File(entry.getName());
                if ((file.getParent() == null && dir.equals("")) ||
                        (file.getParent() != null && file.getParent().replace("\\", "/").equals(dir)))
                {
                    ret.add(entry.getName());
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (zip != null) zip.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static String resourceToIconString(ResourceLocation location)
    {
        String rp = location.getResourcePath();
        return location.getResourceDomain() + ":" + rp.substring(rp.lastIndexOf('/') + 1, rp.length() - 4);
    }
}
