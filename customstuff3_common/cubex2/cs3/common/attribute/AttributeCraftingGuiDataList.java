package cubex2.cs3.common.attribute;

import cubex2.cs2.gui.modular.CraftingGuiData;
import cubex2.cs2.util.GeneralHelper;

import java.util.List;

public class AttributeCraftingGuiDataList extends AttributeStringListToArray<CraftingGuiData>
{

    public AttributeCraftingGuiDataList(AttributeCollection attributeCollection, String name, CraftingGuiData[] defaultValue)
    {
        super(attributeCollection, name, defaultValue, "\\|");
        // TODO Auto-generated constructor stub
    }

    @Override
    public CraftingGuiData getValue(String s)
    {
        List<String> tokens = GeneralHelper.tokenizeStringList(s);
        int id = Integer.parseInt(tokens.get(0));
        int width = Integer.parseInt(tokens.get(1));
        int height = Integer.parseInt(tokens.get(2));
        int x = Integer.parseInt(tokens.get(3));
        int y = Integer.parseInt(tokens.get(4));
        int resultX = Integer.parseInt(tokens.get(5));
        int resultY = Integer.parseInt(tokens.get(6));
        return new CraftingGuiData(id, width, height, x, y, resultX, resultY);
    }

    @Override
    public String getString(CraftingGuiData value)
    {
        return value.id + "," + value.width + "," + value.height + "," + value.x + "," + value.y + "," + value.resultX + "," + value.resultY;
    }

}
