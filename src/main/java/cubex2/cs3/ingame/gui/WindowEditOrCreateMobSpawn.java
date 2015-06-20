package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.MobSpawn;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Biomes;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;

public class WindowEditOrCreateMobSpawn extends Window
{
    private final BaseContentPack pack;
    private MobSpawn editingSpawn;

    private DropBox<String> dbMob;
    private DropBox<EnumCreatureType> dbType;
    private NumericUpDown nupRate;
    private NumericUpDown nupMin;
    private NumericUpDown nupMax;
    private ListBox<BiomeGenBase> lbBiomes;

    public WindowEditOrCreateMobSpawn(BaseContentPack pack)
    {
        super("New Mob Spawn", CREATE | CANCEL, 180, 225);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateMobSpawn(MobSpawn spawn, BaseContentPack pack)
    {
        super("Edit Mob Spawn", EDIT | CANCEL, 180, 225);
        this.pack = pack;
        editingSpawn = spawn;

        initControls();
    }

    private void initControls()
    {
        row("Mob:");
        dbMob = row(dropBox(GeneralHelper.getMobNames()));
        dbMob.setSelectedValue("Creeper");

        row("Spawn Type:");
        dbType = row(dropBox(EnumCreatureType.values()));
        dbType.setSelectedValue(EnumCreatureType.monster);

        row("Rate:");
        nupRate = row(numericUpDown());
        nupRate.setMinValue(1);

        ControlContainer container = row(container().height(9));
        container.col(label("Min Count:").width((180 - 14) / 2));
        container.col(label("Max Count:").width((180 - 14) / 2));

        container = row(container().height(20));
        nupMin = container.col(numericUpDown().width((180 - 14 - 2) / 2), 3);
        nupMax = container.col(numericUpDown().width((180 - 14 - 4) / 2), 3);

        row("Biomes:");
        ListBoxDescription<BiomeGenBase> desc = new ListBoxDescription<BiomeGenBase>();
        desc.multiSelect = true;
        desc.elements = Biomes.getBiomes();
        desc.rows = 3;
        desc.sorted = true;
        desc.comparator = Biomes.COMPARATOR;
        lbBiomes = listBox(desc).below(lastControl).fillWidth(7).add();
        lbBiomes.disableGlobalScrolling();

        if (editingSpawn != null)
        {
            dbMob.setSelectedValue(editingSpawn.mob);
            dbType.setSelectedValue(editingSpawn.type);
            nupRate.setValue(editingSpawn.rate);
            nupMin.setValue(editingSpawn.min);
            nupMax.setValue(editingSpawn.max);
            lbBiomes.select(editingSpawn.biomes);
        } else
        {
            lbBiomes.select(Biomes.getBiomes());
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            String mob = dbMob.getSelectedValue();
            EnumCreatureType type = dbType.getSelectedValue();
            int rate = nupRate.getValue();
            int min = nupMin.getValue();
            int max = nupMax.getValue();
            List<BiomeGenBase> biomes = Lists.newArrayList(lbBiomes.getSelectedItems());
            MobSpawn spawn = new MobSpawn(mob, rate, min, max, type, biomes, pack);
            spawn.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingSpawn.mob = dbMob.getSelectedValue();
            editingSpawn.type = dbType.getSelectedValue();
            editingSpawn.rate = nupRate.getValue();
            editingSpawn.min = nupMin.getValue();
            editingSpawn.max = nupMax.getValue();
            if (editingSpawn.max < editingSpawn.min)
                editingSpawn.max = editingSpawn.min;
            editingSpawn.biomes.clear();
            editingSpawn.biomes.addAll(lbBiomes.getSelectedItems());
            editingSpawn.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
