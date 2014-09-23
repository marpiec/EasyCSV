package pl.mpieciukiewicz.easycsv.internal;

import pl.mpieciukiewicz.easycsv.EasyCSVConfig;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser<T> {

    private final EasyCSVConfig config;
    private final CharUtils charUtils;
    private final ResultBuilder<T> resultBuilder;

    public CSVParser(EasyCSVConfig config, ResultBuilder<T> resultBuilder) {
        this.config = config;
        this.charUtils = new CharUtils(config);
        this.resultBuilder = resultBuilder;
    }

    public T parse(Reader reader) {
        try {
            while (readRow(reader)) {/*do nothing */}
            return resultBuilder.getResult();
        } catch (IOException e) {
            throw new CsvParseException(e);
        }
    }


    private boolean readRow(Reader reader) throws IOException {
        boolean nonEmptyRow = false;

        CharSequence value;
        while ((value = parseValue(reader)) != null) {
            nonEmptyRow = true;
            resultBuilder.addRowValue(value);
        }

        if(nonEmptyRow) {
            resultBuilder.rowFinished();
        }
        return nonEmptyRow;
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
