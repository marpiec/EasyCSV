package pl.mpieciukiewicz.easycsv;

import java.nio.charset.Charset;

public class FormatConfiguration {

    public static final FormatConfiguration DEFAULT = new FormatConfiguration(',', '"', System.getProperty("line.separator"), Charset.defaultCharset());
    public static final FormatConfiguration COMMAS = DEFAULT;
    public static final FormatConfiguration SEMICOLONS = new FormatConfiguration(';', '"', System.getProperty("line.separator"), Charset.defaultCharset());

    public final char valueSeparator;
    public final char quote;
    public final String newLine;
    public final Charset encoding;

    private FormatConfiguration(char valueSeparator, char quote, String newLine, Charset encoding) {
        this.valueSeparator = valueSeparator;
        this.quote = quote;
        this.newLine = newLine;
        this.encoding = encoding;
    }

    public static FormatConfiguration of(char valueSeparator, char quote, String newLine, Charset encoding) {
        return new FormatConfiguration(valueSeparator, quote, newLine, encoding);
    }
}
