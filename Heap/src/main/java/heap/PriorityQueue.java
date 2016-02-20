package heap;

public interface PriorityQueue<T extends Comparable<T>> {

    public void insert(T val);

    /**
     * Removes and returns the top of the queue
     **/
    public T poll();

    public int size();

    public void clear();

    public T peek();
}