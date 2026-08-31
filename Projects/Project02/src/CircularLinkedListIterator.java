import java.util.*;

/**
 * Circular Iterator over elements of the list, never signals "done" for a non-empty list.
 * @param <E> the element stored in each position of the list
 */
class CircularLinkedListIterator<E> implements Iterator<E> {
    /**
     * The current node being traversed.
     */
    private Node<E> current;

    /**
     * A constructor for the CircularLinkedListIterator class.
     * @param front the starting node for traversal
     */
    public CircularLinkedListIterator(Node<E> front) {
        this.current = front;
    }

    /**
     * Checks if there is an element after the current one to traverse.
     * @return a boolean that describes the above
     */
    public boolean hasNext() {
        return current != null;
    }

    /**
     * Goes to the next element to process.
     * @return the current data
     * @throws NoSuchElementException if list is empty
     */
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException("List is empty");
        }
        E data = current.data;
        current = current.next;
        return data;
    }
}
