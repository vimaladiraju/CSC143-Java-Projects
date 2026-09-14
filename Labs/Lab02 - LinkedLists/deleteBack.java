public int deleteBack() {
    if (front == null) {
        throw new java.util.NoSuchElementException();
    }

    if (front.next == null) {
        int value = front.data;
        front = null;
        return value;
    }

    ListNode current = front;
    while(current.next.next != null) {
        current = current.next;
    }

    int value = current.next.data;
    current.next = null;
    return value;

}



