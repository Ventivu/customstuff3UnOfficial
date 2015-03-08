package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class Window extends ControlContainer implements IValueListener
{
    protected static final int BACK = 1;
    protected static final int CANCEL = 2;
    protected static final int NEW = 4;
    protected static final int CREATE = 8;
    protected static final int DELETE = 16;
    protected static final int EDIT = 32;
    protected static final int SELECT = 64;


    public String tag = null;

    protected Button btnBack;
    protected Button btnCancel;
    protected Button btnNew;
    protected Button btnCreate;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSelect;
    protected Button btnPlus;

    private final int usedControls;

    private String title;

    private List<IValidityControl> validityControls = Lists.newArrayList();

    public Window(int width, int height)
    {
        this(null, 0, width, height);
    }

    public Window(int usedControls, int width, int height)
    {
        this(null, usedControls, width, height);
    }

    public Window(String title, int width, int height)
    {
        this(title, 0, width, height);
    }

    public Window(String title, int usedControls, int width, int height)
    {
        super(width, height, null, 0, 0, null);
        this.usedControls = usedControls;
        this.title = title;

        init();
    }

    private void init()
    {
        controls.clear();
        validityControls.clear();

        if ((usedControls & BACK) == BACK)
        {
            btnBack = button("Back").right(7).bottom(7).add();
        }
        if ((usedControls & CANCEL) == CANCEL)
        {
            btnCancel = button("Cancel").right(7).bottom(7).add();
        }
        if ((usedControls & SELECT) == SELECT)
        {
            btnSelect = button("Select").left(7).bottom(7).add();
        }
        int xOffset = 0;
        if ((usedControls & NEW) == NEW)
        {
            btnNew = button("New").left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & CREATE) == CREATE)
        {
            btnCreate = button("Create").left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & EDIT) == EDIT)
        {
            btnEdit = button("Edit").left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
        if ((usedControls & DELETE) == DELETE)
        {
            btnDelete = button("Delete").left(7 + xOffset).bottom(7).add();
            xOffset += 63;
        }
    }

    @Override
    public void addControl(Control c)
    {
        if (c instanceof IValidityControl)
        {
            IValidityControl vc = (IValidityControl) c;
            validityControls.add(vc);
            vc.setValueChangedListener(this);
        }

        super.addControl(c);
    }

    @Override
    protected void controlClicked(Control c, int mouseX, int mouseY)
    {
        handleDefaultButtonClick(c);
    }

    /**
     * Handles mouse-click on edit, back and cancel buttons, if c is one of those.
     *
     * @param c The clicked control.
     */
    protected void handleDefaultButtonClick(Control c)
    {
        if (c == btnBack)
        {
            GuiBase.openPrevWindow();
        } else if (c == btnCancel)
        {
            GuiBase.openPrevWindow();
        } else if (c == btnEdit)
        {
            handleEditButtonClicked();
        }
    }

    protected void handleEditButtonClicked()
    {

    }

    @Override
    public void onValueChanged(Control c)
    {
        updateValidation();
    }

    protected void updateValidation()
    {
        boolean allValidValues = true;
        for (IValidityControl c : validityControls)
        {
            if (!c.hasValidValue())
            {
                allValidValues = false;
                break;
            }
        }

        if (btnCreate != null)
            btnCreate.setEnabled(allValidValues);

        if (btnEdit != null)
            btnEdit.setEnabled(allValidValues);
    }


    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.renderEngine.bindTexture(Textures.BG);

        GuiHelper.drawRectSliced(bounds, 0, 0, 256, 256);

        if (title != null)
        {
            mc.renderEngine.bindTexture(Textures.CONTROLS2);
            int width = mc.fontRenderer.getStringWidth(title) + 14;

            drawTexturedModalRect(bounds.getX() + (bounds.getWidth() - width) / 2, bounds.getY() - 15, 156, 0, width / 2, 18);
            drawTexturedModalRect(bounds.getX() + (bounds.getWidth() - width) / 2 + width / 2, bounds.getY() - 15, 256 - width / 2, 0, width / 2, 18);

            mc.fontRenderer.drawString(title, bounds.getX() + (bounds.getWidth() - mc.fontRenderer.getStringWidth(title)) / 2, bounds.getY() - 10, Color.BLACK);
        }

        super.draw(mouseX, mouseY, renderTick);
    }

}
