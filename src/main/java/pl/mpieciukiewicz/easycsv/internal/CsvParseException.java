package pl.mpieciukiewicz.easycsv.internal;

public class CsvParseException extends RuntimeException {
    public CsvParseException(Throwable cause) {
        super(cause);
    }

    public CsvParseException(String message) {
        super(message);
    }
}
