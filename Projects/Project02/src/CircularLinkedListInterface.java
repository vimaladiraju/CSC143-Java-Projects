import java.util.Iterator;

/**
 * Requirements for CircularLinkedList class
 * @param <E>   type parameter for the list elements
 */
public interface CircularLinkedListInterface<E> {

    /**
     * Retrieves a count of elements being maintained by the list.
     *
     * @return the size of the list (count of elements)
     */
    public int size();

    /**
     * Empties the list, leaving no data
     */
    public void clear();

    /**
     * Retrieves the data at the specified position in the list
     *
     * @param position 0-based index for the list; must be in the range 0 to size - 1
     * @return the data in the specified position in the list
     */
    public E get(int position);

    /**
     * Adds a new node to the end of the list; by nature of the circular list, this element will point to the first element
     *
     * @param value the element to add to the list
     */
    public void add(E value);

    /**
     * Returns whether the specified element is contained in the list
     *
     * @param value     element to search for
     * @return          true, if found; false, if not
     */
    public boolean contains(E value);

    /**
     * Removes the first match (only) of the specified item from the list, if it exists there.
     *
     * @param value the element to remove from the list
     * @return true, if the element was found and removed; false, if not found or list is empty
     */
    public boolean remove(E value);

    /**
     * Removes the node at the specified position in the list
     * @param position position in the list; must be in range 0 to size - 1
     */
    public void remove(int position);

    /**
     * Creates and returns a shallow copy of this list (list and internal nodes are cloned but the same elements
     * will be referenced).
     * <br/><br/>
     * Technical details we wouldn't usually include in JavaDoc:
     * Object's clone method returns a copied object (cast needed to store it), but all the nodes are the same,
     * as are their data references.  See the API reference on the Object class's clone method
     * (<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone--">Object clone</a>),
     * paying attention to the need for a try/catch around the important super.clone() call, and to
     * an interface that this class must implement to avoid a runtime error when clone() is called.
     * <br/><br/>
     * There will be an unchecked cast that's unavoidable; you'll need to suppress it.
     * <br/><br/>
     * After you have the cloned list, you'll need to clear it and then add the data from the old list,
     * which will instantiate new nodes in the process (but they'll reference the same items as did the old list).
     * This is excellent Java knowledge to have in your toolbox!
     *
     * @return shallow copy of this list (containing the same elements in memory as this list)
     */
    public CircularLinkedList<E> clone();

    /**
     * Shuffles (randomizes) the list with O(n) efficiency.
     * <br/><br/>
     * Technical details we wouldn't usually include in JavaDoc:
     * Uses the Fisher-Yates technique, described here:
     * <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates Shuffle Wikipedia</a>
     * <br/><br/>
     * Data is first copied into an array.  The shuffling algorithm is then applied.
     * Data is then copied back into the list.  There will be an unchecked cast that's unavoidable;
     * you'll need to suppress it.
     */
    public void shuffle();

    /**
     * Retrieves an iterator over the list's elements.  Do not do other list operations like add or remove
     * from within an iterator loop; the results are not guaranteed to function as you might expect
     *
     * @return a strongly typed iterator over elements in the list
     */
    public Iterator<E> iterator();

}
