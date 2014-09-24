package pl.mpieciukiewicz.easycsv;

import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.mpieciukiewicz.easycsv.TestUtils.list;

@Test
public class ResultBuildingTest {

    private List<List<String>> referenceResult = list(list("simpleHeader", "spaced header", "header \"with\" quotes", "header with, commas", "com'p\"\"lex, header\",,", "with;semi\";colons"," with surrounding spaces "),
                                                      list("a", "b", "c", "d", "e", "f", "g"));

    public void testBuildingListOfLists() {

        //Given
        InputStream csvStream = ParsingTest.class.getResourceAsStream("/complexExcelCsvCommas.csv");
        EasyCSV easyCSV = EasyCSVConfigurator.DEFAULT.build();

        //When
        List<List<String>> content = easyCSV.parseToLists(new InputStreamReader(csvStream));

        //Then
        assertThat(content).isEqualTo(referenceResult);
    }
}
