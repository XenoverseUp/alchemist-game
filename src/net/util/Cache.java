package net.util;

import java.util.HashMap;
import java.util.function.Supplier;

public class Cache {
    private HashMap<String, String> cachedValues = new HashMap<>();
    private HashMap<String, Supplier<String>> validators = new HashMap<>();

    public void create(String key, Supplier<String> supplier) {
        validators.put(key, supplier);
        set(key, null);
    }
    public String get(String key) {
        if (!validators.containsKey(key)) return null;
        if (cachedValues.get(key) == null) revalidate(key);

        return cachedValues.get(key);
    }

    public void revalidate(String key) {
        String value = validators.get(key).get();
        if (value != null) set(key, value);
    }

    public void revalidateAll() {
        cachedValues.keySet().forEach((key) -> revalidate(key));
    }

    public void reset(String key) {
        set(key, null);
    }

    private void set(String key, String value) {
        cachedValues.put(key, value);
    }
}
