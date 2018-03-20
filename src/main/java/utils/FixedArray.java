package utils;

public class FixedArray<T> {
    private final Object[] array;
    private int counter = 0;
    private final int size;

    public FixedArray(int size) {
        this.size = size;
        array = new Object[size];
    }

    public void add(T item) {
        if (counter >= size) {
            throw new Error("Maximal capacity achieved");
        }
        array[counter++] = item;
    }

    public int size() {
        return counter;
    }

    public boolean isEmpty() {
        return counter == 0;
    }

    public void add(int index, T item) {
        if (index < 0 || index >= size) {
            throw new Error("Wrong index: " + index);
        }
        array[index] = item;
    }

    public void remove(int index) {
        add(index, null);
    }

    public void remove(T item) {
        for (int i=0 ; i<size ; i++) {
            if (array[i] != null && array[i].equals(item)){
                array[i] = null;
            }
        }
    }

    public boolean contains(T item) {
        for (int i=0 ; i<size ; i++) {
            if (array[i] != null && array[i].equals(item)){
                return true;
            }
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new Error("Wrong index: " + index);
        }
        return (T)array[index];
    }

    public void clear() {
        for (int i=0 ; i<size ; i++) {
            array[i] = null;
        }

        counter = 0;
    }
}
