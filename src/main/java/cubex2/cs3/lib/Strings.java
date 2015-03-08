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
    public static final String REGISTRY_BLOCK = "BlockRegistry";
    public static final String REGISTRY_WORLD_GEN = "WorldGenRegistry";

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
    public static final String INFO_TOOL_CLASS_BLOCK = "The tool class that is effective|" +
            " on this block. Notice that if|" +
            " the material is set to rock|" +
            " or iron, pickaxes are effective|" +
            " even if not specified here.|" +
            "The most common tool classes are:|" +
            " pickaxe|" +
            " axe|" +
            " shovel";
    public static final String INFO_HARVEST_LEVEL_BLOCK = "The harvest level that is|" +
            "required for the tool to|" +
            "be effective on this block.|" +
            " 0 = wood / gold|" +
            " 1 = stone|" +
            " 2 = iron|" +
            " 3 = diamond";
    public static final String INFO_TOOL_CLASS_ITEM = "The tool class specifies on what|" +
            " blocks the item is efficient.|" +
            "You can use multiple|" +
            " tool classes by using ',' as|" +
            " a separator.|" +
            "Common tool classes are:|" +
            " pickaxe|" +
            " axe|" +
            " shovel|" +
            "Special classes are:|" +
            " noHarvest - can't break any blocks|" +
            " all - effective on everything";
    public static final String INFO_HARVEST_LEVEL_ITEM = "The harvest level of the|" +
            "specified tool class.|" +
            " 0 = wood / gold|" +
            " 1 = stone|" +
            " 2 = iron|" +
            " 3 = diamond";
}
