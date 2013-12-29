package cubex2.cs3.common.attribute;

import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.util.JavaScriptHelper;
import cubex2.cs3.util.ZipHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Map;

public class AttributeCollection
{
    protected BaseContentPack pack;
    private Map<String, Attribute<?>> attributeMap = Maps.newHashMap();

    public AttributeCollection(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public BaseContentPack getPack()
    {
        return pack;
    }

    public Attribute<?> getAttribute(String name)
    {
        return attributeMap.containsKey(name) ? attributeMap.get(name) : null;
    }

    public Collection<Attribute<?>> getAttributes()
    {
        return Collections.unmodifiableCollection(attributeMap.values());
    }

    public void addAttribute(Attribute<?> attribute)
    {
        attributeMap.put(attribute.name, attribute);
    }

    public void loadFromJsFile(String pathToFile) throws Exception
    {
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);

        try
        {
            Scriptable scope = JavaScriptHelper.getInstanceScope(cx, pack);
            for (Map.Entry<String, Attribute<?>> entry : attributeMap.entrySet())
            {
                ScriptableObject.putProperty(scope, entry.getKey(), entry.getValue().getJSValue());
            }

            String fileName = pathToFile.substring(pathToFile.lastIndexOf('/') + 1);

            if (pack.directory.isDirectory())
            {
                BufferedReader reader = new BufferedReader(new FileReader(new File(pack.directory, pathToFile)));
                cx.evaluateReader(scope, reader, fileName, 0, null);
                reader.close();
            }
            else
            {
                String content = ZipHelper.readEntryContent(pack.directory, pathToFile);
                cx.evaluateString(scope, content, fileName, 0, null);
            }

            for (Map.Entry<String, Attribute<?>> entry : attributeMap.entrySet())
            {
                Attribute<?> attribute = entry.getValue();
                Object value = ScriptableObject.getProperty(scope, attribute.getName());

                if (!attribute.readValue(ScriptableObject.getProperty(scope, attribute.getName())))
                    throw new IllegalArgumentException(fileName + ": Attribute " + attribute.getName() + ": " + value + " is an invalid value.");
            }
        } finally
        {
            Context.exit();
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList attributeList = new NBTTagList();
        for (Attribute<?> attribute : getAttributes())
        {
            NBTTagCompound attributeTag = new NBTTagCompound();
            attributeTag.setString("AttributeName", attribute.getName());
            attribute.writeToNBT(attributeTag);
            attributeList.appendTag(attributeTag);
        }
        compound.setTag("AttributeList", attributeList);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList attributeList = compound.getTagList("AttributeList");
        for (int i = 0; i < attributeList.tagCount(); i++)
        {
            NBTTagCompound attributeTag = (NBTTagCompound) attributeList.tagAt(i);
            Attribute<?> attribute = getAttribute(attributeTag.getString("AttributeName"));
            attribute.readFromNBT(attributeTag);
        }
    }
}
