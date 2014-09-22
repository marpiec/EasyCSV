package pl.mpieciukiewicz.easycsv.internal;

import pl.mpieciukiewicz.easycsv.EasyCSVConfig;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser {

    private final EasyCSVConfig config;
    private final CharUtils charUtils;

    public CSVParser(EasyCSVConfig config) {
        this.config = config;
        this.charUtils = new CharUtils(config);
    }

    public Iterable<Map<String, String>> parseToMaps(Reader reader) {
        try {
            List<String> header = toListOfStrings(readRow(reader));
            int columnsCount = header.size();


            ArrayList<Map<String, String>> rows = new ArrayList<Map<String, String>>();

            Map<String, String> resultRow = new HashMap<String, String>();

            List<CharSequence> row;
            while (!(row = readRow(reader)).isEmpty()) {
                for (int i = 0; i < columnsCount; i++) {
                    resultRow.put(header.get(i), row.get(i).toString());
                }
                rows.add(resultRow);
            }

            return rows;
        } catch (IOException e) {
            throw new CsvParseException(e);
        }
    }

    private List<String> toListOfStrings(List<CharSequence> charSequences) {
        ArrayList<String> strings = new ArrayList<String>(charSequences.size());
        for (CharSequence charSequence : charSequences) {
            strings.add(charSequence.toString());
        }
        return strings;
    }

    private List<CharSequence> readRow(Reader reader) throws IOException {

        List<CharSequence> row = new ArrayList<CharSequence>();

        CharSequence value;
        while ((value = parseValue(reader)) != null) {
            row.add(value);
        }

        return row;

    }

    public CharSequence parseValue(Reader reader) throws IOException {

        char character = (char) reader.read();
        if (charUtils.isEndOfLine(character)) {
            return null;
        } else if (charUtils.isQuote(character)) {
            return parseQuotedValue(reader);
        } else {
            return parseSimpleValue(character, reader);
        }

    }

    public CharSequence parseQuotedValue(Reader reader) throws IOException {
        StringBuilder value = new StringBuilder();

        char character;

        do {
            character = (char) reader.read();
            if (charUtils.isQuote(character)) {
                character = (char) reader.read();
                if (charUtils.isEndOfValue(character)) {
                    return value;
                } else if (charUtils.isQuote(character)) {
                    value.append(config.quote);
                } else {
                    throw new CsvParseException("Expected [value separator], [end on line] or [end of file], but found [" + character + "]");
                }
            } else {
                value.append(character);
            }
        } while (character != -1);

        throw new CsvParseException("Missing closing quote");
    }


    private CharSequence parseSimpleValue(char firstCharacter, Reader reader) throws IOException {
        StringBuilder value = new StringBuilder().append(firstCharacter);

        char character;
        while (charUtils.notEndOfValue(character = (char) reader.read())) {
            value.append(character);
        }

        return value;
    }
}
