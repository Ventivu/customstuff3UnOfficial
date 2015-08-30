package cubex2.cs3.tileentity;

import cubex2.cs3.common.WrappedTileEntity;
import cubex2.cs3.tileentity.attributes.TileEntityInventoryAttributes;
import cubex2.cs3.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInventory extends TileEntityCS implements IInventory
{
    public ItemStack[] inventoryContents;
    private int slotCount;

    public TileEntityInventory(WrappedTileEntity wrappedTileEntity)
    {
        super(wrappedTileEntity);
        slotCount = ((TileEntityInventoryAttributes) container).slotCount;
        inventoryContents = new ItemStack[slotCount];
    }

    public TileEntityInventory()
    {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        slotCount = ((TileEntityInventoryAttributes) container).slotCount;
        inventoryContents = new ItemStack[slotCount];

        Util.readStacksFromNBT("CS3_Inv_Items", inventoryContents, compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        Util.writeStacksToNBT("CS3_Inv_Items", inventoryContents, compound);
    }

    @Override
    public int getSizeInventory()
    {
        return slotCount;
    }

    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return p_70301_1_ >= 0 && p_70301_1_ < this.inventoryContents.length ? this.inventoryContents[p_70301_1_] : null;
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.inventoryContents[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.inventoryContents[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.inventoryContents[p_70298_1_];
                this.inventoryContents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.inventoryContents[p_70298_1_].splitStack(p_70298_2_);

                if (this.inventoryContents[p_70298_1_].stackSize == 0)
                {
                    this.inventoryContents[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.inventoryContents[p_70304_1_] != null)
        {
            ItemStack itemstack = this.inventoryContents[p_70304_1_];
            this.inventoryContents[p_70304_1_] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.inventoryContents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return "invCS3TileEntity";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }
}
