import java.util.Comparator;

public class Heap<T extends HeapItem<T>> {
    private T[] items;
    private int currentItemCount;

    private Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public Heap(int maxHeapSize, Comparator<T> comparator) {
        items = (T[]) new HeapItem[maxHeapSize];
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    private void resizeHeap() {
        int newSize = items.length * 2; // Double the size
        T[] newItems = (T[]) new HeapItem[newSize];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    public void add(T item) {
        if (currentItemCount >= items.length) {
            resizeHeap();
        }
        item.setHeapIndex(currentItemCount);
        items[currentItemCount] = item;
        sortUp(item);
        currentItemCount++;
    }

    public T removeFirst() {
        T firstItem = items[0];
        currentItemCount--;
        items[0] = items[currentItemCount];
        items[0].setHeapIndex(0);
        sortDown(items[0]);
        return firstItem;
    }

    public void updateItem(T item) {
        sortUp(item);
    }

    public int size() {
        return currentItemCount;
    }

    public boolean contains(T item) {
        return items[item.getHeapIndex()].equals(item);
    }

    private void sortDown(T item) {
        while (true) {
            int childIndexLeft = item.getHeapIndex() * 2 + 1;
            int childIndexRight = item.getHeapIndex() * 2 + 2;
            int swapIndex = 0;

            if (childIndexLeft < currentItemCount) {
                swapIndex = childIndexLeft;

                if (childIndexRight < currentItemCount) {
                    if (items[childIndexLeft].compareTo(items[childIndexRight]) < 0) {
                        swapIndex = childIndexRight;
                    }
                }

                if (item.compareTo(items[swapIndex]) < 0) {
                    swap(item, items[swapIndex]);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void sortUp(T item) {
        int parentIndex = (item.getHeapIndex() - 1) / 2;

        while (parentIndex >= 0) {
            T parentItem = items[parentIndex];
            if (item.compareTo(parentItem) > 0) {
                swap(item, parentItem);
            } else {
                break;
            }
            parentIndex = (item.getHeapIndex() - 1) / 2;
        }
    }

    private void swap(T itemA, T itemB) {
        items[itemA.getHeapIndex()] = itemB;
        items[itemB.getHeapIndex()] = itemA;

        int itemAIndex = itemA.getHeapIndex();
        itemA.setHeapIndex(itemB.getHeapIndex());
        itemB.setHeapIndex(itemAIndex);
    }
}
