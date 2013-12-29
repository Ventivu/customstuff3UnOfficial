package cubex2.cs3.common.attribute;

import cubex2.cs3.lib.Potions;
import net.minecraft.potion.Potion;

public class AttributePotionArray extends AttributeStringToOtherArray<Potion>
{

    public AttributePotionArray(AttributeCollection attributeCollection, String name, Potion defaultValue, int size)
    {
        super(attributeCollection, name, new Potion[size]);
    }

    @Override
    public Potion getValue(String s)
    {
        return Potions.getPotion(s);
    }

    @Override
    public String getString(Potion value)
    {
        return Potions.getPotionName(value);
    }

}
