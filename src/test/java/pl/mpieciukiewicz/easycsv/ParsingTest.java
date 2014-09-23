package pl.mpieciukiewicz.easycsv;

import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.mpieciukiewicz.easycsv.TestUtils.entry;
import static pl.mpieciukiewicz.easycsv.TestUtils.map;

@Test
public class ParsingTest {

    private Map<String, String> referenceMap = map(entry("simpleHeader", "a"),
                                               entry("spaced header", "b"),
                                               entry("header \"with\" quotes", "c"),
                                               entry("header with, commas", "d"),
                                               entry("com'p\"\"lex, header\",,", "e"),
                                               entry("with;semi\";colons", "f"),
                                               entry(" with surrounding spaces ", "g"));

    public void testCommaDelimitedInputStreamParsing() {

        //Given
        InputStream csvStream = ParsingTest.class.getResourceAsStream("/complexExcelCsvCommas.csv");
        EasyCSV easyCSV = new EasyCSV();

        //then
        testAgainstReferenceData(csvStream, easyCSV);

    }

    public void testSemicolonDelimitedInputStreamParsing() {

        //Given
        InputStream csvStream = ParsingTest.class.getResourceAsStream("/complexExcelCsvSemicolons.csv");
        EasyCSV easyCSV = new EasyCSV(EasyCSVConfig.SEMICOLONS);

        //then
        testAgainstReferenceData(csvStream, easyCSV);

    }

    private void testAgainstReferenceData(InputStream csvStream, EasyCSV easyCSV) {

        //When
        Iterable<Map<String, String>> content = easyCSV.parseToMaps(csvStream);

        //Then
        assertThat(content).hasSize(1);
        Map<String, String> record = content.iterator().next();

        assertThat(record).isEqualTo(referenceMap);

    }



}
