import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomBag<E> implements Bag<E> {
  private final ArrayList<E> data = new ArrayList<>();
  private final Random rnd = new Random();

  @Override
  public void push(E e) {
    data.add(e); // append at end
  }

  @Override
  public E pop() {
    int n = data.size();
    if (n == 0) throw new NoSuchElementException("pop from empty bag");
    int i = rnd.nextInt(n);
    E res = data.get(i);
    int last = n - 1;
    if (i != last) {
      data.set(i, data.get(last)); // swap with last
    }
    data.remove(last);
    return res;
  }

  @Override
  public boolean isEmpty() {
    return data.isEmpty();
  }
}
