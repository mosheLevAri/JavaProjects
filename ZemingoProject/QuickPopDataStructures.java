package il.co.lird.FS133.Projects.ZemingoProject;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class QuickPopDataStructures<T> implements Iterable<T>, IQuickDataStructers<T> {

    private Node head = null;
    private Node tail = null;
    private final Comparator<T> comparator;
    private final Object lock = new Object();
    private final Semaphore semaphore = new Semaphore(0);

    public QuickPopDataStructures(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    @Override
    public void push(T element) {

        Node newNode = new Node(element);
        IteratorImplemention iter = new IteratorImplemention();
        boolean isFound = false;

        synchronized (lock) {


            if (head == null) {
                head = tail = newNode;
                return;
            }

            while (iter.hasNext()) {
                T data = iter.next();

                if (comparator.compare(element, data) > 0) {
                    iter.updateDirectionInNewNode(newNode);
                    isFound = true;
                }
            }

            if (!isFound) {
                tail.next = newNode;
                newNode.prev = tail;

                newNode = tail;
            }

            semaphore.release();
        }
    }

    @Override
    public T pop() {
        T returnValue = null;

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (lock) {
            returnValue = head.data;

            if (head == null) {
                throw new NoSuchElementException();
            }

            if (null != head.next) {
                head = head.next;
                head.prev = null;
            }
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

        private void updateDirectionInNewNode(Node node) {
            node.next = lastReturned;
            node.prev = lastReturned.prev;

            lastReturned.prev = node;

            if (null != node.prev) {
                node.prev.next = node;
            }
        }
    }
}
