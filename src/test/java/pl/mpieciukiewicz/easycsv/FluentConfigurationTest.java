package pl.mpieciukiewicz.easycsv;

import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.mpieciukiewicz.easycsv.Mapping.mapping;
import static pl.mpieciukiewicz.easycsv.TestUtils.entry;
import static pl.mpieciukiewicz.easycsv.TestUtils.map;

class MyRecord {
    int a;
    int b;
}

@Test
public class FluentConfigurationTest {

    public void testConfigurationA() {

        //given
        EasyCSV easyCSV = EasyCSVConfigurator.DEFAULT.
                withColumnsOfTypes(int.class, String.class, double.class).
                withLazyParsing().
                build();

        //when
        Configuration config = extractConfig(easyCSV);

        //then
        assertThat(config.lazyParsing).isTrue();
        assertThat(config.recordColumnTypesAsList).containsExactly(int.class, String.class, double.class);
        assertThat(config.recordColumnTypesAsMap).isNull();
        assertThat(config.recordsType).isNull();
    }

    public void testConfigurationB() {

        //given
        EasyCSV easyCSV = EasyCSVConfigurator.DEFAULT.
                withColumnsMappings(mapping("Column A", int.class), mapping("Column B", int.class), mapping("Column C", int.class)).
                withEagerParsing().
                build();

        //when
        Configuration config = extractConfig(easyCSV);

        //then
        assertThat(config.lazyParsing).isFalse();
        assertThat(config.recordColumnTypesAsList).isNull();
        assertThat(config.recordColumnTypesAsMap).isEqualTo(map(entry("Column A", int.class), entry("Column B", int.class), entry("Column C", int.class)));
        assertThat(config.recordsType).isNull();
    }

    public void testConfigurationC() {

        //given
        EasyCSV easyCSV = EasyCSVConfigurator.DEFAULT.
                withLazyParsing().
                withColumnsFromType(MyRecord.class).
                build();

        //when
        Configuration config = extractConfig(easyCSV);

        //then
        assertThat(config.lazyParsing).isTrue();
        assertThat(config.recordColumnTypesAsList).isNull();
        assertThat(config.recordColumnTypesAsMap).isNull();
        assertThat(config.recordsType).isEqualTo(MyRecord.class);

    }

    private Configuration extractConfig(EasyCSV easyCSV) {
        try {
            Field configField = EasyCSV.class.getDeclaredField("config");
            configField.setAccessible(true);
            return (Configuration) configField.get(easyCSV);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
