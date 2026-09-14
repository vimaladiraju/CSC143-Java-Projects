public boolean isSorted(Stack<Integer> s) {
    if (s.size() <= 1) {
        return true;
    } 
    Queue<Integer> q = new LinkedList<>();
    boolean sorted = true;
    int prev = s.pop();
    q.add(prev);

    while (!s.isEmpty()) {
        int current = s.pop();
        q.add(current);
        if (prev > current) {
            sorted = false;
        }
        prev = current;
    }

    while (!q.isEmpty()) {
        s.push(q.remove());
    }

    while (!s.isEmpty()) {
        q.add(s.pop());
    }

    while (!q.isEmpty()) {
        s.push(q.remove());
    }

    return sorted;
}
