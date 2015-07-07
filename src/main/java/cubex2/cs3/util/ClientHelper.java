package cubex2.cs3.util;

import cubex2.cs3.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ClientHelper
{
    public static void refreshResources(Minecraft mc)
    {
        mc.getSoundHandler().pauseSounds();
        mc.refreshResources();
        mc.getSoundHandler().resumeSounds();
    }

    public static String loadDocFile(String path)
    {
        try
        {
            InputStream stream = ClientProxy.resPack.getInputStream(new ResourceLocation("cs3", "docs/" + path));
            String res = IOUtils.toString(stream, Charsets.UTF_8).replace("\r", "");
            stream.close();
            return res;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return "[FILE NOT FOUND: " + path + " ]";
    }
}
