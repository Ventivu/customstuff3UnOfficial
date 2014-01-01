package cubex2.cs3.common;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

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
            if (fuel.isRepresentingStack(stack))
                return fuel.duration;
        }
        return 0;
    }
}
