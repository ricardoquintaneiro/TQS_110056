package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> collection;

    public TqsStack() {
        this.collection = new LinkedList<>();
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
        collection.push(item);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
}
