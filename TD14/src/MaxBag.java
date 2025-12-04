import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Collections;

public class MaxBag<E extends Comparable<? super E>> implements Bag<E> {
  private final PriorityQueue<E> pq = new PriorityQueue<>(Collections.reverseOrder());

  @Override
  public void push(E e) {
    pq.add(e);
  }

  @Override
  public E pop() {
    E e = pq.poll();
    if (e == null)
      throw new NoSuchElementException("pop from empty max bag");
    return e;
  }

  @Override
  public boolean isEmpty() {
    return pq.isEmpty();
  }
}

