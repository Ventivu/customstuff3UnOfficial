package cubex2.cs3.ingame.gui.common;

import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.block.attributes.BlockAttributes;
import cubex2.cs3.common.attribute.AttributeContainer;
import cubex2.cs3.lib.Docs;
import cubex2.cs3.lib.Scripts;

public class WindowScriptInfo extends WindowDocs
{
    private final String scriptName;
    private final AttributeContainer container;

    public WindowScriptInfo(String scriptName, AttributeContainer container)
    {
        super(scriptName, getDocs(scriptName, container));
        this.scriptName = scriptName;
        this.container = container;

        TriggerType type = container instanceof BlockAttributes ? TriggerType.BLOCK : TriggerType.ITEM;
        String[] objects = Scripts.getInfo(scriptName, type).availableObjects;
        for (String o : objects)
        {
            content.button(o).below(content.lastControl).size(100, 13).add();
        }
        scroll.automaticTotalHeight();
    }

    private static String getDocs(String scriptName, AttributeContainer container)
    {
        TriggerType type = container instanceof BlockAttributes ? TriggerType.BLOCK : TriggerType.ITEM;
        String desc = Docs.get(type.name().toLowerCase() + "/" + scriptName + ".txt").trim();
        desc += "\n\nAvailable objects:\n";

        return desc;
    }
}
