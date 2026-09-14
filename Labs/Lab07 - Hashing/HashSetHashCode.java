public int hashCode() {
    int sum = 0;
    for (Node front : elements) {
        Node current = front;
        while (current != null) {
            if (current.data != null) {
                sum += current.data.hashCode();
            }
            current = current.next;
        }
    }
    return sum;
}
