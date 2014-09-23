package pl.mpieciukiewicz.easycsv;

import java.util.*;

public class TestUtils {

    public static <K, V> Map<K, V> map(Map.Entry<K, V>... entries) {
        HashMap<K, V> resultMap = new HashMap<K, V>(entries.length);
        for (Map.Entry<K, V> mapEntry : entries) {
            resultMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return resultMap;
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<K, V>(key, value);
    }

    public static <V> List<V> list(V... elements) {
        return new ArrayList<V>(Arrays.asList(elements));
    }
}
