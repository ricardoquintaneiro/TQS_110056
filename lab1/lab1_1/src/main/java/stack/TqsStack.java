package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> collection;
    private int maxSize = -1;

    public TqsStack() {
        this.collection = new LinkedList<>();
    }

    public TqsStack(int maxSize) {
        this.collection = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot pop element.");
        }
        return collection.pop();
    }

    public int size() {
        return collection.size();
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot peek element.");
        }
        return collection.peek();
    }

    public void push(T item) {
        if (collection.size() == maxSize) {
            throw new IllegalStateException("Stack is full. Cannot push element.");
        }
        collection.push(item);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
}
