package cubex2.cs3.ingame.gui.control;

import com.google.common.collect.Lists;
import cubex2.cs3.lib.Textures;
import cubex2.cs3.util.GuiHelper;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class TextBox extends Control implements IValidityControl
{
    private GuiTextField textField;
    private IValidityProvider validityProvider;
    private IValueChangedListener valueChangedListener;
    private String validityMessage;
    private boolean isValid = true;
    private Rectangle validityRect;

    private int maxLength = Integer.MAX_VALUE;

    public TextBox(int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
        textField = new GuiTextField(mc.fontRenderer, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        textField.setMaxStringLength(maxLength);
        validityRect = new Rectangle(rect.getX() + rect.getWidth() - 12, rect.getY() + (rect.getHeight() - 9) / 2, 9, 9);
    }

    public String getText()
    {
        return textField.getText();
    }

    public void setText(String value)
    {
        textField.setText(value);
        valueChanged();
    }

    @Override
    public boolean hasValidValue()
    {
        return isValid;
    }

    @Override
    public void setValueChangedListener(IValueChangedListener listener)
    {
        valueChangedListener = listener;
    }

    public void setValidityProvider(IValidityProvider validityProvider)
    {
        this.validityProvider = validityProvider;
    }


    public void setMaxLength(int value)
    {
        maxLength = value;
        textField.setMaxStringLength(value);
    }

    @Override
    public void updateRect()
    {
        super.updateRect();

        String text = getText();
        textField = new GuiTextField(mc.fontRenderer, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        textField.setMaxStringLength(maxLength);
        setText(text);
        validityRect = new Rectangle(rect.getX() + rect.getWidth() - 12, rect.getY() + (rect.getHeight() - 9) / 2, 9, 9);
    }

    @Override
    public void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);
        textField.textboxKeyTyped(c, key);
        if (getText().length() > maxLength)
        {
            setText(getText().substring(0, getText().length() - 1));
        }

        valueChanged();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        textField.mouseClicked(mouseX, mouseY, button);
    }

    private void valueChanged()
    {
        if (validityProvider != null)
        {
            validityMessage = validityProvider.checkValidity(this);
            isValid = validityMessage == null;
        }

        if (valueChangedListener != null)
        {
            valueChangedListener.valueChanged(this);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float renderTick)
    {
        if (!isValid)
        {
            mc.renderEngine.bindTexture(Textures.CONTROLS);
            zLevel += 10.0f;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            drawTexturedModalRect(validityRect.getX(), validityRect.getY(), 200, 72, validityRect.getWidth(), validityRect.getHeight());
            zLevel -= 10.0f;
        }
        textField.drawTextBox();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        if (!isValid && validityRect.contains(mouseX, mouseY))
        {
            GuiHelper.drawHoveringText(Lists.newArrayList(validityMessage), mouseX, mouseY, mc.fontRenderer);
        }
    }
}
