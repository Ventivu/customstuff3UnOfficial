package cubex2.cs3.common.attribute;


public class AttributeSlotMatrix
{
}/* extends AttributeStringToOther<SlotMatrix>
{
    private int defaultWidth = 1;
    private int defaultHeight = 1;

    public AttributeSlotMatrix(AttributeCollection attributeCollection, String name, SlotMatrix defaultValue)
    {
        super(attributeCollection, name, defaultValue);
        allowNull = true;
    }

    public AttributeSlotMatrix(AttributeCollection attributeCollection, String name, SlotMatrix defaultValue, int defaultWidth, int defaultHeight)
    {
        super(attributeCollection, name, defaultValue);
        allowNull = true;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    @Override
    public SlotMatrix getValue(String s)
    {
        String[] split = s.split(",");
        int firstId = Integer.parseInt(split[0]);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int width = split.length > 3 ? Integer.parseInt(split[3]) : defaultWidth;
        int height = split.length > 4 ? Integer.parseInt(split[4]) : defaultHeight;
        return new SlotMatrix(x, y, firstId, width, height);
    }

    @Override
    public String getString(SlotMatrix value)
    {
        return null;
    }

}*/
