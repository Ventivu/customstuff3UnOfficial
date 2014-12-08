package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.common.ShapelessRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class WindowEditOrCreateShapelessRecipe extends Window implements IWindowClosedListener
{
    private final IngameContentPack pack;
    private ShapelessRecipe editingRecipe;

    private RecipeInputDisplay[] inputDisplays;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;
    private ButtonUpDown btnIncrAmount;
    private ButtonUpDown btnDecrAmount;

    public WindowEditOrCreateShapelessRecipe(IngameContentPack pack)
    {
        super("New Shapeless Recipe", CREATE | CANCEL, 180, 150);
        this.pack = pack;
    }

    public WindowEditOrCreateShapelessRecipe(ShapelessRecipe recipe, IngameContentPack pack)
    {
        super("Edit Shapeless Recipe", EDIT | CANCEL, 180, 150);
        this.pack = pack;
        editingRecipe = recipe;
    }

    @Override
    public void init()
    {
        super.init();

        inputDisplays = new RecipeInputDisplay[9];
        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            inputDisplays[i] = new RecipeInputDisplay(33 + col * 18, 10 + row * 18, this).setDrawSlotBackground();
            addControl(inputDisplays[i]);
        }

        resultDisplay = new ItemDisplay(121, 10 + 18, this).setDrawSlotBackground();
        resultDisplay.setClearOnRightClick();
        addControl(resultDisplay);

        btnIncrAmount = new ButtonUpDown(true, 138, 10 + 17, this);
        addControl(btnIncrAmount);

        btnDecrAmount = new ButtonUpDown(false, 138, 10 + 17 + 9, this);
        addControl(btnDecrAmount);

        if (editingRecipe != null)
        {
            for (int i = 0; i < editingRecipe.input.length; i++)
            {

                inputDisplays[i].setRecipeInput(editingRecipe.input[i]);
                inputDisplays[i].setClearOnRightClick();
            }

            resultDisplay.setItemStack(editingRecipe.result);
        }

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 93, 10 + 18, 22, 15, this);
        addControl(pbArrow);

        updateButtons();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == resultDisplay)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "result");
        } else if (c == btnCreate)
        {
            ItemStack result = resultDisplay.getItemStack();
            RecipeInput[] inputs = getRecipeInput();

            ShapelessRecipe recipe = new ShapelessRecipe(inputs, result, pack);
            recipe.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            ItemStack result = resultDisplay.getItemStack();
            RecipeInput[] inputs = getRecipeInput();

            editingRecipe.result = result;
            editingRecipe.input = inputs;
            editingRecipe.edit();

            GuiBase.openPrevWindow();
        } else if (c == btnIncrAmount)
        {
            int stackSize = resultDisplay.getItemStack().stackSize;
            stackSize = Math.min(resultDisplay.getItemStack().getMaxStackSize(), stackSize + (GuiBase.isShiftKeyDown() ? 5 : 1));
            resultDisplay.setStackSize(stackSize);
            updateButtons();
        } else if (c == btnDecrAmount)
        {
            int stackSize = resultDisplay.getItemStack().stackSize;
            stackSize = Math.max(1, stackSize - (GuiBase.isShiftKeyDown() ? 5 : 1));
            resultDisplay.setStackSize(stackSize);
            updateButtons();
        } else
        {
            int index = ArrayUtils.indexOf(inputDisplays, c);

            if (index != -1)
            {
                GuiBase.openWindow(new WindowSelectRecipeInput(pack), "" + index);
            } else
            {
                handleDefaultButtonClick(c);
            }
        }
    }

    private RecipeInput[] getRecipeInput()
    {
        List<RecipeInput> inputs = Lists.newArrayList();
        for (RecipeInputDisplay display : inputDisplays)
        {
            if (display.getRecipeInput() != null)
            {
                inputs.add(display.getRecipeInput());
            }
        }

        return inputs.toArray(new RecipeInput[inputs.size()]);
    }

    private void updateButtons()
    {
        boolean validData = resultDisplay.getItemStack() != null && getRecipeInput().length > 0;

        if (editingRecipe == null)
        {
            btnCreate.setEnabled(validData);
        } else
        {
            btnEdit.setEnabled(validData);
        }

        ItemStack result = resultDisplay.getItemStack();
        btnIncrAmount.setEnabled(result != null && result.stackSize < result.getMaxStackSize());
        btnDecrAmount.setEnabled(result != null && result.stackSize > 1);
    }

    @Override
    public void windowClosed(Window window)
    {
        if (window.tag.equals("result"))
        {
            WindowSelectItem w = (WindowSelectItem) window;
            if (w.getSelectedStack() != null)
            {
                resultDisplay.setItemStack(w.getSelectedStack());
                updateButtons();
            }
        } else
        {
            WindowSelectRecipeInput w = (WindowSelectRecipeInput) window;
            if (w.getSelectedInput() != null)
            {
                inputDisplays[Integer.parseInt(w.tag)].setRecipeInput(w.getSelectedInput());
                updateButtons();
            }
        }
    }
}
