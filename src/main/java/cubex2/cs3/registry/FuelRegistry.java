package cubex2.cs3.registry;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.Fuel;
import cubex2.cs3.lib.Strings;
import net.minecraft.item.ItemStack;

public class FuelRegistry extends ContentRegistry<Fuel> implements IFuelHandler
{
    public FuelRegistry(BaseContentPack pack)
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
        return Strings.REGISTRY_FUEL;
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
