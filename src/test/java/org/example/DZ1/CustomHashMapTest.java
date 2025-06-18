package org.example.DZ1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {

    @Test
    public void size() {
        var map = new CustomHashMap<>();

        assertEquals(map.size(), 0);
        map.put(2,5);
        assertEquals(map.size(), 1);
        map.put(7,45);
        assertEquals(map.size(), 2);
        map.put(7,46);
        assertEquals(map.size(), 2);

    }

    @Test
    public void isEmpty() {
        var map = new CustomHashMap<>();
        assertTrue(map.isEmpty());
        map.put(5,55);
        assertFalse(map.isEmpty());
    }

    @Test
    public void getNPut() {
        var map = new CustomHashMap<>();
        assertEquals(map.put(2,5), 5);
        assertEquals(map.put(7,45), 45);

        assertEquals(map.get(2), 5);
        assertEquals(map.get(7), 45);

        assertEquals(map.get(156), null);
    }

    private class MyObject{
        private int value;

        public MyObject(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyObject myObject = (MyObject) o;
            return value == myObject.value;
        }

        @Override
        public int hashCode() {
            return 34;
        }
    }

    @Test
    public void getNPutWhenHashCollided() {
        var map = new CustomHashMap<MyObject, Integer>();

        var key1 = new MyObject(12);
        assertEquals(map.put(key1, 5), 5);

        assertEquals(map.get(new MyObject(156)), null);
    }

    @Test
    public void getNPutForNullKey(){
        var map = new CustomHashMap<>();
        assertEquals(map.put(2,5), 5);
        assertEquals(map.get(2), 5);
    }

    @Test
    public void containsKey() {
        var map = new CustomHashMap<>();
        assertFalse(map.containsKey(2));
        map.put(2,5);
        map.put(7,77);
        assertTrue(map.containsKey(2));
        assertFalse(map.containsKey(5));
        assertTrue(map.containsKey(7));
    }

    @Test
    public void containsValue() {
        var map = new CustomHashMap<>();
        assertFalse(map.containsValue(5));
        map.put(2,5);
        map.put(7,77);
        assertTrue(map.containsValue(5));
        assertFalse(map.containsValue(2));
        assertTrue(map.containsValue(77));
    }

    @Test
    public void remove() {
        var map = new CustomHashMap<>();
        map.put(2,5);
        map.put(7,45);
        map.put(null, 55);

        assertEquals(map.get(2), 5);
        assertEquals(map.get(7), 45);
        assertEquals(map.get(null), 55);

        assertEquals(map.remove(7), 45);
    }

    @Test
    public void clear() {
        var map = new CustomHashMap<>();
        map.put(2,5);
        map.put(7,45);
        map.put(null, 55);

        assertEquals(map.get(2), 5);
        assertEquals(map.get(7), 45);
        assertEquals(map.get(null), 55);

        map.clear();

        assertEquals(map.get(2), null);
        assertEquals(map.get(7), null);
        assertEquals(map.get(null), null);

        assertFalse(map.containsKey(2));
        assertFalse(map.containsKey(7));
        assertFalse(map.containsKey(null));

        assertFalse(map.containsKey(5));
        assertFalse(map.containsKey(42));
        assertFalse(map.containsKey(55));
    }

    @Test
    public void testToString() {
        var map = new CustomHashMap<>();
        map.put(2,5);
        map.put(7,45);

        assertEquals(map.toString(), "{2->5, 7->45}");
    }
}