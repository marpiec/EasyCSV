package pl.mpieciukiewicz.easycsv;

import pl.mpieciukiewicz.easycsv.internal.CSVParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

public class EasyCSV {

    private final EasyCSVConfig config;

    public EasyCSV() {
        config = EasyCSVConfig.DEFAULT;
    }

    public EasyCSV(EasyCSVConfig config) {
        this.config = config;
    }

    public Iterable<Map<String, String>> parseToMaps(InputStream inputStream) {
        return parseToMaps(new InputStreamReader(inputStream));
    }

    public Iterable<Map<String, String>> parseToMaps(String string) {
        return parseToMaps(new StringReader(string));
    }

    public Iterable<Map<String, String>> parseToMaps(Reader reader) {
        return new CSVParser(config).parseToMaps(reader);
    }
}
