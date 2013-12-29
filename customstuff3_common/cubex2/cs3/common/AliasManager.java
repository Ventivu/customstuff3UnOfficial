package cubex2.cs3.common;

import net.minecraft.item.Item;

public class AliasManager extends ContentManager<Alias>
{
    public AliasManager(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public Alias newDataInstance()
    {
        return new Alias(pack);
    }

    @Override
    public String getName()
    {
        return "AliasManager";
    }
}
