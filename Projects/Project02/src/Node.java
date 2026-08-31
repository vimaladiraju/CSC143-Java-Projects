/**
 * Internal node structure for the circular linked list.
 * Package-private access ensures it is hidden from external clients.
 *
 * @param <E> the type of data stored in the node
 */
class Node<E> {
    /**
     * Data held by this node.
     */
    E data;

    /**
     * Reference to the next node in this list.
     */
    Node<E> next;

    /**
     * Constructs a new node with the given data.
     * @param data the data to store in this node
     */
    Node(E data) {
        this.data = data;
        this.next = null;
    }
}
