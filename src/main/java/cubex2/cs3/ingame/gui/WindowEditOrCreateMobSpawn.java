package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.MobSpawn;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
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
        super("New Mob Spawn", CREATE | CANCEL, 180, 215);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateMobSpawn(MobSpawn spawn, BaseContentPack pack)
    {
        super("Edit Mob Spawn", EDIT | CANCEL, 180, 215);
        this.pack = pack;
        editingSpawn = spawn;

        initControls();
    }

    private void initControls()
    {
        label("Mob:").at(7, 7).add();
        dbMob = dropBox(GeneralHelper.getMobNames()).below(lastControl).fillWidth(7).add();
        dbMob.setSelectedValue("Creeper");

        label("Spawn Type:").below(dbMob).add();
        dbType = dropBox(EnumCreatureType.values()).below(lastControl).fillWidth(7).add();
        dbType.setSelectedValue(EnumCreatureType.monster);

        label("Rate:").below(dbType, 5).add();
        nupRate = numericUpDown().below(lastControl).fillWidth(7).add();
        nupRate.setMinValue(1);

        label("Min Count:").below(nupRate, 5).add();
        nupMin = numericUpDown().below(lastControl).left(7).width((180 - 14) / 2).add();
        nupMin.setMinValue(1);

        label("Max Count:").below(nupRate, 5).left(nupMin, 3).add();
        nupMax = numericUpDown().below(lastControl).left(nupMin, 3).right(7).add();
        nupMax.setMinValue(1);

        label("Biomes:").below(nupMin, 5).add();
        ListBoxDescription<BiomeGenBase> desc = new ListBoxDescription<BiomeGenBase>();
        desc.multiSelect = true;
        desc.elements = Biomes.getBiomes();
        desc.rows = 3;
        desc.sorted = true;
        desc.comparator = Biomes.COMPARATOR;
        lbBiomes = listBox(desc).below(lastControl).fillWidth(7).add();

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
            int min = nupRate.getValue();
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
            editingSpawn.min = nupRate.getValue();
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
