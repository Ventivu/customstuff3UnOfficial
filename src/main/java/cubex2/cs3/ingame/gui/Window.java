package cubex2.cs3.ingame.gui;

import com.google.common.collect.Lists;
import cubex2.cs3.ingame.gui.control.*;
import cubex2.cs3.lib.Color;
import cubex2.cs3.lib.Textures;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class Window extends ControlContainer implements IValueChangedListener
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
        super(0, 0, width, height, null);
        this.usedControls = 0;
    }

    public Window(int usedControls, int width, int height)
    {
        super(0, 0, width, height, null);
        this.usedControls = usedControls;
    }

    public Window(String title, int width, int height)
    {
        super(0, 0, width, height, null);
        this.title = title;
        this.usedControls = 0;
    }

    public Window(String title, int usedControls, int width, int height)
    {
        super(0, 0, width, height, null);
        this.usedControls = usedControls;
        this.title = title;
    }

    public void init()
    {
        controls.clear();
        validityControls.clear();

        if ((usedControls & BACK) == BACK)
        {
            btnBack = new Button("Back", -7, -7, 60, this);
            addControl(btnBack);
        }
        if ((usedControls & CANCEL) == CANCEL)
        {
            btnCancel = new Button("Cancel", -7, -7, 60, this);
            addControl(btnCancel);
        }
        if ((usedControls & SELECT) == SELECT)
        {
            btnSelect = new Button("Select", 7, -7, 60, this);
            addControl(btnSelect);
        }
        int xOffset = 0;
        if ((usedControls & NEW) == NEW)
        {
            btnNew = new Button("New", 7, -7, 60, this);
            addControl(btnNew);
            xOffset += 63;
        }
        if ((usedControls & CREATE) == CREATE)
        {
            btnCreate = new Button("Create", 7 + xOffset, -7, 60, this);
            addControl(btnCreate);
            xOffset += 63;
        }
        if ((usedControls & EDIT) == EDIT)
        {
            btnEdit = new Button("Edit", 7 + xOffset, -7, 60, this);
            addControl(btnEdit);
            xOffset += 63;
        }
        if ((usedControls & DELETE) == DELETE)
        {
            btnDelete = new Button("Delete", 7 + xOffset, -7, 60, this);
            addControl(btnDelete);
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
    protected void controlClicked(Control c, int mouseX, int mouseY, int button)
    {
        if (button == 0)
        {
            if (c == btnBack)
            {
                GuiBase.openPrevWindow();
            } else if (c == btnCancel)
            {
                GuiBase.openPrevWindow();
            }
        }
    }

    @Override
    public void valueChanged(Control c)
    {
        boolean allValidValues = true;
        for (int i = 0; i < validityControls.size(); i++)
        {
            if (!validityControls.get(i).hasValidValue())
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
    public void draw(int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.renderEngine.bindTexture(Textures.BG);

        int heightChange = rect.getHeight() % 2 != 0 ? 1 : 0;
        int widthChange = rect.getWidth() % 2 != 0 ? 1 : 0;

        // Top Left
        drawTexturedModalRect(rect.getX(), rect.getY(), 0, 0, rect.getWidth() / 2 + widthChange, rect.getHeight() / 2 + heightChange);
        // Top Right
        drawTexturedModalRect(rect.getX() + rect.getWidth() / 2 + widthChange, rect.getY(), 256 - rect.getWidth() / 2, 0, rect.getWidth() / 2, rect.getHeight() / 2 + heightChange);
        // Bottom Left
        drawTexturedModalRect(rect.getX(), rect.getY() + rect.getHeight() / 2 + heightChange, 0, 256 - rect.getHeight() / 2, rect.getWidth() / 2 + widthChange, rect.getHeight() / 2);
        // Bottom Right
        drawTexturedModalRect(rect.getX() + rect.getWidth() / 2 + widthChange, rect.getY() + rect.getHeight() / 2 + heightChange, 256 - rect.getWidth() / 2, 256 - rect.getHeight() / 2, rect.getWidth() / 2, rect.getHeight() / 2);

        if (title != null)
        {
            mc.renderEngine.bindTexture(Textures.CONTROLS2);
            int width = mc.fontRenderer.getStringWidth(title) + 14;

            drawTexturedModalRect(rect.getX() + (rect.getWidth() - width) / 2, rect.getY() - 15, 156, 0, width / 2, 18);
            drawTexturedModalRect(rect.getX() + (rect.getWidth() - width) / 2 + width / 2, rect.getY() - 15, 256 - width / 2, 0, width / 2, 18);

            mc.fontRenderer.drawString(title, rect.getX() + (rect.getWidth() - mc.fontRenderer.getStringWidth(title)) / 2, rect.getY() - 10, Color.BLACK);
        }

        super.draw(mouseX, mouseY);
    }

}
