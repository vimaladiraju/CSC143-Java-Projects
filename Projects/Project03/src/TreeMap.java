import java.util.Arrays;

public class TreeMap<K extends Comparable<K>, V> implements TreeMapInterface<K, V> {
    private TreeMapNode<K, V> overallRoot;
    private int size;

    /**
     * Constructs an empty TreeMap.
     */
    public TreeMap() {
        this.overallRoot = null;
        this.size = 0;
    }

    /**
     * Retrieves the number of key/value pair elements managed by the map.
     * @return the number of elements in the map
     */
    public int size() {
        return this.size;
    }

    /**
     * Clears the existing tree, removing all existing key/value pairs.
     */
    public void clear() {
        this.overallRoot = null;
        this.size = 0;
    }

    /**
     * Retrieves the corresponding value for the specified key.
     * @param key key of interest
     * @return value corresponding to specified key, or null if if key is not found
     * @throws IllegalArgumentException if key is null
     */
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        return get(this.overallRoot, key);
    }

    private V get(TreeMapNode<K, V> root, K key) {
        if (root == null) return null;
        int compare = key.compareTo(root.key);
        if (compare == 0) {
            return root.value;
        } else if (compare < 0) {
            return get(root.left, key);
        } else {
            return get(root.right, key);
        }
    }

    /**
     * Adds a key/value pair to the tree map while preserving BST ordering.
     * Duplicate keys are placed in the left subtree to accomodate multiple entries with equal values.
     * @param key       the key in the key/value pair; used to organize the tree
     * @param value     the value in the key/value pair; this data is looked up through key-based searches
     */
    public void put (K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        this.overallRoot = put(this.overallRoot, key, value);
    }

    private TreeMapNode<K, V> put(TreeMapNode<K, V> root, K key, V value) {
        if (root == null) {
            this.size++;
            return new TreeMapNode<>(key, value);
        }
        int compare = key.compareTo(root.key);
        if (compare <= 0) {
            root.left = put(root.left, key, value);
        } else {
            root.right = put(root.right, key, value);
        }
        return root;
    }

    /**
     * Checks the tree to see if it contains the given key.
     * @param key the key to search for
     * @return true if the key is in the tree map, false otherwise
     */
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return containsKey(this.overallRoot, key);
    }

    private boolean containsKey(TreeMapNode<K, V> root, K key) {
        if (root == null) return false;
        int compare = key.compareTo(root.key);
        if (compare == 0){
            return true;
        } else if (compare < 0) {
            return containsKey(root.left, key);
        } else {
            return containsKey(root.right, key);
        }
    }

    /**
     * Retrieves an array of key data from the map in sorted in-order sequence.
     * @param array to fill in.  If smaller than the map's size, a new array will be created.  If larger than the
     *              map's size, data will be filled in from index 0, with a null reference just after the copied-in data.
     *              This parameter must not be null.
     * @return a reference to the filled-in array
     * @throws IllegalArgumentException if array is null
     */
    public K[] toKeyArray(K[] array) {
        if (array == null) throw new IllegalArgumentException("Array cannot be null.");
        K[] target = array;
        if (target.length < this.size) {
            target = Arrays.copyOf(array, this.size);
        }
        toKeyArrayHelper(this.overallRoot, target, 0);
        if (target.length > this.size) {
            target[this.size] = null;
        }
        return target;
    }

    private int toKeyArrayHelper(TreeMapNode<K, V> root, K[] target, int index) {
        if (root != null) {
            index = toKeyArrayHelper(root.left, target, index);
            target[index] = root.key;
            index++;
            index = toKeyArrayHelper(root.right, target, index);
        }
        return index;
    }

    /**
     * Retrieves an array of value data from the map in key in-order sequence.
     * @param array to fill in.  If smaller than the map's size, a new array will be created.  If larger than the
     *              map's size, data will be filled in from index 0, with a null reference just after the copied-in data.
     *              This parameter must not be null.
     * @return a reference to the filled-in array
     * @throws IllegalArgumentException if array is null
     */
    public V[] toValueArray(V[] array) {
        if (array == null) throw new IllegalArgumentException("Array cannot be null.");
        V[] target = array;
        if (target.length < this.size) {
            target = Arrays.copyOf(array, this.size);
        }
        toValueArrayHelper(this.overallRoot, target, 0);
        if (target.length > this.size) {
            target[this.size] = null;
        }
        return target;
    }

    private int toValueArrayHelper(TreeMapNode<K, V> root, V[] target, int index) {
        if (root != null) {
            index = toValueArrayHelper(root.left, target, index);
            target[index] = root.value;
            index++;
            index = toValueArrayHelper(root.right, target, index);
        }
        return index;
    }

    /**
     * Represents a single node in the binary search tree storing a key/value pair.
     * @param <K> key type
     * @param <V> value type
     */
    private static class TreeMapNode<K, V> {
        public K key;
        public V value;
        public TreeMapNode<K, V> left;
        public TreeMapNode<K, V> right;

        public TreeMapNode(K key, V value) {
            this(key, value, null, null);
        }

        public TreeMapNode(K key, V value, TreeMapNode<K, V> left, TreeMapNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}