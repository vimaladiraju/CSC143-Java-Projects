import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TreeMapTest {
    private TreeMap<Integer, String> map;

    @BeforeEach
    public void setUp() {
        map = new TreeMap<>();
    }

    @Test
    public void testNewMapIsEmpty() {
        assertEquals(0, map.size());
        assertFalse(map.containsKey(12));
        assertNull(map.get(12));
    }

    @Test
    public void testPutandGet() {
        map.put(10, "Ten");
        map.put(5, "Five");
        map.put(12, "Twelve");

        assertEquals(3, map.size());
        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(5));
        assertTrue(map.containsKey(12));
        assertEquals("Ten", map.get(10));
        assertEquals("Five", map.get(5));
        assertEquals("Twelve", map.get(12));
        assertNull(map.get(99));
    }

    @Test
    public void testNullKeyHandling() {
        assertThrows(IllegalArgumentException.class, () -> map.put(null, "Value"));
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(null));
        assertThrows(IllegalArgumentException.class, () -> map.toKeyArray(null));
        assertThrows(IllegalArgumentException.class, () -> map.toValueArray(null));

    }

    @Test
    public void testClear() {
        map.put(1, "One");
        map.put(2, "Two");
        assertEquals(2, map.size());

        map.clear();
        assertEquals(0, map.size());
        assertFalse(map.containsKey(1));
        assertNull(map.get(1));
    }

    @Test
    public void testToKeyArrayExactAndLargerAndSmaller() {
        map.put(20, "Twenty");
        map.put(10, "Ten");
        map.put(30, "Thirty");

        Integer[] exact = map.toKeyArray(new Integer[3]);
        assertArrayEquals(new Integer[]{10, 20, 30}, exact);

        Integer[] larger = map.toKeyArray(new Integer[5]);
        assertEquals(5, larger.length);
        assertEquals(10, larger[0]);
        assertEquals(20, larger[1]);
        assertEquals(30, larger[2]);
        assertNull(larger[3]);

        Integer[] smaller = map.toKeyArray(new Integer[0]);
        assertEquals(3, smaller.length);
        assertArrayEquals(new Integer[]{10, 20, 30}, smaller);
    }

    @Test
    public void testToValueArrayInKeyOrder() {
        map.put(20, "Twenty");
        map.put(10, "Ten");
        map.put(30, "Thirty");

        String[] values = map.toValueArray(new String[0]);
        assertArrayEquals(new String[]{"Ten, Twenty, Thirty"}, values);
    }

    @Test
    public void testDuplicates() {
        map.put(10, "First Ten");
        map.put(10, "Second Ten");

        assertEquals(2, map.size());
        Integer[] keys = map.toKeyArray(new Integer[0]);
        assertEquals(new Integer[]{10, 10}, keys);
    }

}
