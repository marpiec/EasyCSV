package pl.mpieciukiewicz.easycsv;

import java.util.List;
import java.util.Map;

public class Configuration {

    final public List<Class<?>> recordColumnTypesAsList;
    final public Map<String, Class<?>> recordColumnTypesAsMap;
    final public Class<?> recordsType;

    final public boolean lazyParsing;

    final public FormatConfiguration formatConfiguration;

    public Configuration(List<Class<?>> recordColumnTypesAsList, Map<String, Class<?>> recordColumnTypesAsMap,
                         Class<?> recordsType, boolean lazyParsing,
                         FormatConfiguration formatConfiguration) {
        this.recordColumnTypesAsList = recordColumnTypesAsList;
        this.recordColumnTypesAsMap = recordColumnTypesAsMap;
        this.recordsType = recordsType;
        this.lazyParsing = lazyParsing;
        this.formatConfiguration = formatConfiguration;
    }
}
