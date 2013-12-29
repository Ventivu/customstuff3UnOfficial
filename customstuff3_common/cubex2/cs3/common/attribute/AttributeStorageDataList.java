package cubex2.cs3.common.attribute;

public class AttributeStorageDataList
{
}/* extends AttributeStringListToArray<StorageData>
{

    public AttributeStorageDataList(AttributeCollection attributeCollection, String name, StorageData[] defaultValue)
    {
        super(attributeCollection, name, defaultValue, "\\|");
    }

    @Override
    public StorageData getValue(String s)
    {
        List<String> tokens = GeneralHelper.tokenizeStringList(s);
        int id = Integer.parseInt(tokens.get(0));
        int size = Integer.parseInt(tokens.get(1));
        return new StorageData(id, size);
    }

    @Override
    public String getString(StorageData value)
    {
        return value.getId() + "," + value.size;
    }

}*/
