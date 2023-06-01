package ua.nure.hliebov.practice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListImpl implements List {

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

    private class IteratorImpl implements Iterator<Object> {

        private Node current = head;
        private Node previous = null;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            removable = true;
            Object data = current.data;
            previous = current;
            current = current.next;
            return data;
        }

        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException();
            }
            Node nextNode = current.next;
            if (previous == null) {
                head = nextNode;
            } else {
                previous.next = nextNode;
            }
            if (nextNode == null) {
                tail = previous;
            }
            current = previous;
            removable = false;
            size--;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    @Override
    public void addLast(Object element) {
        Node newNode = new Node(element);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
    }

    @Override
    public void removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node currentNode = head;
            while (currentNode.next != tail) {
                currentNode = currentNode.next;
            }
            currentNode.next = null;
            tail = currentNode;
        }
        size--;
    }

    @Override
    public Object getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    @Override
    public Object getLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        return tail.data;
    }

    @Override
    public Object search(Object element) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.data.equals(element)) {
                return currentNode.data;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public boolean remove(Object element) {
        Node currentNode = head;
        Node previousNode = null;
        while (currentNode != null) {
            if (currentNode.data.equals(element)) {
                if (previousNode == null) {
                    head = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
                if (currentNode == tail) {
                    tail = previousNode;
                }
                size--;
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        return false;
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

    public static void main(String[] args) {
        List list = new ListImpl();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        System.out.println("toString: " + list.toString());

        System.out.print("Iterator: ");
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            System.out.print(element + " ");
        }
        System.out.println();

        // Додаткові тести для демонстрації роботи інших методів
        System.out.println("Size: " + list.size());

        list.remove("B");
        System.out.println("After remove: " + list.toString());

        System.out.println("Search 'A': " + list.search("A"));

        list.addFirst("D");
        System.out.println("After addFirst: " + list.toString());
    }
}
