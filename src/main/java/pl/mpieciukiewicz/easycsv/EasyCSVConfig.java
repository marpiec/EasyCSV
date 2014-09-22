package pl.mpieciukiewicz.easycsv;

import java.nio.charset.Charset;

public class EasyCSVConfig {

    public static final EasyCSVConfig DEFAULT = new EasyCSVConfig(',', '"', System.getProperty("line.separator"), Charset.defaultCharset());
    public static final EasyCSVConfig COMMAS = DEFAULT;
    public static final EasyCSVConfig SEMICOLONS = new EasyCSVConfig(';', '"', System.getProperty("line.separator"), Charset.defaultCharset());

    public final char valueSeparator;
    public final char quote;
    public final String newLine;
    public final Charset encoding;

    private EasyCSVConfig(char valueSeparator, char quote, String newLine, Charset encoding) {
        this.valueSeparator = valueSeparator;
        this.quote = quote;
        this.newLine = newLine;
        this.encoding = encoding;
    }

    public static EasyCSVConfig of(char valueSeparator, char quote, String newLine, Charset encoding) {
        return new EasyCSVConfig(valueSeparator, quote, newLine, encoding);
    }
}
