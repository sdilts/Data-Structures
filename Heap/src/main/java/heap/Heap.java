package heap;

/** 
 * Heap implementation that is implemented with an arrayList
 * Works as a priority queue as well
 *
 * @author Stuart Dilts
 *  Time-stamp: <2016-02-20 00:20:09 stuart>
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Heap<E extends Comparable<E>> implements PriorityQueue<E> {

    private final ArrayList<E> heap;
    //private int size;

    /** Constructor for heap that creates a heap with initial size of 30 */
    public Heap() {
	heap = new ArrayList(20);
    }

    /**
     * Constructor for heap
     * @param size Initial size of the heap
     */
    public Heap(int size) {
	heap = new ArrayList(size);
    }

    @Override
    public void insert(E val) {
	heap.add(val);
	//treecode up:
	int index = heap.size()-1;

	while(heap.get(index).compareTo(heap.get(parent(index))) > 0) {
	    E temp = heap.set(parent(index), val);
	    heap.set(index, temp);
	    index = parent(index);
	}

    }
    /**
     * Removes the greatest value from the heap. Returns null if the
     * heap is empty
     * @return value removed
     **/
    @Override
    public E poll() {
	if(heap.size() > 0) {
	    E oldVal;
	    if(heap.size() == 1) {
		oldVal = heap.remove(heap.size()-1);
		//printTree();
	    } else {
		oldVal = heap.set(0, heap.remove(heap.size()-1));
		int index = 0;
		//need a bounds check for greatestchild
		int greatestChild = greatestChild(index);

		while(greatestChild != -1 && heap.get(index).compareTo(heap.get(greatestChild)) <= 0) {
		    //swap the values up and down:
		    E temp = heap.set(greatestChild, heap.get(index));
		    heap.set(index, temp);
		    index = greatestChild;
		    greatestChild = greatestChild(index);
		    // System.out.println("node swap:");
		    // printTree();
		    // greatestChild = greatestChild(index);
		    // System.out.println("index = " + index);
		    // System.out.println("greatestChild = " +
		    // 		       greatestChild);
		    // System.out.printf("%s < %s: %s\n", heap.get(index),
		    // 		  heap.get(greatestChild),
		    // 		  heap.get(index).compareTo(heap.get(greatestChild)) < 0);
		}
	    }
	    return oldVal;
	} else return null;
    }

    public int greatestChild(int i) {
	//check to see if its a leaf node;
	if(leftChild(i) >= heap.size()) {
	    //System.out.println("leaf");
	    return -1;
	} else {
	    //if only the left child:
	    if(rightChild(i) >= heap.size()) {
		return leftChild(i);
                //makes leftChild always higher priority than right:
	    } else if(heap.get(leftChild(i)).compareTo(heap.get(rightChild(i))) >= 0) {
		return leftChild(i);
	    } else {
		return rightChild(i);
	    }
	}
    }

    public void printTree() {
	if(!heap.isEmpty()) {
	    Queue<Integer> queue = new LinkedList();
	    queue.add(0);
	    int onLevel = 0;
	    int nextLevel = 1;
	    int levelNum = 0;
	    int height = binlog(heap.size());
	    System.out.println("Height = " + height);

	    System.out.println("----------------------");

	    int indent =  height - levelNum;
	    for(int i = 0; i < indent; i++) {
		System.out.print("  ");
	    }

	    while(!queue.isEmpty()) {
		onLevel++;
		int temp = queue.poll();
		System.out.printf(heap.get(temp) + " ");
		if(leftChild(temp) < heap.size()) {
		    queue.add(leftChild(temp));
		}
		if(rightChild(temp) < heap.size()) {
		    queue.add(rightChild(temp));
		}
		if(onLevel >= nextLevel) {
		    levelNum++;
		    System.out.println();
		    onLevel = 0;
		    nextLevel += nextLevel;
		    indent =  height - levelNum;
		    for(int i = 0; i < indent; i++) {
			System.out.print(" ");
		    }
		}
	    }
	}
	System.out.println("\n---------------------------");
    }

    public static int binlog( int bits ) {// returns 0 for bits=0 
	int log = 0;
	if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	if( bits >= 256 ) { bits >>>= 8; log += 8; }
	if( bits >= 16  ) { bits >>>= 4; log += 4; }
	if( bits >= 4   ) { bits >>>= 2; log += 2; }
	return log + ( bits >>> 1 );
    }

    private int parent(int i) {
	return (i-1)/ 2;
    }

    private int leftChild(int i) {
	return (i * 2) + 1;
     }

    private int rightChild(int i) {
	return (i * 2) + 2;
    }

    @Override
    public int size() {
	return heap.size();
    }

    @Override
    public void clear() {
	heap.clear();
	//size = 0;
    }

    @Override
    public E peek() {
	return heap.get(0);
    }

}