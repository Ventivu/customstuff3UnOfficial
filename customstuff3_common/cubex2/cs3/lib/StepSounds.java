package cubex2.cs3.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;

public class StepSounds
{
    private static BiMap<String, StepSound> stepSoundMap = HashBiMap.create();

    public static StepSound getStepSound(String name)
    {
        if (name == null)
            return null;

        StepSound stepSound = null;

        if (stepSoundMap.containsKey(name))
        {
            stepSound = stepSoundMap.get(name);
        }

        return stepSound;
    }

    public static String getStepSoundName(StepSound stepSound)
    {
        if (stepSound == null)
            return null;

        String name = null;

        if (stepSoundMap.inverse().containsKey(stepSound))
        {
            name = stepSoundMap.inverse().get(stepSound);
        }

        return name;
    }

    static
    {
        stepSoundMap.put("anvil", Block.soundAnvilFootstep);
        stepSoundMap.put("cloth", Block.soundClothFootstep);
        stepSoundMap.put("glass", Block.soundGlassFootstep);
        stepSoundMap.put("grass", Block.soundGrassFootstep);
        stepSoundMap.put("gravel", Block.soundGravelFootstep);
        stepSoundMap.put("ladder", Block.soundLadderFootstep);
        stepSoundMap.put("metal", Block.soundMetalFootstep);
        stepSoundMap.put("powder", Block.soundPowderFootstep);
        stepSoundMap.put("sand", Block.soundSandFootstep);
        stepSoundMap.put("stone", Block.soundStoneFootstep);
        stepSoundMap.put("wood", Block.soundWoodFootstep);
    }
}
