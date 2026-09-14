public ArrayIntList remove(int index) {
    checkIndex(index);

    for (int i = index; i < size - 1; i++) {
        elementData[i] = elementData[i+1];
    }

    size --;
    return this;
}
