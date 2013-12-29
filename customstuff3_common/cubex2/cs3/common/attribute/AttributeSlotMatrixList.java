package cubex2.cs3.common.attribute;

public class AttributeSlotMatrixList
{
}/* extends Attribute<String>
{
    public SlotMatrix[] slots;

    public AttributeSlotMatrixList(AttributeCollection attributeCollection, String name, String defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public boolean hasValue()
    {
        return slots != null && slots.length > 0;
    }

    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        if (s == null)
            return true;
        s = s.trim().replace(" ", "");
        this.value = s;

        String[] elements = s.split("\\|");
        slots = new SlotMatrix[elements.length];
        for (int i = 0; i < elements.length; i++)
        {
            String[] split = elements[i].split(",");
            int firstId = Integer.parseInt(split[0]);
            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);
            int width = split.length > 3 ? Integer.parseInt(split[3]) : 1;
            int height = split.length > 4 ? Integer.parseInt(split[4]) : 1;
            if (split.length > 5)
            {
                List<ForgeDirection> sides = Lists.newArrayList();
                for (int j = 5; j < split.length; j++)
                {
                    String side = split[j].toLowerCase();
                    if (side.equals("bottom") || side.equals("yn"))
                    {
                        sides.add(ForgeDirection.DOWN);
                    }
                    else if (side.equals("top") || side.equals("yp"))
                    {
                        sides.add(ForgeDirection.UP);
                    }
                    else if (side.equals("north") || side.equals("zn"))
                    {
                        sides.add(ForgeDirection.NORTH);
                    }
                    else if (side.equals("south") || side.equals("zp"))
                    {
                        sides.add(ForgeDirection.SOUTH);
                    }
                    else if (side.equals("west") || side.equals("xn"))
                    {
                        sides.add(ForgeDirection.WEST);
                    }
                    else if (side.equals("east") || side.equals("xp"))
                    {
                        sides.add(ForgeDirection.EAST);
                    }
                }
                slots[i] = new SlotMatrix(x, y, firstId, width, height, sides);
            }
            else
            {
                slots[i] = new SlotMatrix(x, y, firstId, width, height);
            }
        }

        return true;
    }

    @Override
    public void writeIEAttribute(List<String> lines)
    {
        if (value != null)
        {
            lines.add(getName() + "=" + value);
        }
    }

    @Override
    public void readIEAttribute(String fullName, String value)
    {
        readValue(value);
    }

}*/
