package cubex2.cs3.registry;

import com.google.common.collect.Maps;
import cubex2.cs3.common.Alias;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.registry.ContentRegistry;

import java.util.Map;

public class AliasRegistry extends ContentRegistry<Alias>
{
    private Map<String, Alias> nameToAliasMap = Maps.newHashMap();

    public AliasRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    public Alias getAlias(String name)
    {
        return nameToAliasMap.get(name);
    }

    @Override
    public void add(Alias content)
    {
        super.add(content);
        nameToAliasMap.put(content.name, content);
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
