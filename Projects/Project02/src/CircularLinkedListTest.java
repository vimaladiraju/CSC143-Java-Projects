import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the CircularLinkedList class.
 */
public class CircularLinkedListTest {
    private CircularLinkedList<String> list;

    @BeforeEach
    public void setUp() {
        list = new CircularLinkedList<>();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, list.size());
        assertFalse(list.contains("test"));
    }

    @Test
    public void testVarargsConstructor() {
        CircularLinkedList<String> minionList = new CircularLinkedList<>("Stuart", "Kevin", "Bob");
        assertEquals(3, minionList.size());
        assertEquals("Stuart", minionList.get(0));
        assertEquals("Kevin", minionList.get(1));
        assertEquals("Bob", minionList.get(2));

    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add("A");
        assertEquals(1, list.size());
    }

    @Test
    public void testClear() {
        list.add("A");
        list.add("B");
        list.clear();
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    public void testGet() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    public void testAdd() {
        list.add("First");
        assertEquals(1, list.size());
        assertEquals("First", list.get(0));
    }

    @Test
    public void testRemoveByValue() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(list.remove("B"));
        assertEquals(2, list.size());
        assertFalse(list.contains("B"));
    }

    @Test
    public void testRemoveByPosition() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.remove(0);
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
    }


    @Test
    public void testContains() {
        assertFalse(list.contains("A"));

        list.add("A");
        list.add(null);

        assertTrue(list.contains("A"));
        assertTrue(list.contains(null));
        assertFalse(list.contains("B"));
    }

    @Test
    public void testClone() {
        list.add("A");
        list.add("B");

        CircularLinkedList<String> listClone = list.clone();
        assertEquals(list.size(), listClone.size());
        assertEquals(list.get(0), listClone.get(0));
        assertEquals(list.get(1), listClone.get(1));
        listClone.add("C");
        assertNotEquals(list.size(), listClone.size());
    }

    @Test
    public void testShuffle() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        list.shuffle();
        assertEquals(4, list.size());
        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));
        assertTrue(list.contains("D"));
    }

    @Test
    public void testIterator() {
        list.add("1st");
        list.add("2nd");
        Iterator<String> iter = list.iterator();
        assertTrue(iter.hasNext());
        assertEquals("1st", iter.next());
        assertEquals("2nd", iter.next());
        assertEquals("1st", iter.next());

        CircularLinkedList<String> emptyList = new CircularLinkedList<>();
        Iterator<String> iter2 = emptyList.iterator();
        assertFalse(iter2.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter2.next());

    }


}
