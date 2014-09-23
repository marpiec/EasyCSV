package pl.mpieciukiewicz.easycsv.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Not thread safe.
 */
public class ListListResultBuilder implements ResultBuilder<List<List<String>>> {

    private List<String> resultRow = new ArrayList<String>();
    private List<List<String>> resultsRows = new ArrayList<List<String>>();

    public void addRowValue(CharSequence value) {
        resultRow.add(value.toString());
    }

    public void rowFinished() {
        resultsRows.add(resultRow);
        resultRow = new ArrayList<String>();
    }

    @Override
    public List<List<String>> getResult() {
        return resultsRows;
    }
}
