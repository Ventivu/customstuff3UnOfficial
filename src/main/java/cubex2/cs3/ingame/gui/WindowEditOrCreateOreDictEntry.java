package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class WindowEditOrCreateOreDictEntry extends Window implements IValidityProvider, IWindowClosedListener<WindowSelectAlias>
{
    private IngameContentPack pack;
    private OreDictionaryEntry editingEntry;

    private Label lblAlias;
    private Label lblOreClass;
    private Label lblOtherItems;
    private AliasDisplay aliasDisplay;
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

        lblAlias = new Label("Alias:", 7, 7, this);
        addControl(lblAlias);

        aliasDisplay = new AliasDisplay(7, 17, this);
        aliasDisplay.setDrawSlotBackground();
        if (editingEntry != null)
            aliasDisplay.setAlias(editingEntry.alias);
        addControl(aliasDisplay);

        lblOreClass = new Label("Ore Class:", 7, 37, this);
        addControl(lblOreClass);

        tbOreClass = new TextBox(7, 47, 168, 20, this);
        if (editingEntry != null)
        {
            tbOreClass.setText(String.valueOf(editingEntry.oreClass));
        }
        tbOreClass.setValidityProvider(this);
        addControl(tbOreClass);

        lblOtherItems = new Label("Items in ore class:", 7, 81, this);
        addControl(lblOtherItems);


        itemDisplays = new ItemDisplay[9];
        for (int i = 0; i < itemDisplays.length; i++)
        {
            itemDisplays[i] = new ItemDisplay(7 + i * 19, 91, this);
            itemDisplays[i].setDrawSlotBackground();
            addControl(itemDisplays[i]);
        }
        updateItemDisplays();

        updateButton(tbOreClass.hasValidText());
    }

    private void updateItemDisplays()
    {
        List<ItemStack> ores = OreDictionary.getOres(tbOreClass.getText().trim());
        for (int i = 0; i < itemDisplays.length; i++)
        {
            if (i < ores.size())
            {
                itemDisplays[i].setItemStack(ores.get(i).copy());
            }
            else
            {
                itemDisplays[i].setItemStack(null);
            }
        }
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == aliasDisplay)
        {
            GuiBase.openWindow(new WindowSelectAlias(pack));
        } else if (c == btnCreate)
        {
            Alias alias = aliasDisplay.getAlias();
            String oreClass = tbOreClass.getText().trim();
            OreDictionaryEntry entry = new OreDictionaryEntry(oreClass, alias, pack);
            entry.apply();
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingEntry.alias = aliasDisplay.getAlias();
            editingEntry.oreClass = tbOreClass.getText().trim();
            editingEntry.edit();
            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private void updateButton(boolean tbValidity)
    {
        if (editingEntry == null)
        {
            btnCreate.setEnabled(tbValidity && aliasDisplay.getAlias() != null);
        } else
        {
            btnEdit.setEnabled(tbValidity && aliasDisplay.getAlias() != null);
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

        updateButton(message == null);
        updateItemDisplays();

        return message;
    }

    @Override
    public void windowClosed(WindowSelectAlias window)
    {
        if (window.getSelectedAlias() != null)
            aliasDisplay.setAlias(window.getSelectedAlias());
        updateButton(tbOreClass.hasValidText());
    }
}
