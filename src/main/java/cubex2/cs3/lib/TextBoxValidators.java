package cubex2.cs3.lib;

import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.TextBox;

public class TextBoxValidators
{
    public static IValidityProvider POSITIVE_INTEGER = new IntegerValidator(0, Integer.MAX_VALUE);

    public static IValidityProvider FLOAT = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something.";
            }
            else if (!tb.getText().matches("(-?)([0-9]+)(\\.[0-9]+)*"))
            {
                message = "Enter a decimal number";
            }

            return message;
        }
    };

    public static class IntegerValidator implements IValidityProvider
    {
        private int minValue;
        private int maxValue;

        public IntegerValidator(int min, int max)
        {
            minValue = min;
            maxValue = max;
        }

        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something.";
            }
            else if (!tb.getText().matches("(-?)[0-9]+"))
            {
                message = "Enter a number.";
            }
            else if (tb.getText().length() > 10 || Long.parseLong(tb.getText()) > (long)maxValue)
            {
                message = "This number is too large. Max value: " + maxValue;
            }
            else if (Integer.parseInt(tb.getText()) < minValue)
            {
                message = "This number is too small. Min value: " + minValue;
            }

            return message;
        }
    }

    public static class MinMaxValidator extends IntegerValidator
    {
        private TextBox tbMin;

        public MinMaxValidator(TextBox tbMin)
        {
            super(0, Integer.MAX_VALUE);
            this.tbMin = tbMin;
        }

        @Override
        public String checkValidity(TextBox tb)
        {
            String message = super.checkValidity(tb);

            if (message == null && tbMin.hasValidValue() && Integer.parseInt(tb.getText()) < Integer.parseInt(tbMin.getText()))
            {
                message = "Max can't be smaller than min";
            }

            return message;
        }
    }
}
