package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

public class WindowEditOrCreateShapedRecipe extends Window implements IWindowClosedListener
{
    private final BaseContentPack pack;
    private ShapedRecipe editingRecipe;

    private RecipeInputDisplay[] inputDisplays;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;
    private ButtonUpDown btnIncrAmount;
    private ButtonUpDown btnDecrAmount;
    private Label lblWidth;
    private Label lblHeight;

    public WindowEditOrCreateShapedRecipe(BaseContentPack pack)
    {
        super("New Shaped Recipe", CREATE | CANCEL, 180, 150);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateShapedRecipe(ShapedRecipe recipe, BaseContentPack pack)
    {
        super("Edit Shaped Recipe", EDIT | CANCEL, 180, 150);
        this.pack = pack;
        editingRecipe = recipe;

        initControls();
    }

    private void initControls()
    {
        inputDisplays = new RecipeInputDisplay[9];
        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            inputDisplays[i] = recipeInputDisplay().left(33+col*18).top(10+row*18).add().setDrawSlotBackground();
            inputDisplays[i].setClearOnRightClick();
        }

        resultDisplay = itemDisplay().left(121).top(28).add().setDrawSlotBackground();
        resultDisplay.setClearOnRightClick();

        btnIncrAmount = buttonUp().left(138).top(27).add();
        btnDecrAmount = buttonDown().left(138).top(36).add();

        if (editingRecipe != null)
        {
            for (int i = 0; i < editingRecipe.input.length; i++)
            {
                int row = i / editingRecipe.width;
                int col = i % editingRecipe.width;

                inputDisplays[col + row * 3].setRecipeInput(editingRecipe.input[i]);
            }

            resultDisplay.setItemStack(editingRecipe.result);
        }

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).left(93).top(28).size(22, 15).add();

        lblWidth = label("Width: 3").left(7).top(74).add();

        lblHeight = label("Height: 3").left(7).top(84).add();

        updateWidthAndHeight();
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
            int[] minMax = getMinMax();
            int width = minMax[1] - minMax[0] + 1;
            int height = minMax[3] - minMax[2] + 1;

            ItemStack result = resultDisplay.getItemStack();

            RecipeInput[] inputs = new RecipeInput[width * height];
            for (int i = 0; i < inputs.length; i++)
            {
                int row = i / width + minMax[2];
                int col = i % width + minMax[0];

                inputs[i] = inputDisplays[col + row * 3].getRecipeInput();
            }

            ShapedRecipe recipe = new ShapedRecipe(width, height, inputs, result, pack);
            recipe.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            int[] minMax = getMinMax();
            int width = minMax[1] - minMax[0] + 1;
            int height = minMax[3] - minMax[2] + 1;

            ItemStack result = resultDisplay.getItemStack();

            RecipeInput[] inputs = new RecipeInput[width * height];
            for (int i = 0; i < inputs.length; i++)
            {
                int row = i / width + minMax[2];
                int col = i % width + minMax[0];

                inputs[i] = inputDisplays[col + row * 3].getRecipeInput();
            }
            editingRecipe.width = width;
            editingRecipe.height = height;
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

    private void updateButtons()
    {
        boolean validData = resultDisplay.getItemStack() != null;
        for (int i = 0; i < inputDisplays.length; i++)
        {
            if (inputDisplays[i].getRecipeInput() != null)
            {
                break;
            } else if (i == inputDisplays.length - 1)
            {
                validData = false;
            }
        }
        if (editingRecipe == null)
        {
            btnCreate.setEnabled(validData);
        } else
        {
            btnEdit.setEnabled(validData);
        }

        ItemStack stack = resultDisplay.getItemStack();
        btnIncrAmount.setEnabled(stack != null && stack.stackSize < stack.getMaxStackSize());
        btnDecrAmount.setEnabled(stack != null && stack.stackSize > 1);
    }

    private void updateWidthAndHeight()
    {
        int[] minMax = getMinMax();

        int width = minMax[1] - minMax[0] + 1;
        int height = minMax[3] - minMax[2] + 1;
        lblWidth.setText("Width: " + width);
        lblHeight.setText("Height: " + height);
    }

    private int[] getMinMax()
    {
        int minX = 2, maxX = 0, minY = 2, maxY = 0;
        for (int i = 0; i < inputDisplays.length; i++)
        {
            if (inputDisplays[i].getRecipeInput() != null)
            {
                int row = i / 3;
                int col = i % 3;

                if (col < minX)
                {
                    minX = col;
                }
                if (col > maxX)
                {
                    maxX = col;
                }
                if (row < minY)
                {
                    minY = row;
                }
                if (row > maxY)
                {
                    maxY = row;
                }
            }
        }

        return new int[]{minX, maxX, minY, maxY};
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
                updateWidthAndHeight();
                updateButtons();
            }
        }
    }
}
