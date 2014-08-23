package cubex2.cs3.ingame;

import cubex2.cs3.basic.ContentPack;
import cubex2.cs3.registry.ContentRegistry;
import cubex2.cs3.util.IOHelper;
import cubex2.cs3.util.IPurpuseStringProvider;
import cubex2.cs3.util.NBTHelper;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class IngameContentPack extends ContentPack implements IPurpuseStringProvider, Comparable<IngameContentPack>
{
    public IngameContentPack(File directory, String name, String id)
    {
        super(directory, name, id);
    }

    @Override
    public void prepare()
    {
    }

    @Override
    public void init()
    {
        logger.info("Initializing...");
        load();
    }

    public void save()
    {
        if (!IngameContentPackLoader.initialized)
            return;

        NBTTagCompound compound = new NBTTagCompound();

        NBTTagList managerList = new NBTTagList();
        for (ContentRegistry contentManager : contentRegistryList)
        {
            NBTTagCompound managerTag = new NBTTagCompound();
            contentManager.writeToNBT(managerTag);
            managerList.appendTag(managerTag);
        }
        compound.setTag("ManagerList", managerList);

        IOHelper.writeNBTToFile(compound, new File(directory, "data.dat"));

        NBTHelper.dumpNBT(compound, new File(directory, "data.txt"));
    }

    public void load()
    {
        if (!new File(directory, "data.dat").exists())
            return;
        NBTTagCompound compound = IOHelper.readNBTFromFile(new File(directory, "data.dat"));

        NBTTagList managerList = compound.getTagList("ManagerList", 10);
        for (int i = 0; i < managerList.tagCount(); i++)
        {
            NBTTagCompound managerTag = managerList.getCompoundTagAt(i);
            String managerName = managerTag.getString("Name");
            ContentRegistry manager = getContentRegistry(managerName);
            manager.readFromNBT(managerTag);
        }
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl ? getName() : null;
    }

    @Override
    public int compareTo(IngameContentPack o)
    {
        return name.compareTo(o.name);
    }
}
