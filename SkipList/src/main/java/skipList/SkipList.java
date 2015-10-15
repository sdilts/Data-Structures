/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skipList;


import java.util.Random;

public class SkipList<K extends Comparable<K>, V> {

    private int size;
    private Node<K,V> root;
    private int maxHeight;
    private int height;
    private Random r;

    /**
     * Constructs a Skip list with height 18. This value cannot be
     * changed after the object is created. See the other constructor
     * to specify a size.
     */
    public SkipList() {
	//instantiate the root value and set its values to null:
	//A general guideline for the height is that 2^h = max size of
	//list. For our purposes, 2^16 = ~65,000 is appropriate.
	r = new Random();
	maxHeight = 18;
	root = new Node<>(null,null, maxHeight);
    }
    /**
     * Constructs a skip list with the height specified by
     * maxHeight. This value cannot change once the object is created.
     * @param maxHeight the maximum height of the skip list.
     */
    public SkipList(int maxHeight) {
	//may want to check to see if maxHeight is a reasonable value
	r = new Random();
	this.maxHeight = maxHeight;
	root = new Node<>(null,null,maxHeight);
    }

   /**
     * Returns the value stored the by given key. Returns null if the key doesn't exist.
     * @param key The key used for the search
     * @return the value stored by <i>key</i>, null if the key doesn't exits.
     */
    public V get(K key) {
	Node<K,V> current = root;
	for(int i = height; i >= 0; i--) {
	    while(current.pointers[i] != null &&
		  current.pointers[i].key.compareTo(key) <= 0) {
		current = current.pointers[i];
	    }
	}
	if(current.key.compareTo(key) == 0) {
	    return current.value;
	} else return null;
    }
    /** 
     * Returns an array of Nodes that is useful for adding and removing elements.
     * The first element of the returned array will contain a node with the key 
     * <i>key</i> if a node exists with that key.
     * @param key
     * @return
     */
    private Node[] findAction(K key) {
	Node<K,V> current = root;
	Node[] tower = new Node[maxHeight];
	for(int i = height; i >= 0; i--) {
	    while(current.pointers[i] != null && current.pointers[i].key.compareTo(key) < 0) {
		current = current.pointers[i];
	    }
	    tower[i] = current;
	}
	return tower;		
    }
    /**
     * Determines how far up the tower a node should be inserted, based on probability.
     * @return 
     */
    private int determineLevel() {
	int random = r.nextInt();
	int lvl = 1;
	while(lvl < maxHeight && ((random >> lvl) & 1) == 1) {
	    lvl++;
	}
	return lvl;
    }
    /**
     * Inserts the value <i>value</i> into the list using the given key. 
     * Overwrites the previous value if it existed.
     * @param key
     * @param value
     * @return the previous value assigned to the supplied key, null otherwise.
     */
    public V put(K key, V value) {
	Node<K,V>[] tower = findAction(key);
	if(tower[0].pointers[0] != null && tower[0].pointers[0].key.compareTo(key) == 0) {
	    V old = tower[0].pointers[0].value;
	    tower[0].pointers[0].value = value;
	    return old;
	} else {
	    int lvl = determineLevel();
	    if(lvl > height) {
		//int count = 0;
		for(int i = height; i < lvl && i < maxHeight; i++) {
		    tower[i] = root;
		    height++; //update height
		}
		//height += count;
	    }
	    Node<K,V> newNode = new Node<>(key, value, lvl);
	    for(int i = 0; i < lvl; i++) {
		newNode.pointers[i] = tower[i].pointers[i];
		tower[i].pointers[i] = newNode;
	    }
	    size++;
	    return null;
	}
    }
    /**
     * Removes the node with the given key value and returns its value.
     * @param key
     * @return 
     */
    public V remove(K key) {
	Node[] tower = findAction(key);
	Node<K,V> n = tower[0].pointers[0];
	if(n != null && n.key.compareTo(key) == 0) {
	    for(int i = 0; i < n.pointers.length; i++) {
		tower[i].pointers[i] = n.pointers[i];
	    }
            size--;
	    while(height > 0 && root.pointers[height] == null) {
		height--;
	    }
            return n.value;
	} else return null;
    }
     
    public void printTower() {
	Node current = root;
        for (Node pointer : current.pointers) {
            System.out.print("[" + current.key + "]");
        }
System.out.println();
	current = current.pointers[0];
	while(current != null) {
            for (Node pointer : current.pointers) {
                System.out.print("[" + current.key + "]");
            }
	    System.out.println();
	    current = current.pointers[0];
	}
    }
    
    public void printList() {
        //Node current = root;
        for(int i = root.pointers.length-1; i >= 0; i--) {
            Node printed = root.pointers[i];
            while(printed != null) {
                System.out.printf("[%s]", printed.key);
                printed = printed.pointers[i];
            }
            if(root.pointers[i] != null) System.out.println();
        }
    }
    /**
     * Returns the size of the skip list:
     * @return 
     */
    public int size() {
	return size;
    }

    protected class Node<K extends Comparable<K>, V> {
	public Node<K,V>[] pointers;
	public V value;
	public K key;

	public Node(K key, V value) {
	    this.key = key;
	    this.value = value;
	}
 
	public Node(K key, V value, int level) {
	    this.key = key;
	    this.value = value;
	    pointers = new Node[level];
	}
   
    }
}
//THIS CODE NOT NEEDED: KEPT FOR REFERENCE PUROPSES
/*
   private class Pack<I,T> {
	public I first;
	public T second;
	public Pack(I first, T second) {
	    this.first = first;
	    this. second = second;
	}
   }
private Node<K,V> find(K key) {
	Node<K,V> current = root;
	int curHeight = height; //array is zero indexed
	while(true) {
	    if(current.pointers[curHeight] != null) {
		int compare =
		    current.pointers[curHeight].key.compareTo(key);
		if(compare == 0) {
		    return current.pointers[curHeight];
		} else if(compare < 0) {
		    current = current.pointers[curHeight];
		} else if(curHeight > 0) {
		    curHeight--;
		} else return current;
	    } else if(curHeight > 0) {
		curHeight--;
	    } else return current;
	}
}
public V get(K key) {
	Node<K,V> n = find(key);
	if(n.key.compareTo(key) == 0) {
	    return n.value;
	} else return null;
}
*/
