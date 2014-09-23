package pl.mpieciukiewicz.easycsv.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Not thread safe.
 */
public class MapListResultBuilder implements ResultBuilder<List<Map<String, String>>> {

    private int column = 0;
    private boolean readingHeader = true;
    private List<String> header = new ArrayList<String>();

    private Map<String, String> resultRow = new HashMap<String, String>();
    private List<Map<String, String>> resultsRows = new ArrayList<Map<String, String>>();

    public void addRowValue(CharSequence value) {
        if(readingHeader) {
            header.add(value.toString());
        } else {
            resultRow.put(header.get(column), value.toString());
            column++;
        }
    }

    public void rowFinished() {
        if(!readingHeader) {
            resultsRows.add(resultRow);
        }
        readingHeader = false;
        resultRow = new HashMap<String, String>();
        column = 0;
    }

    @Override
    public List<Map<String, String>> getResult() {
        return resultsRows;
    }
}
