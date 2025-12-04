public class Stack<E> implements Bag<E> {
    private final java.util.ArrayDeque<E> data = new java.util.ArrayDeque<>();

    @Override
    public void push(E e) {
        data.addLast(e);
    }

    @Override
    public E pop() {
        if (data.isEmpty())
            throw new java.util.NoSuchElementException("pop from empty stack");
        return data.removeLast();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
