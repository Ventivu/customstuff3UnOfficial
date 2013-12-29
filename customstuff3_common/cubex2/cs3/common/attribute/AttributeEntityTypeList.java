package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeEntityTypeList extends Attribute<String>
{
    public String[] includedEntities;
    public String[] excludedEntities;

    public AttributeEntityTypeList(AttributeCollection attributeCollection, String name, String defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public boolean hasValue()
    {
        return includedEntities != null && includedEntities.length > 0 || excludedEntities != null && excludedEntities.length > 0;
    }

    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        s = s.trim();
        this.value = s;

        if (s.contains("include"))
        {
            String includeList = s.substring(s.indexOf("include(") + 8, s.indexOf(")"));
            String[] split = includeList.split(",");
            includedEntities = new String[split.length];
            for (int i = 0; i < split.length; i++)
            {
                includedEntities[i] = split[i].trim();
            }
        }

        if (s.contains("exclude"))
        {
            String excludeList = s.substring(s.indexOf("exclude(") + 8, s.indexOf(")", s.indexOf("exclude(") - 1));
            String[] split = excludeList.split(",");
            excludedEntities = new String[split.length];
            for (int i = 0; i < split.length; i++)
            {
                excludedEntities[i] = split[i].trim();
            }
        }
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        readValue(compound.getString("Value"));
    }
}
