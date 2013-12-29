package cubex2.cs3.common.attribute;

import cubex2.cs2.gui.modular.FurnaceGuiData;
import cubex2.cs2.util.GeneralHelper;

import java.util.List;

public class AttributeFurnaceGuiDataList extends AttributeStringListToArray<FurnaceGuiData>
{

    public AttributeFurnaceGuiDataList(AttributeCollection attributeCollection, String name, FurnaceGuiData[] defaultValue)
    {
        super(attributeCollection, name, defaultValue, "\\|");
    }

    @Override
    public FurnaceGuiData getValue(String s)
    {
        List<String> tokens = GeneralHelper.tokenizeStringList(s);
        int id = Integer.parseInt(tokens.get(0));
        int inputX = Integer.parseInt(tokens.get(1));
        int inputY = Integer.parseInt(tokens.get(2));
        int fuelX = Integer.parseInt(tokens.get(3));
        int fuelY = Integer.parseInt(tokens.get(4));
        int resultX = Integer.parseInt(tokens.get(5));
        int resultY = Integer.parseInt(tokens.get(6));
        int cookSrcX = Integer.parseInt(tokens.get(7));
        int cookSrcY = Integer.parseInt(tokens.get(8));
        int cookDestX = Integer.parseInt(tokens.get(9));
        int cookDestY = Integer.parseInt(tokens.get(10));
        int cookWidth = Integer.parseInt(tokens.get(11));
        int cookHeight = Integer.parseInt(tokens.get(12));
        int burnSrcX = Integer.parseInt(tokens.get(13));
        int burnSrcY = Integer.parseInt(tokens.get(14));
        int burnDestX = Integer.parseInt(tokens.get(15));
        int burnDestY = Integer.parseInt(tokens.get(16));
        int burnWidth = Integer.parseInt(tokens.get(17));
        int burnHeight = Integer.parseInt(tokens.get(18));
        return new FurnaceGuiData(id, inputX, inputY, fuelX, fuelY, resultX, resultY, cookSrcX, cookSrcY, cookDestX, cookDestY, cookWidth, cookHeight, burnSrcX, burnSrcY, burnDestX, burnDestY, burnWidth, burnHeight);
    }

    @Override
    public String getString(FurnaceGuiData value)
    {
        return value.id + "," + value.inputX + "," + value.inputY + "," + value.fuelX + "," + value.fuelY + "," + value.resultX + "," + value.resultY;
    }

}
