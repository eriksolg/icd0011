package storage;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private Map<String, Object> map = new HashMap<>();

    public void store(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }
}
