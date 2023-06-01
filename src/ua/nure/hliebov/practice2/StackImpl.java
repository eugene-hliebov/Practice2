package ua.nure.hliebov.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackImpl implements Stack{
    private Node top;
    private int size;

    private static class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
        }
    }

    @Override
    public void push(Object element) {
        Node newNode = new Node(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    @Override
    public Object pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object poppedData = top.data;
        top = top.next;
        size--;
        return poppedData;
    }

    @Override
    public Object top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return top.data;
    }

    @Override
    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node currentNode = top;
        while (currentNode != null) {
            sb.append(currentNode.data);
            if (currentNode.next != null) {
                sb.append(", ");
            }
            currentNode = currentNode.next;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    private boolean isEmpty() {
        return top == null;
    }

    private class IteratorImpl implements Iterator<Object> {

        private Node currentNode;

        public IteratorImpl() {
            currentNode = top;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Object element = currentNode.data;
            currentNode = currentNode.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Stack stack = new StackImpl();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        System.out.println("toString: " + stack.toString());

        System.out.print("Iterator: ");
        Iterator<Object> iterator = stack.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();

        // Additional tests to demonstrate the use of other methods
        System.out.println("Size: " + stack.size());

        System.out.println("Top: " + stack.top());

        System.out.println("Pop: " + stack.pop());
        System.out.println("After pop: " + stack.toString());
    }
}
