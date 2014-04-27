package cubex2.cs3.util;

import com.sun.javafx.beans.annotations.NonNull;

public interface IPurpuseStringProvider
{
    @NonNull
    String getStringForPurpose(StringProviderPurpose purpose);
}
