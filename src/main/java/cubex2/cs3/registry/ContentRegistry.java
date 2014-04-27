package cubex2.cs3.registry;

import com.google.common.collect.Lists;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Content;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Collections;
import java.util.List;

public abstract class ContentRegistry<T extends Content>
{
    protected BaseContentPack pack;
    protected List<T> contentList = Lists.newArrayList();

    public ContentRegistry(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public List<T> getContentList()
    {
        return Collections.unmodifiableList(contentList);
    }

    public void add(T content)
    {
        contentList.add(content);
    }

    public void remove(T content)
    {
        contentList.remove(content);
    }

    public abstract T newDataInstance();

    public abstract String getName();

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", getName());

        NBTTagList contentTagList = new NBTTagList();
        for (int i = 0; i < contentList.size(); i++)
        {
            NBTTagCompound contentCompound = new NBTTagCompound();
            contentList.get(i).writeToNBT(contentCompound);
            contentTagList.appendTag(contentCompound);
        }
        compound.setTag("ContentList", contentTagList);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList contentTagList = compound.getTagList("ContentList", 10);
        for (int i = 0; i < contentTagList.tagCount(); i++)
        {
            NBTTagCompound contentCompound = contentTagList.getCompoundTagAt(i);
            T data = newDataInstance();
            data.readFromNBT(contentCompound);
            data.apply();
        }
    }

}
