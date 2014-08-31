package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import cubex2.cs3.common.attribute.AttributeContainer;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Field;

public class StringBridge extends AttributeBridge<String>
{
    @Override
    public String loadValueFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("Value"))
        {
            return compound.getString("Value");
        }

        return null;
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, String value)
    {
        if (value != null && value.length() > 0)
        {
            compound.setString("Value", value);
        }
    }
}
