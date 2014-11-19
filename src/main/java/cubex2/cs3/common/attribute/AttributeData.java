package cubex2.cs3.common.attribute;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;

import java.lang.reflect.Field;

public class AttributeData implements Comparable<AttributeData>, IPurposeStringProvider
{
    public final Attribute attribute;
    public final Field field;

    public AttributeData(Attribute attribute, Field field)
    {
        this.attribute = attribute;
        this.field = field;
    }

    public String getDisplayName()
    {
        return attribute.customName().length() > 0 ? attribute.customName() : field.getName();
    }

    @Override
    public int compareTo(AttributeData o)
    {
        return getDisplayName().compareTo(o.getDisplayName());
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return getDisplayName();
    }
}
