package cubex2.cs3.common.attribute;

import cubex2.cs2.tileentity.modular.FurnaceData;
import cubex2.cs2.util.GeneralHelper;

import java.util.List;

public class AttributeFurnaceDataList extends AttributeStringListToArray<FurnaceData>
{

    public AttributeFurnaceDataList(AttributeCollection attributeCollection, String name, FurnaceData[] defaultValue)
    {
        super(attributeCollection, name, defaultValue, "\\|");
    }

    @Override
    public FurnaceData getValue(String s)
    {
        List<String> tokens = GeneralHelper.tokenizeStringList(s);
        int id = Integer.parseInt(tokens.get(0));
        int ticksToSmelt = Integer.parseInt(tokens.get(1));
        return new FurnaceData(id, ticksToSmelt);
    }

    @Override
    public String getString(FurnaceData value)
    {
        return value.id + "," + value.ticksToSmelt;
    }

}
