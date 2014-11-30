package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class WindowEditOrCreateOreDictEntry extends Window implements IValidityProvider, IWindowClosedListener<WindowSelectItem>
{
    private IngameContentPack pack;
    private OreDictionaryEntry editingEntry;

    private Label lblItem;
    private Label lblOreClass;
    private Label lblOtherItems;
    private ItemDisplay itemDisplay;
    private TextBox tbOreClass;
    private ItemDisplay[] itemDisplays;

    public WindowEditOrCreateOreDictEntry(IngameContentPack pack)
    {
        super("New Ore Dictionary Entry", CREATE | CANCEL, 182, 201);
        this.pack = pack;
    }

    public WindowEditOrCreateOreDictEntry(OreDictionaryEntry entry, IngameContentPack pack)
    {
        super("Edit Ore Dictionary Entry", EDIT | CANCEL, 182, 201);
        this.pack = pack;
        editingEntry = entry;
    }

    @Override
    public void init()
    {
        super.init();

        lblItem = label("Item:").at(7,7).add();
        itemDisplay = itemDisplay().below(lblItem).add();
        lblOreClass = label("Ore Class:").below(itemDisplay,5).add();
        tbOreClass = textBox().below(lblOreClass).fillWidth(7).height(20).add();
        lblOtherItems = label("Items in ore class:").below(tbOreClass, 10).add();

        itemDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        itemDisplay.setDrawSlotBackground();
        if (editingEntry != null)
            itemDisplay.setItemStack(editingEntry.stack);


        tbOreClass.setValidityProvider(this);
        if (editingEntry != null)
        {
            tbOreClass.setText(String.valueOf(editingEntry.oreClass));
        }

        itemDisplays = new ItemDisplay[9];
        for (int i = 0; i < itemDisplays.length; i++)
        {
            itemDisplays[i] = new ItemDisplay(7 + i * 19, 91, this);
            itemDisplays[i].setDrawSlotBackground();
            addControl(itemDisplays[i]);
        }
        updateItemDisplays();
    }

    private void updateItemDisplays()
    {
        List<ItemStack> ores = OreDictionary.getOres(tbOreClass.getText().trim());
        for (int i = 0; i < itemDisplays.length; i++)
        {
            if (i < ores.size())
            {
                itemDisplays[i].setItemStack(ores.get(i).copy());
            } else
            {
                itemDisplays[i].setItemStack(null);
            }
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
            ItemStack stack = itemDisplay.getItemStack();
            String oreClass = tbOreClass.getText().trim();
            OreDictionaryEntry entry = new OreDictionaryEntry(oreClass, stack, pack);
            entry.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingEntry.stack = itemDisplay.getItemStack();
            editingEntry.oreClass = tbOreClass.getText().trim();
            editingEntry.edit();
            GuiBase.openPrevWindow();
        } else
        {
            handleDefaultButtonClick(c);
        }
    }

    @Override
    public String checkValidity(TextBox tb)
    {
        String message = null;

        String text = tbOreClass.getText().trim();
        if (text.length() == 0)
        {
            message = "Enter a value.";
        }

        updateItemDisplays();

        return message;
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
            itemDisplay.setItemStack(window.getSelectedStack());
    }
}
