package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.ArrayHelper;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.item.ItemStack;

public class WindowEditOrCreateShapedRecipe extends Window implements IWindowClosedListener
{
    private final IngameContentPack pack;
    private ShapedRecipe editingRecipe;

    private RecipeInputDisplay[] inputDisplays;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;
    private ButtonUpDown btnIncrAmount;
    private ButtonUpDown btnDecrAmount;
    private Label lblWidth;
    private Label lblHeight;

    public WindowEditOrCreateShapedRecipe(IngameContentPack pack)
    {
        super("New Shaped Recipe", CREATE | CANCEL, 180, 150);
        this.pack = pack;
    }

    public WindowEditOrCreateShapedRecipe(ShapedRecipe recipe, IngameContentPack pack)
    {
        super("Edit Shaped Recipe", EDIT | CANCEL, 180, 150);
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
        addControl(resultDisplay);

        btnIncrAmount = new ButtonUpDown(true, 138, 10 + 17, this);
        addControl(btnIncrAmount);

        btnDecrAmount = new ButtonUpDown(false, 138, 10 + 17 + 9, this);
        addControl(btnDecrAmount);

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

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 93, 10 + 18, 22, 15, this);
        addControl(pbArrow);

        lblWidth = new Label("Width: 3", 7, 74, this);
        addControl(lblWidth);

        lblHeight = new Label("Height: 3", 7, 84, this);
        addControl(lblHeight);

        updateWidthAndHeight();
        updateButtons();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button != 0 && !(c instanceof RecipeInputDisplay) && c != resultDisplay)
            return;

        if (c == resultDisplay)
        {
            if (button == 0)
            {
                GuiBase.openWindow(new WindowSelectItem(false), "result");
            }
            else if (button == 1)
            {
                resultDisplay.setItemStack(null);
            }
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
            int index = ArrayHelper.indexOf(c, inputDisplays);

            if (index != -1)
            {
                if (button == 0)
                {
                    GuiBase.openWindow(new WindowSelectRecipeInput(pack), "" + index);
                }
                else if (button == 1)
                {
                    inputDisplays[index].setRecipeInput(null);
                }
            } else
            {
                super.controlClicked(c, mouseX, mouseY, button);
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
