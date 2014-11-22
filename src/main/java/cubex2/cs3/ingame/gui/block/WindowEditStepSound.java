package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Button;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import cubex2.cs3.lib.StepSounds;
import net.minecraft.block.Block;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class WindowEditStepSound extends Window implements IStringProvider<Block.SoundType>
{
    private WrappedBlock wrappedBlock;

    private DropBox<Block.SoundType> dbSounds;
    private Button btnPlayPlace;
    private Button btnPlayBreak;
    private Button btnPlayStep;

    public WindowEditStepSound(WrappedBlock block)
    {
        super("stepSound", EDIT | CANCEL, 200, 100);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        dbSounds = dropBox(StepSounds.getAllSounds()).y(7).fillWidth(7).add();
        dbSounds.setStringProvider(this);
        dbSounds.setSelectedValue(wrappedBlock.container.stepSound);

        btnPlayPlace = button("Play Place").y(7).below(dbSounds).width(60).add();
        btnPlayPlace.playSound = false;

        btnPlayBreak = button("Play Break").rightTo(btnPlayPlace).width(60).add();
        btnPlayBreak.playSound = false;

        btnPlayStep = button("Play Step").rightTo(btnPlayBreak).width(60).add();
        btnPlayStep.playSound = false;
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnPlayPlace)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation(dbSounds.getSelectedValue().func_150496_b())));
        } else if (c == btnPlayBreak)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation(dbSounds.getSelectedValue().getBreakSound())));
        } else if (c == btnPlayStep)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation(dbSounds.getSelectedValue().getStepResourcePath())));
        } else if (c == btnEdit)
        {
            wrappedBlock.container.stepSound = dbSounds.getSelectedValue();
            wrappedBlock.block.stepSound = dbSounds.getSelectedValue();
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String getStringFor(Block.SoundType value)
    {
        return StepSounds.getStepSoundName(value);
    }
}
