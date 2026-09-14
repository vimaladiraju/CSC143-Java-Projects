public ArrayIntList stutter() {
    ensureCapacity(size * 2); // ensure array is long enough
    for (int i = size - 1; i >= 0; i--) {
        int value = elementData[i];

        elementData[2 * i] = value;
        elementData[2 * i + 1] = value;
    }

    size *= 2;
    return this;
}