import java.util.*;

/**
 * Generic circular singly-linked list implementation.
 * @param <E> element type stored in this list
 */
public class CircularLinkedList<E> implements CircularLinkedListInterface<E>, Cloneable {
    /**
     * Node associated with the first element in the list.
     */
    private Node<E> front;
    /**
     * Node associated with the last element in the list.
     */
    private Node<E> end;
    /**
     * Integer representing the total amount of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty CircularLinkedList.
     */
    public CircularLinkedList() {
        this.front = null;
        this.end = null;
        this.size = 0;
    }

    /**
     * Constructs a CircularLinkedList initialized with the provided elements.
     * @param elements elements to populate the CircularLinkedList with.
     */
    @SafeVarargs
    public CircularLinkedList(E... elements) {
        this();
        if (elements != null) {
            for (E element : elements) {
                add(element);
            }
        }
    }

    /**
     * Method to add an element to the end of the CircularLinkedList.
     * @param value the element to add to the CircularLinkedList
     */
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        if (size == 0) {
            front = newNode;
            end = newNode;
            end.next = front;
        } else {
            end.next = newNode;
            end = newNode;
            end.next = front;
        }
        size++;
    }

    /**
     * Method to return the size of the CircularLinkedList.
     * @return the size of the CircularLinkedList
     */
    public int size() {
        return size;
    }

    /**
     * Method to clear the CircularLinkedList.
     */
    public void clear() {
        front = null;
        end = null;
        size = 0;
    }

    /**
     * Method to retrieve data from the CircularLinkedList given an index.
     * @param position 0-based index for the list; must be in the range 0 to size - 1
     * @return the data associated with the given position
     * @throws IndexOutOfBoundsException if position is out of range
     */
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }
        Node<E> curr = front;
        for (int i = 0; i < position; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    /**
     * Method to check if the CircularLinkedList contains the provided value.
     * @param value     element to search for
     * @return a boolean that tells if the element was found or not
     */
    public boolean contains(E value) {
        if (size == 0) return false;
        Node<E> curr = front;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, curr.data)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Removes the given element from the CircularLinkedList.
     * @param value the element to remove from the list
     * @return true if the element was removed, false if the element was not found
     */
    public boolean remove (E value) {
        if (size == 0) return false;
        Node<E> curr = front;
        Node<E> prev = end;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(value, curr.data)) {
                if (size == 1) {
                    front = null;
                    end = null;
                } else {
                    prev.next = curr.next;
                    if (curr == front) {
                        front = curr.next;
                    }
                    if (curr == end) {
                        end = prev;
                    }
                }
                size --;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    /**
     * Removes the element associated with the given position.
     * @param position position in the list; must be in range 0 to size - 1
     * @throws IndexOutOfBoundsException if position is out of range
     */
    public void remove (int position) {
        if (size == 0 || position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }
        if (size == 1) {
            front = null;
            end = null;
        } else {
            Node<E> prev = end;
            Node<E> curr = front;
            for (int i = 0; i < position; i++) {
                prev = curr;
                curr = curr.next;
            }
            prev.next = curr.next;
            if (curr == front) {
                front = curr.next;
            }
            if (curr == end) {
                end = prev;
            }
        }
        size--;
    }

    /**
     * Method to create a copy of the CircularLinkedList.
     * @return an exact copy of the CircularLinkedList
     */
    @SuppressWarnings("unchecked")
    public CircularLinkedList<E> clone() {
        try {
            CircularLinkedList<E> copy = (CircularLinkedList<E>) super.clone();
            copy.clear();
            Node<E> curr = this.front;
            for (int i = 0; i < this.size; i++) {
                copy.add(curr.data);
                curr = curr.next;
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to randomize the order in the CircularLinkedList.
     */
    @SuppressWarnings("unchecked")
    public void shuffle() {
        if (size <= 1) return;
        Object[] array = new Object[size];
        Node<E> curr = front;
        for (int i = 0; i < size; i++) {
            array[i] = curr.data;
            curr = curr.next;
        }

        Random rand = new Random();
        for (int i = size - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Object temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        clear();
        for (Object item : array) {
            add((E) item);
        }
    }

    /**
     * Creates a Circular Iterator to iterate over the elements of the CircularLinkedList indefinitely.
     * @return the newly-created iterator
     */
    public Iterator<E> iterator() {
        return new CircularLinkedListIterator<>(front);
    }

}
