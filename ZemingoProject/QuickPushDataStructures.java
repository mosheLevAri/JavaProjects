package il.co.lird.FS133.Projects.ZemingoProject;

import java.util.Comparator;
import java.util.Objects;

public class QuickPushDataStructures<T> {

    private Node head = null;
    private final Comparator<? super T> comparator;
    private final Object lock = new Object();

    public QuickPushDataStructures(Comparator<? super T> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    public void push(T element) {

        Node newNode = new Node(element);

        synchronized (lock) {
            if (head == null) {
                head = newNode;
                return;
            }

            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    public T pop() {

        T returnValue = null;

        if (null == head) {
            throw new NullPointerException(" elements NOT exist");
        }

        synchronized (lock) {

            Node max = findMaxValue();
            returnValue = max.data;
            removeNodeFromList(max);
        }

        return returnValue;
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
}