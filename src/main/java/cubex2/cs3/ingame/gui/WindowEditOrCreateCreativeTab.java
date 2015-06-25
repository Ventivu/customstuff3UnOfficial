package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.CreativeTab;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.TextBox;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateCreativeTab extends Window
{
    private final BaseContentPack pack;
    private CreativeTab editingTab;

    private TextBox tbName;
    private TextBox tbLabel;
    private ItemDisplay display;

    public WindowEditOrCreateCreativeTab(BaseContentPack pack)
    {
        super("New Creative Tab", CREATE | CANCEL, 180, 128);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateCreativeTab(CreativeTab editingTab, BaseContentPack pack)
    {
        super("Edit Creative Tab", EDIT | CANCEL, 180, 128);
        this.pack = pack;
        this.editingTab = editingTab;

        initControls();
    }

    public void initControls()
    {
        row("Name:");
        tbName = row(textBox());

        row("Label:");
        tbLabel = row(textBox());

        row("Icon:");
        display = row(itemDisplay());
        display.setDrawSlotBackground();
        display.useSelectItemDialog(false);
        display.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);

        if (editingTab != null)
        {
            tbName.setText(editingTab.name);
            tbName.setEnabled(false);
            tbLabel.setText(editingTab.label);
            display.setItemStack(editingTab.icon);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == btnCreate)
        {
            String name = tbName.getText();
            String label = tbLabel.getText();
            ItemStack icon = display.getItemStack();
            CreativeTab tab = new CreativeTab(name, label, icon, pack);
            tab.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingTab.label = tbLabel.getText();
            editingTab.icon = display.getItemStack();
            editingTab.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }
}
