package cubex2.cs3.gui.data;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.NBTData;
import cubex2.cs3.util.StringProviderPurpose;
import net.minecraft.nbt.NBTTagCompound;

public class ShiftClickRule implements NBTData, IPurposeStringProvider
{
    public boolean fromInv = false;
    public int fromStart = -1; // -1 = player inv
    public int fromEnd = -1;
    public boolean toInv = false;
    public int toStart = 0;
    public int toEnd = 0;

    public boolean fuelOnly = false;
    public boolean furnaceInputOnly = false;

    public ShiftClickRule()
    {
    }

    public ShiftClickRule(boolean fromInv, int fromStart, int fromEnd, boolean toInv, int toStart, int toEnd, boolean fuelOnly, boolean furnaceInputOnly)
    {
        this.fromInv = fromInv;
        this.fromStart = fromStart;
        this.fromEnd = fromEnd;
        this.toInv = toInv;
        this.toStart = toStart;
        this.toEnd = toEnd;
        this.fuelOnly = fuelOnly;
        this.furnaceInputOnly = furnaceInputOnly;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("FromInv", fromInv);
        compound.setInteger("FromStart", fromStart);
        compound.setInteger("FromEnd", fromEnd);
        compound.setBoolean("ToInv", toInv);
        compound.setInteger("ToStart", toStart);
        compound.setInteger("ToEnd", toEnd);
        compound.setBoolean("FuelOnly", fuelOnly);
        compound.setBoolean("FurnaceInputOnly",furnaceInputOnly);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        fromInv = compound.getBoolean("FromInv");
        fromStart = compound.getInteger("FromStart");
        fromEnd = compound.getInteger("FromEnd");
        toInv = compound.getBoolean("ToInv");
        toStart = compound.getInteger("ToStart");
        toEnd = compound.getInteger("ToEnd");
        fuelOnly = compound.getBoolean("FuelOnly");
        furnaceInputOnly = compound.getBoolean("FurnaceInputOnly");
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        if (purpose == StringProviderPurpose.LIST_BOX_ITEM_LABEl)
            return String.format("%s(%d, %d) -> %s(%d, %d)", fromInv ? "Inv" : "", fromStart, fromEnd, toInv ? "Inv" : "", toStart, toEnd);
        return null;
    }
}
