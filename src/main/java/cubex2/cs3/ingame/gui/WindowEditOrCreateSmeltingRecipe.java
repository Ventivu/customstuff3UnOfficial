package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateSmeltingRecipe extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private BaseContentPack pack;
    private SmeltingRecipe editingRecipe;

    private ItemDisplay inputDisplay;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;

    public WindowEditOrCreateSmeltingRecipe(BaseContentPack pack)
    {
        super("New Smelting Recipe", CREATE | CANCEL, 180, 100);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateSmeltingRecipe(SmeltingRecipe recipe, BaseContentPack pack)
    {
        super("Edit Smelting Recipe", EDIT | CANCEL, 180, 100);
        this.pack = pack;
        editingRecipe = recipe;

        initControls();
    }

    private void initControls()
    {
        inputDisplay = itemDisplay().at(55, 25).add();
        inputDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_SMELTING_INPUT);
        inputDisplay.setDrawSlotBackground();

        resultDisplay = itemDisplay().at(55 + 30 + 22, 25).add();
        resultDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        resultDisplay.setDrawSlotBackground();

        if (editingRecipe != null)
        {
            inputDisplay.setItemStack(editingRecipe.input);
            resultDisplay.setItemStack(editingRecipe.result);
        }

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).at(55 + 18 + 4, 25).size(22, 15).add();

        updateValidation();
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
            handleDefaultButtonClick(c);
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
    }
}
