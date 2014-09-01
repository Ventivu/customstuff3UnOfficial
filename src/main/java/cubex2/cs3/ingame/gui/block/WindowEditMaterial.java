package cubex2.cs3.ingame.gui.block;

import cubex2.cs3.common.WrappedBlock;
import cubex2.cs3.ingame.gui.GuiBase;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.IStringProvider;
import cubex2.cs3.lib.Materials;
import net.minecraft.block.material.Material;

public class WindowEditMaterial extends Window implements IStringProvider<Material>
{
    private WrappedBlock wrappedBlock;

    private DropBox<Material> dbTabs;

    public WindowEditMaterial(WrappedBlock block)
    {
        super("creativeTab", EDIT | CANCEL, 170, 100);
        wrappedBlock = block;
    }

    @Override
    public void init()
    {
        super.init();

        dbTabs = dropBox(Materials.getAllMaterials()).y(7).fillWidth(7).add();
        dbTabs.setStringProvider(this);
        dbTabs.setSelectedValue(wrappedBlock.container.material);

        label("You need to restart Minecraft\nfor the change to happen.").below(dbTabs, 10).add();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0)
            return;

        if (c == btnEdit)
        {
            wrappedBlock.container.material = dbTabs.getSelectedValue();
            wrappedBlock.getPack().save();

            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    @Override
    public String getStringFor(Material value)
    {
        return Materials.getMaterialName(value);
    }
}
