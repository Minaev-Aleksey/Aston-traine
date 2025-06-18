package org.example.DZ1;

import java.util.LinkedList;
import java.util.Objects;

public class CustomHashMap<K, V> {

    private static final int COUNT_BUCKET = 16;
    private LinkedList<CustomEntry<K, V>>[] buckets;
    private int size = 0;
    private V valueForNUllKey;

    public CustomHashMap() {
        initialize();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        if (key == null) {
            return valueForNUllKey;
        }

        var bucketNUmber = key.hashCode() % COUNT_BUCKET;
        if (buckets[bucketNUmber].isEmpty()) {
            return null;
        }

        for (var curr : buckets[bucketNUmber]) {
            if (chekEquality(key, curr.getKey())) {
                return curr.getValue();
            }
        }
        return null;
    }

    public V put(K key, V value) {
        if (key == null) {
            valueForNUllKey = value;
            return value;
        }

        var bucketNUmber = key.hashCode() % COUNT_BUCKET;
        if (buckets[bucketNUmber].isEmpty()) {
            buckets[bucketNUmber].add(new CustomEntry<>(key, value));
            size++;
            return value;
        }

        for (var curr : buckets[bucketNUmber]) {
            if (chekEquality(key, curr.getKey())) {
                curr.setValue(value);
                return value;
            }
        }

        buckets[bucketNUmber].add(new CustomEntry<>(key, value));
        size++;

        return null;
    }

    public V remove(K key) {
        if(key == null){
            V result = valueForNUllKey;
            valueForNUllKey = null;
            size--;
            return result;
        }

        var bucketNUmber = key.hashCode() % COUNT_BUCKET;
        for(var curr: buckets[bucketNUmber]){
            if(chekEquality(key, curr.getKey())){
                V result = curr.getValue();
                buckets[bucketNUmber].remove(curr);
                size--;
                return result;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        for (var bucket : buckets) {
            for (var curr : bucket) {
                if (chekEquality(value, curr.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }


    public void clear() {
        initialize();
    }

    private void initialize() {
        buckets = new LinkedList[COUNT_BUCKET];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        valueForNUllKey = null;
        size = 0;
    }


    private boolean chekEquality(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var bucket : buckets) {
            for (var item : bucket) {
                sb.append(item.getKey() + "->" + item.getValue() + ", ");
            }
        }
        return "{" + sb.substring(0, sb.length() - 2) + "}";
    }
}

class CustomEntry<K, V> {
    private final K key;
    private V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public CustomEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEntry<?, ?> that = (CustomEntry<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
