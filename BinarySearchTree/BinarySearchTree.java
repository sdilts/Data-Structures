import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

/**
 * A Binary Search tree class that stores any single object in an ordered tree.
 * 
 * @author sdilts
 */
public class BinarySearchTree<E extends Comparable> {

    private Node<E> root;
    private int size;

    /**
     * Creates an empty binary search tree
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts the given value into the tree so that the tree is in order.
     *
     * @param newValue the value to be added to the tree.
     */
    public void insert(E newValue) {
        if (root == null) {
            root = new Node(newValue);
        } else {
            Node currentNode = root;
            boolean placed = false;
            while (!placed) {
                if (newValue.compareTo(currentNode.getValue()) < 0) {
                    if (currentNode.getLeft() == null) {
                        currentNode.setLeft(new Node(newValue));
                        currentNode.getLeft().setParent(currentNode);
                        placed = true;
                    } else {
                        currentNode = currentNode.getLeft();
                    }
                } else {
                    if (currentNode.getRight() == null) {
                        currentNode.setRight(new Node(newValue));
                        currentNode.getRight().setParent(currentNode);
                        placed = true;
                    } else {
                        currentNode = currentNode.getRight();
                    }
                }
            }
        }
	size++;
    }

    /**
     * Removes the first node with the given value from the tree.
     *
     * @param value the value used to find the node to be removed
     * @return true if the value is node is found and removed
     */
    public boolean remove(E value) {
	Node remove = lookupNode(root, value);
	return removeNode(remove);
    }

    /**
     * Removes the given node from the tree
     * The tree will remain sorted
     *
     * @param remove the node to be removed
     * @return true if the node is removed
     */
    public boolean removeNode(Node remove) {
	if(remove == null) {
	    return false;
	} else {
	    
	}
    }
    /**
     * Changes the value of the node identified with toChange to the value of newValue.
     * Will do nothing if a node with the value of toChange is not found.
     * This may make the tree usorted, so be careful.
     *
     * @param toChange the current value of the node that is to be modified
     * @param newValue the value that the node will be changed to.
     */
    public void modify(E toChange, E newValue) {
	Node mod = lookupNode(root, toChange);
	if(mod != null) {
	    mod.setValue(newValue);
	}
    }

    /**
     * Returns the Node below current with the given value.
     *
     * @param top the Node to start at when searching for value.
     * @param search the value for which to search for.
     * @return the node with the value of search
     */
    public Node lookupNode(Node top, E search) {
	if(top == null) {
	    return null;
	} else if(search.compareTo(top.getValue()) < 0) {
	    return lookupNode(top.getLeft(), search);
	} else if(search.compareTo(top.getValue()) > 0) {
	    return lookupNode(top.getRight(), search);
	} else return top;
    }	    

    /**
     * Returns true if the tree is a valid binary search tree. If the
     * tree is null, it will also return true.
     *
     * @return true if the tree is a valid binary search tree
     */
    public boolean isBST() {
	LinkedList<E> treeList =  getTreeList(root);
	Iterator it = treeList.iterator();
	if(it.hasNext()) {
	    E prev = (E) it.next();
	    while(it.hasNext()) {
		E cur = (E) it.next();
		if(prev.compareTo(cur) > 0) {
		    return false;
		} else {
		    prev = cur;
		}
	    }
	    return true;
	} else return true; //the list is empty - that means its a valid tree, right?
    }

    /**
     * Returns an ordered linked list composed of every node below that of the node given. 
     * Will return null if current is null.
     *
     * @param current the top node of the tree to be given as a linked list
     * @return An ordered linked list of the data in the tree
     */
    public LinkedList<E> getTreeList(Node current) {
	LinkedList<E> list = new LinkedList<E>();
	if(current.getLeft() != null) {
	    list.addAll(getTreeList(current.getLeft()));
	}
	list.add((E)current.getValue());
	if(current.getRight() != null) {
	    list.addAll(getTreeList(current.getRight()));
	}
	return list;
    }
    /**
     * Returns a shallow copy of the values in the tree as a sorted array.
     * The copied values are shallow, so the tree may become unsorted
     * if these values are changed.
     * 
     * @return a sorted array of the values in tree
     */
    public E[] copyToArray() {
	E[] array = (E[]) new Object[size];
	copyToArray(array);
	return array;
    }
	

    /**
     * Modifies the given array by copying all of the tree's values in order to replace the values already in the array.
     * The copied values are shallow, so the tree may become unsorted if these
     * values are changed.
     *
     * @throws IllegalArgumentException thrown when the given array is smaller than the size of the tree.
     * @param array the array that the tree will be copied to
     */
    public void copyToArray(E[] array) {
	if(array.length < size) {
	    throw new IllegalArgumentException("array passed to copyToArray(E[]) must be/nthe same size or larger than the size of the tree.");
	} else {
	    int index = 0;
	    Stack stack = new Stack();
	    Node current = root;
	    while(stack.size() != 0 || current != null) {
		if(current != null) {
		    stack.push(current);
		    current = current.getLeft();
		} else {
		    current = (Node) stack.pop();
		    array[index] = (E) current.getValue();
		    index++;
		    current = current.getRight();
		}
	    }
	}
    }

    /**
     * Returns the number of nodes in the tree.
     * @return the size of the tree
     */
    public int getSize() {
	return size;
    }
}
