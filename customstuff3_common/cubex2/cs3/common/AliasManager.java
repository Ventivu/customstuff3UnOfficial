package cubex2.cs3.common;

import com.google.common.collect.Maps;

import java.util.Map;

public class AliasManager extends ContentManager<Alias>
{
    private Map<String, Alias> nameToAliasMap = Maps.newHashMap();

    public AliasManager(BaseContentPack pack)
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
