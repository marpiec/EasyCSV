package pl.mpieciukiewicz.easycsv.internal;

import pl.mpieciukiewicz.easycsv.EasyCSVConfig;

public class CharUtils {

    private static final char endOfContent = (char) -1;

    private final char valueSeparator;

    public CharUtils(EasyCSVConfig config) {
        this.valueSeparator = config.valueSeparator;
    }

    public boolean isEndOfLine(char character) {
        return character == '\n' || character == '\r' || character == endOfContent;
    }

    public boolean isQuote(char character) {
        return character == '"';
    }

    public boolean isValueSeparator(char character) {
        return character == valueSeparator;
    }

    public boolean isEndOfValue(char character) {
        return character == valueSeparator || character == '\n' || character == '\r' || character == endOfContent;
    }

    public boolean notEndOfValue(char character) {
        return character != valueSeparator && character != '\n' && character != '\r' && character != endOfContent;
    }

    public boolean notEndOfQuotedValue(char previousCharacter, char character) {
        return character != valueSeparator && character != '\n' && character != '\r' && character != endOfContent;
    }
}
