package stack;

import java.util.LinkedList;

public class TqsStack<T> {
    private LinkedList<T> collection;

    public TqsStack() {
        this.collection = new LinkedList<>();
    }

    public T pop() {
        throw new NoClassDefFoundError();
        // return collection.pop();
    }

    public int size() {
        return collection.size();
    }

    public T peek() {
        return collection.peek();
    }

    public void push(T item) {
        collection.push(item);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
}
