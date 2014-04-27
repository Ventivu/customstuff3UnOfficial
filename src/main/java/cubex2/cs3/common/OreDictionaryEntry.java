package cubex2.cs3.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryEntry extends BaseContent
{
    public String oreClass;
    public Alias alias;

    public OreDictionaryEntry(String oreClass, Alias alias, BaseContentPack pack)
    {
        super(pack);
        this.oreClass = oreClass;
        this.alias = alias;
    }

    public OreDictionaryEntry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        OreDictionary.registerOre(oreClass, alias.getItemStack());

        super.apply();
    }

    @Override
    public boolean canEdit()
    {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("OreClass", oreClass);
        compound.setString("Alias", alias.name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        oreClass = compound.getString("OreClass");
        alias = pack.aliasRegistry.getAlias(compound.getString("Alias"));
    }
}
