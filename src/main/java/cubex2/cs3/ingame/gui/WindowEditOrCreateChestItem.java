package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ChestItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.DropBox;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.NumericUpDown;
import cubex2.cs3.lib.Validators;
import cubex2.cs3.util.GeneralHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class WindowEditOrCreateChestItem extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private BaseContentPack pack;
    private ChestItem editingItem;

    private DropBox<String> dbChest;
    private ItemDisplay itemDisplay;
    private NumericUpDown nupMinCount;
    private NumericUpDown nupMaxCount;
    private NumericUpDown nupRarity;

    public WindowEditOrCreateChestItem(BaseContentPack pack)
    {
        super("New Chest Item", CREATE | CANCEL, 180, 200);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateChestItem(ChestItem item, BaseContentPack pack)
    {
        super("New Chest Item", CREATE | CANCEL, 180, 100);
        this.pack = pack;
        editingItem = item;

        initControls();
    }

    private void initControls()
    {
        label("Chest:").at(7, 7).add();
        dbChest = dropBox(GeneralHelper.getChestGenNames()).below(lastControl).fillWidth(7).add();
        dbChest.setSelectedValue(ChestGenHooks.BONUS_CHEST);

        label("Item:").below(dbChest).add();
        itemDisplay = itemDisplay().below(lastControl).add();
        itemDisplay.setDrawSlotBackground();
        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);

        label("Min Count:").below(itemDisplay, 5).add();
        nupMinCount = numericUpDown().below(lastControl).fillWidth(7).add();
        nupMinCount.setMinValue(1);

        label("Max Count:").below(nupMinCount, 5).add();
        nupMaxCount = numericUpDown().below(lastControl).fillWidth(7).add();
        nupMaxCount.setMinValue(1);

        label("Rarity:").below(nupMaxCount, 5).add();
        nupRarity = numericUpDown().below(lastControl).fillWidth(7).add();
        nupRarity.setValue(1);

        if (editingItem != null)
        {
            dbChest.setSelectedValue(editingItem.chest);
            itemDisplay.setItemStack(editingItem.stack);
            nupMinCount.setValue(editingItem.minCount);
            nupMaxCount.setValue(editingItem.maxCount);
            nupRarity.setValue(editingItem.rarity);
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == itemDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem());
        } else if (c == btnCreate)
        {
            String chest = dbChest.getSelectedValue();
            ItemStack stack = itemDisplay.getItemStack();
            int minCount = nupMinCount.getValue();
            int maxCount = nupMaxCount.getValue();
            if (maxCount < minCount)
                maxCount = minCount;
            int rarity = nupRarity.getValue();
            ChestItem chestItem = new ChestItem(stack, chest, minCount, maxCount, rarity, pack);
            chestItem.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingItem.chest = dbChest.getSelectedValue();
            editingItem.stack = itemDisplay.getItemStack();
            editingItem.minCount = nupMinCount.getValue();
            editingItem.maxCount = nupMaxCount.getValue();
            if (editingItem.maxCount < editingItem.minCount)
                editingItem.maxCount = editingItem.minCount;
            editingItem.rarity = nupRarity.getValue();
            editingItem.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());

        btnCreate.setEnabled(itemDisplay.getItemStack() != null);
    }
}
