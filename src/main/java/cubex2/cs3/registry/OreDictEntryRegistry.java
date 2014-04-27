package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.lib.Strings;

public class OreDictEntryRegistry extends ContentRegistry<OreDictionaryEntry>
{
    public OreDictEntryRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public OreDictionaryEntry newDataInstance()
    {
        return new OreDictionaryEntry(pack);
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_ORE_DICT_ENTRY;
    }
}
