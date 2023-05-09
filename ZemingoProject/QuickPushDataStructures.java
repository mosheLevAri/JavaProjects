package il.co.lird.FS133.Projects.ZemingoProject;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class QuickPushDataStructures<T> implements Iterable<T>, IQuickDataStructers<T>{

    private Node head = null;
    private final Comparator<T> comparator;
    private final Object lock = new Object();

    public QuickPushDataStructures(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    @Override
    public void push(T element) {
        Node newNode = new Node(element);

        synchronized (lock) {
            if (head == null) {
                head = newNode;
            }
            else
            {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        }
    }

    @Override
    public T pop() {
        T returnValue = null;

        synchronized (lock) {

            if (head == null) {
                throw new NoSuchElementException();
            }

            Node max = findMaxValue();
            returnValue = max.data;
            removeNodeFromList(max);
        }
        return returnValue;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImplemention();
    }


    private class Node {

        private T data = null;
        private Node prev = null;
        private Node next = null;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node findMaxValue() {
        Node maxValue = head;
        Node temp = head;

        while (null != temp) {

            if (comparator.compare(maxValue.data, temp.data) < 0) {
                maxValue = temp;
            }
            temp = temp.next;
        }

        return maxValue;
    }

    private void removeNodeFromList(Node maxValue) {
        if (null != maxValue.next) {
            maxValue.next.prev = maxValue.prev;
        }

        if (null != maxValue.prev) {
            maxValue.prev.next = maxValue.next;
        } else {
            head = maxValue.next;
        }

        maxValue.next = null;
        maxValue.prev = null;
    }

    private class IteratorImplemention implements Iterator<T> {

        private Node currentNode = head;
        private Node lastReturned = currentNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            lastReturned = currentNode;
            currentNode = currentNode.next;

            return lastReturned.data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}