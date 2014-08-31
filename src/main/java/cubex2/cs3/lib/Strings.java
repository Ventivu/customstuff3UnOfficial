package cubex2.cs3.lib;

public class Strings
{
    // NBT
    public static final String INT_DATA_PREFIX = "cs2intData_";
    public static final String STRING_DATA_PREFIX = "cs2stringData_";
    public static final String FLOAT_DATA_PREFIX = "cs2floatData_";

    public static final String FILE_MOD_CONFIGURATION = "config.ini";
    public static final String FILE_MOD = "mod.js";
    public static final String FILE_PACKMCMETA = "pack.mcmeta";

    public static final String FILE_INGAME_MOD = "mod.cs2";

    // Content registries
    public static final String REGISTRY_FUEL = "FuelRegistry";
    public static final String REGISTRY_SMELTING_RECIPE = "SmeltingRecipeRegistry";
    public static final String REGISTRY_ORE_DICT_ENTRY = "OreDictEntryRegistry";
    public static final String REGISTRY_SHAPED_RECIPE = "ShapedRecipeRegistry";
    public static final String REGISTRY_SHAPELESS_RECIPE = "ShapelessRecipeRegistry";
    public static final String REGISTRY_ITEM = "ItemRegistry";

    // GUI text
    public static final String INFO_NEW_PACK = "This has to be unique and|is used to reference items,|blocks and textures.";
    public static final String INFO_TEXURE_FILE = "The format is [modId]:[texture]|" +
            "[modId] is the mod's ID in all lowercase|" +
            "  this has to match the name of folder|" +
            "  located in the assets folder of the mod|" +
            "[texture] is name of the file without \".png\"||" +
            "Example: If the texture is located at|" +
            " \"assets/mymod/textures/items/myItem.png\"|" +
            "you have to enter|" +
            " \"mymod:myItem\"";
}
