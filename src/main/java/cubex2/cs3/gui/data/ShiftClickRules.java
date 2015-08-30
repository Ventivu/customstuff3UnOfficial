package cubex2.cs3.gui.data;

import com.google.common.collect.Lists;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.Util;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ShiftClickRules implements NBTData
{
    public List<ShiftClickRule> rules = Lists.newArrayList();

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        Util.writeListToNBT("Rules", rules, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        Util.readListFromNBT("Rules", rules, compound, ShiftClickRule.class);
    }
}
