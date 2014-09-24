package pl.mpieciukiewicz.easycsv;

import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.mpieciukiewicz.easycsv.TestUtils.list;

@Test
public class ResultBuildingTest {

    class SampleRecord {
        String aaa;
        String bbb;
        String ccc;
        String ddd;

        SampleRecord(String aaa, String bbb, String ccc, String ddd) {
            this.aaa = aaa;
            this.bbb = bbb;
            this.ccc = ccc;
            this.ddd = ddd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SampleRecord that = (SampleRecord) o;

            if (aaa != null ? !aaa.equals(that.aaa) : that.aaa != null) return false;
            if (bbb != null ? !bbb.equals(that.bbb) : that.bbb != null) return false;
            if (ccc != null ? !ccc.equals(that.ccc) : that.ccc != null) return false;
            if (ddd != null ? !ddd.equals(that.ddd) : that.ddd != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = aaa != null ? aaa.hashCode() : 0;
            result = 31 * result + (bbb != null ? bbb.hashCode() : 0);
            result = 31 * result + (ccc != null ? ccc.hashCode() : 0);
            result = 31 * result + (ddd != null ? ddd.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "SampleRecord{" +
                    "aaa='" + aaa + '\'' +
                    ", bbb='" + bbb + '\'' +
                    ", ccc='" + ccc + '\'' +
                    ", ddd='" + ddd + '\'' +
                    '}';
        }
    }

    private List<List<String>> referenceResult = list(list("simpleHeader", "spaced header", "header \"with\" quotes", "header with, commas", "com'p\"\"lex, header\",,", "with;semi\";colons"," with surrounding spaces "),
                                                      list("a", "b", "c", "d", "e", "f", "g"));


    private List<SampleRecord> referenceObjectsResult = list(new SampleRecord("a", "b", "c", "d"), new SampleRecord("1", "2", "3", "4"));


    public void testBuildingListOfLists() {

        //Given
        InputStream csvStream = ParsingTest.class.getResourceAsStream("/complexExcelCsvCommas.csv");
        EasyCSV easyCSV = EasyCSV.DEFAULT.create();

        //When
        List<List<String>> content = easyCSV.parseToLists(new InputStreamReader(csvStream));

        //Then
        assertThat(content).isEqualTo(referenceResult);
    }

    public void testBuildingListOfObjects() {

        //Given
        InputStream csvStream = ParsingTest.class.getResourceAsStream("/simpleStringValues.csv");
        EasyCSV easyCSV = EasyCSV.DEFAULT.create();

        //When
        List<SampleRecord> content = easyCSV.parseToObjects(new InputStreamReader(csvStream), SampleRecord.class);

        //Then
        assertThat(content).isEqualTo(referenceObjectsResult);
    }
}
