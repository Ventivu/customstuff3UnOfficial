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
    private IValueListener valueChangedListener;
    private String validityMessage;
    private boolean isValid = true;
    private Rectangle validityRect;
    private boolean numbersOnly = false;
    private boolean isFocused = false;

    private int maxLength = Integer.MAX_VALUE;

    public TextBox(int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(width, height, anchor, offsetX, offsetY, parent);
        textField = new GuiTextField(mc.fontRenderer, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        textField.setMaxStringLength(maxLength);
        validityRect = new Rectangle(bounds.getX() + bounds.getWidth() - 12, bounds.getY() + (bounds.getHeight() - 9) / 2, 9, 9);
    }

    public String getText()
    {
        return textField.getText();
    }

    public void setText(String value)
    {
        if (numbersOnly)
        {
            value = value.replaceAll("[\\D]", "");
        }
        textField.setText(value);
        valueChanged();
    }

    public void setNumbersOnly(boolean numbersOnly)
    {
        this.numbersOnly = numbersOnly;
        setText(textField.getText());
    }

    @Override
    public boolean hasValidValue()
    {
        return isValid;
    }

    @Override
    public void setValueChangedListener(IValueListener listener)
    {
        valueChangedListener = listener;
        valueChanged();
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

    public boolean isFocused()
    {
        return textField.isFocused();
    }

    public void setFocused(boolean value)
    {
        isFocused = value;
        textField.setFocused(value);
    }

    @Override
    public void onParentResized()
    {
        super.onParentResized();

        String text = getText();
        textField = new GuiTextField(mc.fontRenderer, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        textField.setFocused(isFocused);
        textField.setMaxStringLength(maxLength);
        setText(text);
        validityRect = new Rectangle(bounds.getX() + bounds.getWidth() - 12, bounds.getY() + (bounds.getHeight() - 9) / 2, 9, 9);
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
        if (numbersOnly)
        {
            textField.setText(textField.getText().replaceAll("[\\D]", ""));
        }

        valueChanged();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, boolean intoControl)
    {
        textField.mouseClicked(mouseX, mouseY, button);
        isFocused = textField.isFocused();
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
            valueChangedListener.onValueChanged(this);
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
