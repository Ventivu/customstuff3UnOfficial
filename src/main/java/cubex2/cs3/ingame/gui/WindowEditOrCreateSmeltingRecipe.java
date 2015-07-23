package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateSmeltingRecipe extends WindowEditOrCreate<SmeltingRecipe>
{
    private ItemDisplay inputDisplay;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;

    public WindowEditOrCreateSmeltingRecipe(BaseContentPack pack)
    {
        super("New Smelting Recipe", 180, 100, pack);
    }

    public WindowEditOrCreateSmeltingRecipe(SmeltingRecipe recipe, BaseContentPack pack)
    {
        super("Edit Smelting Recipe", 180, 100, recipe, pack);
    }

    @Override
    protected void initControls()
    {
        inputDisplay = itemDisplay().at(55, 25).add();
        inputDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_SMELTING_INPUT);
        inputDisplay.setDrawSlotBackground();
        inputDisplay.useSelectItemDialog(false);

        resultDisplay = itemDisplay().at(55 + 30 + 22, 25).add();
        resultDisplay.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        resultDisplay.setDrawSlotBackground();
        resultDisplay.useSelectItemDialog(false);

        if (editingContent != null)
        {
            inputDisplay.setItemStack(editingContent.input);
            resultDisplay.setItemStack(editingContent.result);
        }

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).at(55 + 18 + 4, 25).size(22, 15).add();

        updateValidation();
    }

    @Override
    protected SmeltingRecipe createContent()
    {
        ItemStack input = inputDisplay.getItemStack();
        ItemStack result = resultDisplay.getItemStack();

        return new SmeltingRecipe(input, result, pack);
    }

    @Override
    protected void editContent()
    {
        editingContent.input = inputDisplay.getItemStack();
        editingContent.result = resultDisplay.getItemStack();
    }
}
