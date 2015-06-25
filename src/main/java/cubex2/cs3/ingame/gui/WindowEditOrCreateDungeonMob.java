package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.DungeonMob;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.util.GeneralHelper;

public class WindowEditOrCreateDungeonMob extends Window
{
    private final BaseContentPack pack;
    private DungeonMob editingMob;

    private DropBox<String> dbMob;
    private NumericUpDown nupRarity;

    public WindowEditOrCreateDungeonMob(BaseContentPack pack)
    {
        super("New Dungeon Mob", CREATE | CANCEL, 180, 105);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateDungeonMob(DungeonMob editingMob, BaseContentPack pack)
    {
        super("Edit Dungeon Mob", EDIT | CANCEL, 180, 105);
        this.pack = pack;
        this.editingMob = editingMob;

        initControls();
    }

    private void initControls()
    {
        row("Mob:");
        dbMob = row(dropBox(GeneralHelper.getMobNames()));

        row("Rarity:");
        nupRarity = row(numericUpDown());
        nupRarity.setMinValue(1);

        if (editingMob != null)
        {
            dbMob.setSelectedValue(editingMob.mob);
            nupRarity.setValue(editingMob.rarity);
        } else
        {
            dbMob.setSelectedValue("Creeper");
            nupRarity.setValue(100);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            String mob = dbMob.getSelectedValue();
            int rarity = nupRarity.getValue();
            DungeonMob dungeonMob = new DungeonMob(mob, rarity, pack);
            dungeonMob.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingMob.mob = dbMob.getSelectedValue();
            editingMob.rarity = nupRarity.getValue();
            editingMob.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
