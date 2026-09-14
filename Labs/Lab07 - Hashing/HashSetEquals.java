public boolean equals2(Object o) {
    if (o instanceof HashSet2) {
        HashSet2<Object> other = (HashSet2<Object>) o;
        if (this.size != other.size()) {
            return false;
        }

        for (Node front: elements) {
            Node current = front;
            while (current != null) {
                if (!other.contains(current.data)) {
                    return false;
                }
                current = current.next;
            }
        }

        return true;
    }
    return false;
}

