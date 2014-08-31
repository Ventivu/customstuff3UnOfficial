package cubex2.cs3.lib;

import cubex2.cs3.ingame.gui.control.IValidityProvider;
import cubex2.cs3.ingame.gui.control.TextBox;

public class TextBoxValidators
{
    public static IValidityProvider POSITIVE_INTEGER = new IValidityProvider()
    {
        @Override
        public String checkValidity(TextBox tb)
        {
            String message = null;

            if (tb.getText().length() == 0)
            {
                message = "Enter something.";
            }
            else if (!tb.getText().matches("[0-9]+"))
            {
                message = "Enter a positive number.";
            }
            else if (tb.getText().length() > 10 || Long.parseLong(tb.getText()) > (long)Integer.MAX_VALUE)
            {
                message = "This number is too large.";
            }

            return message;
        }
    };

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
}
