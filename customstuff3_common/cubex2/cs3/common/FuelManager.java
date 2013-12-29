package cubex2.cs3.common;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FuelManager extends ContentManager<Fuel> implements IFuelHandler
{
    public FuelManager(BaseContentPack pack)
    {
        super(pack);
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    public Fuel newDataInstance()
    {
        return new Fuel(pack);
    }

    @Override
    public String getName()
    {
        return "FuelManager";
    }

    @Override
    public int getBurnTime(ItemStack stack)
    {
        for (Fuel fuel : getContentList())
        {
            if (fuel.alias.damageValue == OreDictionary.WILDCARD_VALUE && fuel.alias.item.itemID == stack.itemID)
                return fuel.duration;
            else if (fuel.alias.item.itemID == stack.itemID && fuel.alias.damageValue == stack.getItemDamage())
                return fuel.duration;
        }
        return 0;
    }
}
