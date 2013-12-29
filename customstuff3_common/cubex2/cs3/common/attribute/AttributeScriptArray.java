package cubex2.cs3.common.attribute;

import cubex2.cs3.util.JavaScriptHelper;
import org.mozilla.javascript.Script;

public class AttributeScriptArray extends AttributeStringToOtherArray<Script>
{

    public AttributeScriptArray(AttributeCollection attributeCollection, String name, int size)
    {
        super(attributeCollection, name, new Script[size]);
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
