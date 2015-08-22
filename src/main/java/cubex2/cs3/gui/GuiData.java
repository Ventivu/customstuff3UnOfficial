package cubex2.cs3.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.util.MyFunction;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.Util;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class GuiData implements NBTData
{
    public List<ControlData> controls = Lists.newArrayList();

    public void add(ControlData data)
    {
        controls.add(data);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        Util.writeListToNBT("Controls", controls, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        Util.readListFromNBT("Controls", controls, READER, compound);
    }

    private static final MyFunction<NBTTagCompound, ControlData> READER = new MyFunction<NBTTagCompound, ControlData>()
    {
        @Override
        public ControlData apply(NBTTagCompound compound)
        {
            String type = compound.getString("ControlType");
            ControlData data = null;
            if (type.equals("button"))
            {
                data = new ButtonData();
            }
            else if (type.equals("label"))
            {
                data = new LabelData();
            }
            data.readFromNBT(compound);
            return data;
        }
    };
}
