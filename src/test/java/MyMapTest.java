import org.example.MyMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MyMapTest {

    private MyMap<String, String> map;

    @BeforeEach
    public void setUp() {
        map = new MyMap<>();
    }

    @Test
    public void testSize_EmptyMap() {
        assertEquals(0, map.size());
    }

    @Test
    public void testIsEmpty_EmptyMap() {
        assertTrue(map.isEmpty());
    }

    @Test
    public void testIsEmpty_NonEmptyMap() {
        map.put("key1", "value1");
        assertFalse(map.isEmpty());
    }

    @Test
    public void testContainsKey_ExistingKey() {
        map.put("key1", "value1");
        assertTrue(map.containsKey("key1"));
    }

    @Test
    public void testContainsKey_NonExistingKey() {
        map.put("key1", "value1");
        assertFalse(map.containsKey("key2"));
    }

    @Test
    public void testContainsValue_ExistingValue() {
        map.put("key1", "value1");
        assertTrue(map.containsValue("value1"));
    }

    @Test
    public void testContainsValue_NonExistingValue() {
        map.put("key1", "value1");
        assertFalse(map.containsValue("value2"));
    }

    @Test
    public void testGet_ExistingKey() {
        map.put("key1", "value1");
        assertEquals("value1", map.get("key1"));
    }

    @Test
    public void testGet_NonExistingKey() {
        map.put("key1", "value1");
        assertNull(map.get("key2"));
    }

    @Test
    public void testPut_NewKey() {
        map.put("key1", "value1");
        assertEquals(1, map.size());
        assertEquals("value1", map.get("key1"));
    }

    @Test
    public void testPut_ExistingKey() {
        map.put("key1", "value1");
        map.put("key1", "value2");
        assertEquals(1, map.size());
        assertEquals("value2", map.get("key1"));
    }

    @Test
    public void testRemove_ExistingKey() {
        map.put("key1", "value1");
        String removedValue = map.remove("key1");
        assertEquals("value1", removedValue);
        assertNull(map.get("key1"));
        assertEquals(0, map.size());
    }

    @Test
    public void testRemove_NonExistingKey() {
        map.put("key1", "value1");
        String removedValue = map.remove("key2");
        assertNull(removedValue);
        assertEquals(1, map.size());
    }

    @Test
    public void testClear() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.clear();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    public void testKeySet() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        Set<String> keySet = map.keySet();
        assertTrue(keySet.contains("key1"));
        assertTrue(keySet.contains("key2"));
    }

    @Test
    public void testValues() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        Collection<String> values = map.values();
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    public void testEntrySet() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(2, entrySet.size());
        boolean hasEntry = entrySet.stream().anyMatch(entry -> entry.getKey().equals("key1") && entry.getValue().equals("value1"));
        assertTrue(hasEntry);
    }

    @Test
    public void testPutAll() {
        Map<String, String> anotherMap = new HashMap<>();
        anotherMap.put("key1", "value1");
        anotherMap.put("key2", "value2");

        map.putAll(anotherMap);

        assertEquals(2, map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }

    @Test
    public void testPutNullKey() {
        map.put(null, "value");
        assertEquals("value", map.get(null));
    }

    @Test
    public void testPutNullValue() {
        map.put("key1", null);
        assertNull(map.get("key1"));
    }

    @Test
    public void testPutNullKeyAndValue() {
        map.put(null, null);
        assertNull(map.get(null));
    }
}