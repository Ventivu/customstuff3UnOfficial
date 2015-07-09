package cubex2.cs3.ingame.docs;

import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.StringProviderPurpose;

public class NamedLink implements IPurposeStringProvider, Comparable<NamedLink>
{
    public String text;
    public String link;

    public NamedLink(String text, String link)
    {
        this.text = text;
        this.link = link;
    }

    @Override
    public String getStringForPurpose(StringProviderPurpose purpose)
    {
        return text;
    }

    @Override
    public int compareTo(NamedLink o)
    {
        return text.compareTo(o.text);
    }
}
