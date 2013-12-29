package cubex2.cs3.common.attribute;

import cubex2.cs3.lib.StepSounds;
import net.minecraft.block.StepSound;

public class AttributeStepSound extends AttributeStringToOther<StepSound>
{

    public AttributeStepSound(AttributeCollection attributeCollection, String name, StepSound defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public StepSound getValue(String s)
    {
        return StepSounds.getStepSound(s);
    }

    @Override
    public String getString(StepSound value)
    {
        return StepSounds.getStepSoundName(value);
    }

}
