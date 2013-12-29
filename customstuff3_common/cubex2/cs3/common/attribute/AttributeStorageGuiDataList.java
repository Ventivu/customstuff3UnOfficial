package cubex2.cs3.common.attribute;

public class AttributeStorageGuiDataList
{
}/* extends AttributeStringListToArray<StorageGuiData>
{

    public AttributeStorageGuiDataList(AttributeCollection attributeCollection, String name, StorageGuiData[] defaultValue)
    {
        super(attributeCollection, name, defaultValue, "\\|");
    }

    @Override
    public StorageGuiData getValue(String s)
    {
        List<String> tokens = GeneralHelper.tokenizeStringList(s);
        int id = Integer.parseInt(tokens.get(0));
        int width = Integer.parseInt(tokens.get(1));
        int height = Integer.parseInt(tokens.get(2));
        int x = Integer.parseInt(tokens.get(3));
        int y = Integer.parseInt(tokens.get(4));
        return new StorageGuiData(id, width, height, x, y);
    }

    @Override
    public String getString(StorageGuiData value)
    {
        return value.id + "," + value.width + "," + value.height + "," + value.x + "," + value.y;
    }

}*/
