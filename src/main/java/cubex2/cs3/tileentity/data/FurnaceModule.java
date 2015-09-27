package cubex2.cs3.tileentity.data;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;

public class FurnaceModule implements NBTData, IPurposeStringProvider
{
    public String name;

    public int inputSlot;
    public int outputSlot;
    public int fuelSlot;

    public int cookTime = 200;

    public FurnaceModule(String name, int inputSlot, int outputSlot, int fuelSlot, int cookTime)
    {
        this.name = name;
        this.inputSlot = inputSlot;
        this.outputSlot = outputSlot;
        this.fuelSlot = fuelSlot;
        this.cookTime = cookTime;
    }

    public FurnaceModule()
    {
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setInteger("Input", inputSlot);
        compound.setInteger("Output", outputSlot);
        compound.setInteger("Fuel", fuelSlot);
        compound.setInteger("CookTime", cookTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        inputSlot = compound.getInteger("Input");
        outputSlot = compound.getInteger("Output");
        fuelSlot = compound.getInteger("Fuel");
        cookTime = compound.getInteger("CookTime");
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
        {
            return String.format("%s - I:%d O:%d F:%d CT:%d", name, inputSlot, outputSlot, fuelSlot, cookTime);
        }
        return null;
    }
}
