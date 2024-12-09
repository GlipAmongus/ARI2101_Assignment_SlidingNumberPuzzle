public interface HeapItem<T> extends Comparable<T> {
    int getHeapIndex();
    void setHeapIndex(int index);
}