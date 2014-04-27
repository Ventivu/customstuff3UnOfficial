package cubex2.cs3.util;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOHelper
{
    public static NBTTagCompound readNBTFromFile(File file)
    {
        NBTTagCompound nbt = null;

        try
        {
            nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return nbt;
    }

    public static void writeNBTToFile(NBTTagCompound nbt, File file)
    {
        try
        {
            CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to save data.");
        }
    }
}
