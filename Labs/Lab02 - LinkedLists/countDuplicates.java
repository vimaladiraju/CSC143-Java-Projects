public int countDuplicates() {
    int duplicates = 0;
    if (front == null) {
        return duplicates;
    }

    ListNode current = front;
    while (current.next != null) {
        if (current.data.equals(current.next.data)) {
            duplicates++;
        }
        current = current.next;
    }

    return duplicates;
}
