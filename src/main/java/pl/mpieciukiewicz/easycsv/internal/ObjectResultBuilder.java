package pl.mpieciukiewicz.easycsv.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectResultBuilder<T> implements ResultBuilder<List<T>> {

    private final ObjectConstructionUtil objectConstructionUtil = new ObjectConstructionUtil();

    private final Class<T> clazz;


    private int column = 0;
    private boolean readingHeader = true;
    private List<String> header = new ArrayList<String>();

    private T resultRow = null;
    private List<T> resultsRows = new ArrayList<T>();


    public ObjectResultBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void addRowValue(CharSequence value) {
        if(readingHeader) {
            header.add(value.toString());
        } else {
            try {
                if(resultRow==null) {
                    resultRow = objectConstructionUtil.createInstance(clazz);
                }
                Field field = clazz.getDeclaredField(header.get(column));
                field.setAccessible(true);
                field.set(resultRow, value.toString());
                field.setAccessible(false);
                column++;
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }

        }
    }

    public void rowFinished() {
        if(!readingHeader) {
            resultsRows.add(resultRow);
        }
        readingHeader = false;
        resultRow = null;
        column = 0;
    }

    @Override
    public List<T> getResult() {
        return resultsRows;
    }

}
