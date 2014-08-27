package cubex2.cs3.ingame.gui.control;

import com.google.common.base.Predicate;

public abstract class ValidityControl<T extends Control> extends Control implements IValidityControl
{
    protected Predicate<T> validatorFunc = null;
    protected IValueChangedListener valueChangedListener = null;

    public ValidityControl(int x, int y, int width, int height, Control parent)
    {
        super(x, y, width, height, parent);
    }

    public void setValidatorFunc(Predicate<T> func)
    {
        validatorFunc = func;
    }

    @Override
    public boolean hasValidValue()
    {
        return validatorFunc == null || validatorFunc.apply((T) this);
    }

    @Override
    public void setValueChangedListener(IValueChangedListener listener)
    {
        valueChangedListener = listener;
    }

    protected void valueChanged()
    {
        if (valueChangedListener != null)
        {
            valueChangedListener.valueChanged(this);
        }
    }
}
