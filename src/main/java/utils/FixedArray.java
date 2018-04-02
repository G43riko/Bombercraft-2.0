package utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class FixedArray<T> implements Iterable<T>{
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
        if (index < 0 || index > counter) {
            throw new Error("Wrong index: " + index + ", size: " + counter);
        }
        array[index] = item;
    }

    public void remove(int index) {
        add(index, null);
    }

    public void remove(T item) {
        for (int i=0 ; i<counter ; i++) {
            if (array[i] != null && array[i].equals(item)){
                array[i] = null;
            }
        }
    }

    public boolean contains(T item) {
        for (int i=0 ; i<counter ; i++) {
            if (array[i] != null && array[i].equals(item)){
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	public T get(int index) {
        if (index < 0 || index >= counter) {
            throw new Error("Wrong index: " + index);
        }
        return (T)array[index];
    }

    public void clear() {
        for (int i=0 ; i<counter ; i++) {
            array[i] = null;
        }

        counter = 0;
    }
    
    @Override
    public String toString() {
    	return Arrays.toString(array);
    }

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int _counter = 0;
			private int _size = counter;
			@Override
			public boolean hasNext() {
				return _counter <= _size;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T next() {
				return (T)array[_counter++];
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void forEach(Consumer<? super T> action) {
        for (int i=0 ; i<counter ; i++) {
        	action.accept((T)array[i]);;
        }
	}
	
	Object[] toArray() {
		return Arrays.copyOfRange(array, 0, counter + 1);
	}
}
