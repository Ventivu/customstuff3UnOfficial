package cubex2.cs3.common.attribute;

import cubex2.cs3.common.BaseContentPack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Attribute<T>
{
    public T value;

    protected BaseContentPack pack;
    protected String name;

    public Attribute(AttributeCollection attributeCollection, String name, T defaultValue)
    {
        this.name = name;
        this.value = defaultValue;
        this.pack = attributeCollection.pack;
        attributeCollection.addAttribute(this);
    }

    public String getName()
    {
        return name;
    }

    public Object getJSValue()
    {
        return value;
    }

    public abstract boolean hasValue();

    public abstract boolean readValue(Object value);

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);
}
