package ua.nure.hliebov.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayImpl implements Array {

    private Object[] elements;
    private int size;

    public ArrayImpl() {
        elements = new Object[10]; // Початковий розмір масиву можна змінити за необхідності
        size = 0;
    }

    @Override
    public void add(Object element) {
        if (size == elements.length) {
            // Збільшення розміру масиву, якщо він заповнений
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
        elements[size] = element;
        size++;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        elements[index] = element;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return elements[index];
    }

    @Override
    public int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void clear() {
        elements = new Object[10];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(elements[i]).append(", ");
        }
        sb.append(elements[size - 1]).append("]");
        return sb.toString();
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Object> {

        private int currentIndex = 0;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            removable = true;
            return elements[currentIndex++];
        }

        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException();
            }
            ArrayImpl.this.remove(currentIndex - 1);
            currentIndex--;
            removable = false;
        }
    }

    public static void main(String[] args) {
        Array array = new ArrayImpl();
        array.add("A");
        array.add("B");
        array.add("C");

        System.out.println("toString: " + array.toString());

        System.out.print("Iterator: ");
        Iterator<Object> iterator = array.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();

        // Додаткові тести для демонстрації роботи інших методів
        System.out.println("Size: " + array.size());

        array.set(1, "D");
        System.out.println("After set: " + array.toString());

        System.out.println("Get at index 2: " + array.get(2));

        System.out.println("Index of 'B': " + array.indexOf("B"));

        array.remove(0);
        System.out.println("After remove: " + array.toString());
    }
}