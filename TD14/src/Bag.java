public interface Bag<E> {
    void push(E e);
    E pop();
    boolean isEmpty();
}
