package heap;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Heap h;
        h = new Heap();
	for(int i = 0; i < 10; i++) {
	    h.insert(new OrderTest(10, i));
	}
	h.printTree();

	System.out.println("Polling");
	for(int i = 0; i < 10; i++) {
            System.out.println("Polled: " + ((OrderTest) h.poll()).toString());
            h.printTree();
        }
    }

    private static class OrderTest<E extends Comparable<E>> implements Comparable<E> {

	public Integer order;
	public E number;

	public OrderTest(E number, int order) {
	    this.order = order;
	    this.number = number;
	}

	@Override
	public String toString() {
	    return Integer.toString(order);
	}

        @Override
	public int compareTo(Comparable compare) {
	    return this.number.compareTo((E) ((OrderTest) compare).number);
	}
    }
	    
}