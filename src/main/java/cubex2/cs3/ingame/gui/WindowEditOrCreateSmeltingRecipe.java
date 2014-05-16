package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.lib.Textures;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class WindowEditOrCreateSmeltingRecipe extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private IngameContentPack pack;
    private SmeltingRecipe editingRecipe;

    /*private Label lblInput;
    private Label lblResult;*/
    private ItemDisplay inputDisplay;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;

    public WindowEditOrCreateSmeltingRecipe(IngameContentPack pack)
    {
        super("New Smelting Recipe", CREATE | CANCEL, 180, 100);
        this.pack = pack;
    }

    public WindowEditOrCreateSmeltingRecipe(SmeltingRecipe recipe, IngameContentPack pack)
    {
        super("Edit Smelting Recipe", EDIT | CANCEL, 180, 100);
        this.pack = pack;
        editingRecipe = recipe;
    }

    @Override
    public void init()
    {
        super.init();

        inputDisplay = new ItemDisplay(55, 25, this);
        inputDisplay.setDrawSlotBackground();

        resultDisplay = new ItemDisplay(55 + 30 + 22, 25, this);
        resultDisplay.setDrawSlotBackground();

        if (editingRecipe != null)
        {
            inputDisplay.setItemStack(editingRecipe.input);
            resultDisplay.setItemStack(editingRecipe.result);
        }

        addControl(inputDisplay);
        addControl(resultDisplay);

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 55 + 18 + 4, 25, 22, 15, this);
        addControl(pbArrow);

        updateButton();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (c == inputDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(), "input");
        } else if (c == resultDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "result");
        } else if (c == btnCreate)
        {
            ItemStack input = inputDisplay.getItemStack();
            ItemStack result = resultDisplay.getItemStack();

            SmeltingRecipe recipe = new SmeltingRecipe(input, result, pack);
            recipe.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingRecipe.input = inputDisplay.getItemStack();
            editingRecipe.result = resultDisplay.getItemStack();
            editingRecipe.edit();
            GuiBase.openPrevWindow();
        } else
        {
            super.controlClicked(c, mouseX, mouseY, button);
        }
    }

    private void updateButton()
    {
        boolean validData = inputDisplay.getItemStack() != null && resultDisplay.getItemStack() != null && FurnaceRecipes.smelting().getSmeltingResult(inputDisplay.getItemStack()) == null;
        if (editingRecipe == null)
        {
            btnCreate.setEnabled(validData);
        } else
        {
            btnEdit.setEnabled(validData);
        }
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
        {
            ItemDisplay display = window.tag.equals("input") ? inputDisplay : resultDisplay;
            display.setItemStack(window.getSelectedStack());
        }
        updateButton();
    }
}
