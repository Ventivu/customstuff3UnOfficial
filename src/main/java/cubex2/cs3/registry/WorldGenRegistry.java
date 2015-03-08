package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedWorldGen;
import cubex2.cs3.lib.Strings;

public class WorldGenRegistry extends ContentRegistry<WrappedWorldGen>
{
    public WorldGenRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public WrappedWorldGen newDataInstance()
    {
        return new WrappedWorldGen(pack);
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_WORLD_GEN;
    }
}
