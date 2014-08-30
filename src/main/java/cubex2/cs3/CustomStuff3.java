package cubex2.cs3;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cubex2.cs3.asm.ICSMod;
import cubex2.cs3.basic.ContentPackLoader;
import cubex2.cs3.ingame.IngameContentPackLoader;
import cubex2.cs3.lib.Directories;
import cubex2.cs3.lib.ModInfo;
import cubex2.cs3.util.PostponeHandler;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
public class CustomStuff3
{
    @Instance(ModInfo.ID)
    public static CustomStuff3 instance;

    @SidedProxy(clientSide = "cubex2.cs3.ClientProxy", serverSide = "cubex2.cs3.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = FMLLog.getLogger();

        Directories.init(event.getModConfigurationDirectory().getParentFile());

        ContentPackLoader.instance().searchPacks();
        ContentPackLoader.instance().preparePacks();
        IngameContentPackLoader.instance().preparePacks();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerKeyBindings();

        ContentPackLoader.instance().initPacks();
        IngameContentPackLoader.instance().initPacks();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PostponeHandler.executePostponedTasks();
    }

    public static void loadPack(ICSMod pack)
    {
        if (pack.isIngamePack())
        {
            IngameContentPackLoader.instance().loadPack(pack);
        }
        else
        {
            ContentPackLoader.instance().loadPack(pack);
        }
    }
}
