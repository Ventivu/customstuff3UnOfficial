package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.TradeRecipe;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.TextBoxValidators;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.lib.Validators;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WindowEditOrCreateTradeRecipe extends Window implements IWindowClosedListener<WindowSelectItem>
{
    private final BaseContentPack pack;
    private TradeRecipe editingRecipe;

    private ItemDisplay input1;
    private ItemDisplay input2;
    private ItemDisplay result;
    private PictureBox pbArrow;
    private ButtonUpDown btnIncrInput1;
    private ButtonUpDown btnDecrInput1;
    private ButtonUpDown btnIncrInput2;
    private ButtonUpDown btnDecrInput2;
    private ButtonUpDown btnIncrResult;
    private ButtonUpDown btnDecrResult;
    private DropBox<Integer> dbProfession;
    private TextBox tbChance;

    public WindowEditOrCreateTradeRecipe(BaseContentPack pack)
    {
        super("New Trade Recipe", CREATE | CANCEL, 180, 150);
        this.pack = pack;

        initControls();
    }

    public WindowEditOrCreateTradeRecipe(TradeRecipe recipe, BaseContentPack pack)
    {
        super("Edit Trade Recipe", EDIT | CANCEL, 180, 150);
        this.pack = pack;
        editingRecipe = recipe;

        initControls();
    }

    private void initControls()
    {
        input1 = itemDisplay().at(37, 25).add();
        input1.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        input1.setDrawSlotBackground();

        btnIncrInput1 = buttonUp().left(input1, 1).top(24).add();
        btnDecrInput1 = buttonDown().left(input1, 1).top(btnIncrInput1, 0).add();

        input2 = itemDisplay().at(67, 25).add();
        input2.setDrawSlotBackground();

        btnIncrInput2 = buttonUp().left(input2, 1).top(24).add();
        btnDecrInput2 = buttonDown().left(input2, 1).top(btnIncrInput2, 0).add();

        pbArrow = pictureBox(Textures.CONTROLS, 218, 18).at(97, 26).size(22, 15).add();

        result = itemDisplay().at(126, 25).add();
        result.setValidatorFunc(Validators.ITEM_DISPLAY_NOT_NULL);
        result.setDrawSlotBackground();

        btnIncrResult = buttonUp().left(result, 1).top(24).add();
        btnDecrResult = buttonDown().left(result, 1).top(btnIncrResult, 0).add();

        Label label = label("Profession").below(input1, 7).add();

        List<Integer> villagers = Lists.newArrayList();
        villagers.add(0);
        villagers.add(1);
        villagers.add(2);
        villagers.add(3);
        villagers.add(4);
        villagers.addAll(VillagerRegistry.getRegisteredVillagers());
        dbProfession = dropBox(villagers.toArray(new Integer[villagers.size()])).below(label).width(60).add();
        dbProfession.setStringProvider(VILLAGER_PROFESSION);
        dbProfession.setSelectedValue(0);

        label = label("Chance").below(dbProfession, 5).add();

        tbChance = textBox().below(label).width(30).add();
        tbChance.setText("0.0");
        tbChance.setValidityProvider(TextBoxValidators.FLOAT_ZERO_ONE);

        if (editingRecipe != null)
        {
            input1.setItemStack(editingRecipe.input1);
            input2.setItemStack(editingRecipe.input2);
            result.setItemStack(editingRecipe.result);
            dbProfession.setSelectedValue(editingRecipe.profession);
        }

        updateButtons();
        updateValidation();
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        if (c == result)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "result");
        } else if (c == input1)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "input1");
        } else if (c == input2)
        {
            GuiBase.openWindow(new WindowSelectItem(false), "input2");
        } else if (c == btnCreate)
        {
            TradeRecipe recipe = new TradeRecipe(dbProfession.getSelectedValue(), input1.getItemStack(), input2.getItemStack(), result.getItemStack(), Float.parseFloat(tbChance.getText()), pack);
            recipe.apply();

            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            editingRecipe.profession = dbProfession.getSelectedValue();
            editingRecipe.input1 = input1.getItemStack();
            editingRecipe.input2 = input2.getItemStack();
            editingRecipe.result = result.getItemStack();
            editingRecipe.chance = Float.parseFloat(tbChance.getText());
            editingRecipe.edit();
            GuiBase.openPrevWindow();
        } else if (c == btnIncrInput1)
        {
            increaseAmount(input1);
        } else if (c == btnDecrInput1)
        {
            decreaseAmount(input1);
        } else if (c == btnIncrInput2)
        {
            increaseAmount(input2);
        } else if (c == btnDecrInput2)
        {
            decreaseAmount(input2);
        } else if (c == btnIncrResult)
        {
            increaseAmount(result);
        } else if (c == btnDecrResult)
        {
            decreaseAmount(result);
        }
        {
            handleDefaultButtonClick(c);
        }
    }

    private void increaseAmount(ItemDisplay display)
    {
        int stackSize = display.getItemStack().stackSize;
        stackSize = Math.min(display.getItemStack().getMaxStackSize(), stackSize + (GuiBase.isShiftKeyDown() ? 5 : 1));
        display.setStackSize(stackSize);
        updateButtons();
    }

    private void decreaseAmount(ItemDisplay display)
    {
        int stackSize = display.getItemStack().stackSize;
        stackSize = Math.max(1, stackSize - (GuiBase.isShiftKeyDown() ? 5 : 1));
        display.setStackSize(stackSize);
        updateButtons();
    }

    private void updateButtons()
    {
        ItemStack stackInput1 = input1.getItemStack();
        ItemStack stackInput2 = input2.getItemStack();
        ItemStack stackResult = result.getItemStack();

        btnIncrInput1.setEnabled(stackInput1 != null && stackInput1.stackSize < stackInput1.getMaxStackSize());
        btnDecrInput1.setEnabled(stackInput1 != null && stackInput1.stackSize > 1);

        btnIncrInput2.setEnabled(stackInput2 != null && stackInput2.stackSize < stackInput2.getMaxStackSize());
        btnDecrInput2.setEnabled(stackInput2 != null && stackInput2.stackSize > 1);

        btnIncrResult.setEnabled(stackResult != null && stackResult.stackSize < stackResult.getMaxStackSize());
        btnDecrResult.setEnabled(stackResult != null && stackResult.stackSize > 1);
    }

    @Override
    public void windowClosed(WindowSelectItem window)
    {
        if (window.getSelectedStack() != null)
        {
            if (window.tag.equals("input1"))
            {
                input1.setItemStack(window.getSelectedStack());
            } else if (window.tag.equals("input2"))
            {
                input2.setItemStack(window.getSelectedStack());
            }
            if (window.tag.equals("result"))
            {
                result.setItemStack(window.getSelectedStack());
            }
            updateButtons();
        }
    }

    private static IStringProvider<Integer> VILLAGER_PROFESSION = new IStringProvider<Integer>()
    {
        @Override
        public String getStringFor(Integer value)
        {
            switch (value)
            {
                case 0:
                    return "Farmer";
                case 1:
                    return "Librarian";
                case 2:
                    return "Priest";
                case 3:
                    return "Blacksmith";
                case 4:
                    return "Butcher";
                default:
                    return value.toString();
            }
        }
    };
}
