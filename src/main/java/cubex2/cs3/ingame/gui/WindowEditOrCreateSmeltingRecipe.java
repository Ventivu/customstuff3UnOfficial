package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.Alias;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.AliasDisplay;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.lib.Textures;
import net.minecraft.item.crafting.FurnaceRecipes;

public class WindowEditOrCreateSmeltingRecipe extends Window implements IWindowClosedListener<WindowSelectAlias>
{
    private IngameContentPack pack;
    private SmeltingRecipe editingRecipe;

    /*private Label lblInput;
    private Label lblResult;*/
    private AliasDisplay inputDisplay;
    private AliasDisplay resultDisplay;
    private PictureBox pbArrow;

    public WindowEditOrCreateSmeltingRecipe(IngameContentPack pack)
    {
        super("New Smelting Recipe", CREATE | CANCEL, 180, 201);
        this.pack = pack;
    }

    public WindowEditOrCreateSmeltingRecipe(SmeltingRecipe recipe, IngameContentPack pack)
    {
        super("Edit Smelting Recipe", EDIT | CANCEL, 180, 201);
        this.pack = pack;
        editingRecipe = recipe;
    }

    @Override
    public void init()
    {
        super.init();

        inputDisplay = new AliasDisplay(7, 17, this);
        inputDisplay.setDrawSlotBackground();

        resultDisplay = new AliasDisplay(7 + 30 + 22, 17, this);
        resultDisplay.setDrawSlotBackground();

        if (editingRecipe != null)
        {
            inputDisplay.setAlias(editingRecipe.input);
            resultDisplay.setAlias(editingRecipe.result);
        }

        addControl(inputDisplay);
        addControl(resultDisplay);

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 7 + 18 + 4, 17, 22, 15, this);
        addControl(pbArrow);

        updateButton();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == inputDisplay)
        {
            GuiBase.openWindow(new WindowSelectAlias(pack), "input");
        } else if (c == resultDisplay)
        {
            GuiBase.openWindow(new WindowSelectAlias(pack), "result");
        } else if (c == btnCreate)
        {
            Alias input = inputDisplay.getAlias();
            Alias result = resultDisplay.getAlias();

            SmeltingRecipe recipe = new SmeltingRecipe(input, result, pack);
            recipe.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingRecipe.input = inputDisplay.getAlias();
            editingRecipe.result = resultDisplay.getAlias();
            editingRecipe.edit();
            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private void updateButton()
    {
        boolean validData = inputDisplay.getAlias() != null && resultDisplay.getAlias() != null && FurnaceRecipes.smelting().getSmeltingResult(inputDisplay.getAlias().getItemStack()) == null;
        if (editingRecipe == null)
        {
            btnCreate.setEnabled(validData);
        } else
        {
            btnEdit.setEnabled(validData);
        }
    }

    @Override
    public void windowClosed(WindowSelectAlias window)
    {
        if (window.getSelectedAlias() != null)
        {
            AliasDisplay display = window.tag.equals("input") ? inputDisplay : resultDisplay;
            display.setAlias(window.getSelectedAlias());
        }
        updateButton();
    }
}
