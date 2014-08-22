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
        return compound.getString("Value");
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, String value)
    {
        compound.setString("Value", value);
    }
}
