package pl.mpieciukiewicz.easycsv;

public class Mapping {
    public final String column;
    public final Class<?> columnType;

    private Mapping(String column, Class<?> columnType) {
        this.column = column;
        this.columnType = columnType;
    }

    public static Mapping mapping(String column, Class<?> columnType) {
        return new Mapping(column, columnType);
    }
}
