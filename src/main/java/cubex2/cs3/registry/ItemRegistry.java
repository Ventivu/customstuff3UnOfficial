package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.lib.Strings;

public class ItemRegistry extends ContentRegistry<WrappedItem>
{
    public ItemRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public WrappedItem newDataInstance()
    {
        return new WrappedItem(pack);
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_ITEM;
    }
}
