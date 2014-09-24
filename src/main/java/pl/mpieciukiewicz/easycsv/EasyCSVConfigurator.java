package pl.mpieciukiewicz.easycsv;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EasyCSVConfigurator {

    static final EasyCSVConfigurator DEFAULT = new EasyCSVConfigurator(
            new Configuration(null, null, null, false, FormatConfiguration.DEFAULT),
            false, false, false);

    private final Configuration config;

    private final boolean recordTypeChanged;
    private final boolean lazyParsingChanged;
    private final boolean formatChanged;

    private EasyCSVConfigurator(Configuration config, boolean recordTypeChanged, boolean lazyParsingChanged, boolean formatChanged) {
        this.config = config;
        this.recordTypeChanged = recordTypeChanged;
        this.lazyParsingChanged = lazyParsingChanged;
        this.formatChanged = formatChanged;
    }

    public EasyCSVConfigurator withColumnsOfTypes(Class<?>... types) {
        if(recordTypeChanged) {
            throw new ConfigurationException("Columns types already defined!");
        } else {
            return new EasyCSVConfigurator(new Configuration(new ArrayList<Class<?>>(Arrays.asList(types)), null, null, config.lazyParsing, config.formatConfiguration),
                    true, lazyParsingChanged, formatChanged);
        }
    }

    public EasyCSVConfigurator withColumnsMappings(Mapping... mappings) {
        if(recordTypeChanged) {
            throw new ConfigurationException("Columns types already defined!");
        } else {
            Map<String, Class<?>> mappingsMap = new HashMap<String, Class<?>>(mappings.length);
            for (Mapping mapping : mappings) {
                mappingsMap.put(mapping.column, mapping.columnType);
            }
            return new EasyCSVConfigurator(new Configuration(null, mappingsMap, null, config.lazyParsing, config.formatConfiguration),
                    true, lazyParsingChanged, formatChanged);
        }
    }

    public EasyCSVConfigurator withColumnsFromType(Class<?> recordType) {
        if(recordTypeChanged) {
            throw new ConfigurationException("Columns types already defined!");
        } else {
            return new EasyCSVConfigurator(new Configuration(null, null, recordType, config.lazyParsing, config.formatConfiguration),
                    true, lazyParsingChanged, formatChanged);
        }
    }

    public EasyCSVConfigurator withLazyParsing() {
        if (lazyParsingChanged) {
            throw new ConfigurationException("Lazy/Eager parsing already defined!");
        } else {
            return new EasyCSVConfigurator(new Configuration(config.recordColumnTypesAsList, config.recordColumnTypesAsMap, config.recordsType,
                    true, config.formatConfiguration),
                    recordTypeChanged, true, formatChanged);
        }
    }

    public EasyCSVConfigurator withEagerParsing() {
        if (lazyParsingChanged) {
            throw new ConfigurationException("Lazy/Eager parsing already defined!");
        } else {
            return new EasyCSVConfigurator(new Configuration(config.recordColumnTypesAsList, config.recordColumnTypesAsMap, config.recordsType,
                    false, config.formatConfiguration),
                    recordTypeChanged, true, formatChanged);
        }
    }

    public EasyCSVConfigurator withFileFormat(FormatConfiguration formatConfig) {
        if(formatChanged) {
            throw new ConfigurationException("File format is already defined!");
        } else {
            return new EasyCSVConfigurator(new Configuration(config.recordColumnTypesAsList, config.recordColumnTypesAsMap, config.recordsType,
                    config.lazyParsing, formatConfig),
                    recordTypeChanged, lazyParsingChanged, true);
        }
    }

    public EasyCSV build() {
        return new EasyCSV(config);
    }

}
