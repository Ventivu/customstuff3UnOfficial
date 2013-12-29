package cubex2.cs3.common.attribute;

import cubex2.cs3.lib.Potions;
import net.minecraft.potion.Potion;

public class AttributePotion extends AttributeStringToOther<Potion>
{

    public AttributePotion(AttributeCollection attributeCollection, String name, Potion defaultValue)
    {
        super(attributeCollection, name, defaultValue);
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
