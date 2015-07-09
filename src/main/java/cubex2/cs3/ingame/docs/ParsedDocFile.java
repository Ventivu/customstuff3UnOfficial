package cubex2.cs3.ingame.docs;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs3.api.scripting.TriggerType;
import cubex2.cs3.ingame.gui.common.WindowDocs;
import cubex2.cs3.ingame.gui.control.ControlContainer;
import cubex2.cs3.ingame.gui.control.ScrollContainer;
import cubex2.cs3.ingame.gui.control.listbox.ListBox;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;
import cubex2.cs3.lib.Scripts;
import cubex2.cs3.util.ClientHelper;

import java.util.List;
import java.util.Map;

public class ParsedDocFile
{
    private static final Map<String, ParsedDocFile> docs = Maps.newHashMap();

    public static ParsedDocFile fromPath(String path)
    {
        if (docs.containsKey(path))
            return docs.get(path);
        ParsedDocFile doc = fromString(ClientHelper.loadDocFile(path));
        docs.put(path, doc);
        return doc;
    }

    public static ParsedDocFile fromString(String contents)
    {
        ParsedDocFile doc = new ParsedDocFile();

        String[] lines = contents.split("\n");
        boolean isText = false;
        String text = "";
        for (String line : lines)
        {
            if (!isText)
                line = line.trim();

            if (isText && line.startsWith("}--"))
            {
                doc.elements.add(new DocLabel(text));

                text = "";
                isText = false;
            } else if (isText)
            {
                if (text.length() > 0)
                    text += "\n";
                text += line;
            } else if (line.startsWith("TEXT --{"))
            {
                isText = true;
            } else if (line.startsWith("BUTTON"))
            {
                String[] parts = parseLine(line);
                doc.elements.add(new DocButton(parts[1], parts[2] + ".txt"));
            } else if (line.startsWith("SCRIPT_OBJECTS"))
            {
                String[] parts = parseLine(line);
                String[] objects = Scripts.getInfo(parts[1], TriggerType.valueOf(parts[2])).availableObjects;
                for (String obj : objects)
                    doc.elements.add(new DocButton(obj, "script/" + obj + ".txt"));
            } else if (line.startsWith("TITLE"))
            {
                doc.title = line.substring(line.indexOf(' ') + 1);
            } else if (line.startsWith("TYPE"))
            {
                doc.type = line.substring(line.indexOf(' ') + 1);
            } else if (line.startsWith("ELEM"))
            {
                String[] parts = parseLine(line);
                doc.listBoxElements.add(new NamedLink(parts[1], parts[2] + ".txt"));
            } else if (line.startsWith("SORTED"))
            {
                doc.sorted = true;
            }
        }

        return doc;
    }

    private static String[] parseLine(String line)
    {
        List<String> ret = Lists.newArrayList();
        line = line.trim();

        String current = "";
        boolean insideString = false;

        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if (c == '\\')
            {
                current += line.charAt(i + 1);
                i++;
            } else if (c == '"')
            {
                if (insideString)
                {
                    ret.add(current);
                    current = "";
                }

                insideString = !insideString;
            } else if (c == ' ' && !insideString)
            {
                if (current.length() > 0)
                {
                    ret.add(current);
                    current = "";
                }
            } else
            {
                current += c;
            }
        }
        if (current.length() > 0)
            ret.add(current);

        return ret.toArray(new String[ret.size()]);
    }

    public String title;
    private List<DocElement> elements = Lists.newArrayList();
    private List<NamedLink> listBoxElements = Lists.newArrayList();
    private String type = "default";
    private boolean sorted = false;

    private ParsedDocFile()
    {

    }

    public void add(WindowDocs window)
    {
        if (title != null && title.length() > 0)
        {
            window.title = title;
        }

        if (type.equals("default"))
        {
            ScrollContainer scroll = window.scrollContainer(1).fillWidth(7).top(7).bottom(window.getBackButton(), 5).add();
            ControlContainer content = scroll.content();

            for (DocElement element : elements)
            {
                element.add(window, content);

            }

            scroll.automaticTotalHeight();
        } else if (type.equals("list"))
        {
            ListBoxDescription<NamedLink> desc = new ListBoxDescription<NamedLink>(7, 7);
            desc.elements = listBoxElements;
            desc.canSelect = false;
            desc.rows = 12;
            desc.sorted = sorted;
            ListBox<NamedLink> listBox = window.listBox(desc).fillWidth(7).top(7).add();
        }
    }

    private interface DocElement
    {
        void add(WindowDocs window, ControlContainer content);
    }

    private static class DocLabel implements DocElement
    {
        private String text;

        private DocLabel(String text)
        {
            this.text = text;
        }

        @Override
        public void add(WindowDocs window, ControlContainer content)
        {
            content.row(window.label(Joiner.on('\n').join(window.mc.fontRenderer.listFormattedStringToWidth(text, window.getWidth() - 14 - 3 - ScrollContainer.SLIDER_WIDTH))));
        }
    }

    private static class DocButton implements DocElement
    {
        private String text;
        private String path;

        public DocButton(String text, String path)
        {
            this.text = text;
            this.path = path;
        }

        @Override
        public void add(WindowDocs window, ControlContainer content)
        {
            content.row(window.buttonDoc(text, path).size(100, 13));
        }
    }
}
