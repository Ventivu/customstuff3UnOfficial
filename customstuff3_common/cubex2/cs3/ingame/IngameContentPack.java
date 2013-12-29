package cubex2.cs3.ingame;

import cubex2.cs3.basic.ContentPack;
import cubex2.cs3.common.ContentManager;
import cubex2.cs3.util.IOHelper;
import cubex2.cs3.util.IPurpuseStringProvider;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;

public class IngameContentPack extends ContentPack implements IPurpuseStringProvider
{
    public IngameContentPack(File directory)
    {
        super(directory);
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
        for (ContentManager contentManager : contentManagers.values())
        {
            NBTTagCompound managerTag = new NBTTagCompound();
            contentManager.writeToNBT(managerTag);
            managerList.appendTag(managerTag);
        }
        compound.setTag("ManagerList", managerList);

        IOHelper.writeNBTToFile(compound, new File(directory, "data.dat"));
    }

    public void load()
    {
        if (!new File(directory, "data.dat").exists())
            return;
        NBTTagCompound compound = IOHelper.readNBTFromFile(new File(directory, "data.dat"));

        NBTTagList managerList = compound.getTagList("ManagerList");
        for (int i = 0; i < managerList.tagCount(); i++)
        {
            NBTTagCompound managerTag = (NBTTagCompound) managerList.tagAt(i);
            String managerName = managerTag.getString("Name");
            ContentManager manager = getContentManager(managerName);
            manager.readFromNBT(managerTag);
        }
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl ? getName() : null;
    }
}
