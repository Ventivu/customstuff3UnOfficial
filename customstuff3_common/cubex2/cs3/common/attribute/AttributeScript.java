package cubex2.cs3.common.attribute;

import cubex2.cs3.util.JavaScriptHelper;
import org.mozilla.javascript.Script;

public class AttributeScript extends AttributeStringToOther<Script>
{

    public AttributeScript(AttributeCollection attributeCollection, String name)
    {
        super(attributeCollection, name, null);
        allowNull = true;
    }

    @Override
    public Script getValue(String s)
    {
        if (s == null)
            return null;
        return JavaScriptHelper.createScript(s, getName());
    }

    @Override
    public String getString(Script value)
    {
        return null;
    }

}
