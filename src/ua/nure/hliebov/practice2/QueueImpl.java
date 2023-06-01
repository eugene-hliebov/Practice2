package ua.nure.hliebov.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueImpl implements Queue{
    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
        }
    }

    @Override
    public void enqueue(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public Object dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object removedData = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return removedData;
    }

    @Override
    public Object top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
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
        Node currentNode = head;
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
        return head == null;
    }

    private class IteratorImpl implements Iterator<Object> {

        private Node currentNode;

        public IteratorImpl() {
            currentNode = head;
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
        Queue queue = new QueueImpl();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        System.out.println("toString: " + queue.toString());

        System.out.print("Iterator: ");
        Iterator<Object> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();

        // Додаткові тести для демонстрації роботи інших методів
        System.out.println("Size: " + queue.size());

        System.out.println("Top: " + queue.top());

        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("After dequeue: " + queue.toString());
    }
}
