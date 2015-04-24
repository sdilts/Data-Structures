
package binarySearchTree;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

/**
 * A Binary Search tree class that stores any single object in an ordered tree.
 * It will not store both a key and value, so consider using an object with a 
 * key and an element whose compareTo method reflects that of its key.
 * 
 * @author sdilts
 */
public class BinarySearchTree<T extends Comparable> {

    protected static class Node<E extends Comparable> {

	private E value;
	private Node<E> left;
	private Node<E> right;
	private Node<E> parent;

	public Node(E value) {
	    this.value = value;
	}

	public int compareTo(Node compare) {
	    return this.getValue().compareTo(compare.getValue()); 
	}

	public boolean isLeaf() {
	    if(left == null && right == null) {
		return true;
	    } else return false;
	}

	public E getValue() {
	    return value;
	}
    
	public void setValue(E value) {
	    this.value = value;
	}

	public void setLeft(Node<E> left) {
	    this.left = left;
	}

	public Node<E> getLeft() {
	    return left;
	}

	public void setRight(Node<E> right) {
	    this.right = right;
	}

	public Node<E> getRight() {
	    return right;
	}

	public void setParent(Node parent) {
	    this.parent = parent;
	}

	public Node<E> getParent() {
	    return parent;
	}
    }//------------END OF NODE CLASS------------

    //allows us to change behavior of general methods:
    protected Node<T> createNode(T e) {
	return new Node<T>(e);
    }

    protected Node<T> root;
    private int size;

    /**
     * Creates an empty binary search tree
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts the given element into the tree so that the tree is sorted.
     *
     * @param newValue the value to be added to the tree.
     */ 
    public void insert(T value) {
	insertNode(createNode(value));
    }

    /**
     * inserts the given node into the tree
     * @param newNode the node to be inserted
     */
    protected void insertNode(Node newNode) {
	if (root == null) {
	    root = newNode;
	} else {
	    Node currentNode = root;
	    boolean placed = false;
	    while (!placed) {
		if (newNode.compareTo(currentNode) < 0) {
		    if (currentNode.getLeft() == null) {
			currentNode.setLeft(newNode);
			currentNode.getLeft().setParent(currentNode);
			placed = true;
		    } else {
			currentNode = currentNode.getLeft();
		    }
		} else {
		    if (currentNode.getRight() == null) {
			currentNode.setRight(newNode);
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
     * Returns the Node below top with the given value.
     *
     * @param top the Node to start at when searching for value.
     * @param search the value for which to search for.
     * @return the node with the value of search
     */
    protected Node lookupNode(Node top, T search) {
	if(top == null) {
	    return null;
	} else if(search.compareTo(top.getValue()) < 0) {
	    return lookupNode(top.getLeft(), search);
	} else if(search.compareTo(top.getValue()) > 0) {
	    return lookupNode(top.getRight(), search);
	} else return top;
    }	    

    /**
     * Returns the Node with the given value
     * @param search the value for which to search for
     */
    protected Node getNode(T search) {
	return lookupNode(root, search);
    }

    private void promoteLeft(Node rn) {
	if(rn == root) {
	    root = rn.getLeft();
	    root.setParent(null);
	} else if(rn.getParent().getRight() == rn) {
	    rn.getParent().setRight(rn.getLeft());
	} else {
	    rn.getParent().setLeft(rn.getLeft());
	}
	rn.getLeft().setParent(rn.getParent());
    }

    private void promoteRight(Node rn) {
	if(rn == root) {
	    root = rn.getRight();
	    root.setParent(null);
	} else if(rn.getParent().getRight() == rn) {
	    rn.getParent().setRight(rn.getRight());
	} else {
	    rn.getParent().setLeft(rn.getRight());
	}
	rn.getRight().setParent(rn.getParent());
    }	

    /**
     * Removes the given node from the tree.
     * The tree will remain sorted.
     *
     * @param remove the node to be removed
     * @return true if the node is removed
     */
    protected boolean remove(Node rn) {
	if(rn == null) {
	    return false;
	}
	if(rn.getLeft() == null || rn.getRight() == null) {
	    if(rn.getLeft() != null) {
		promoteLeft(rn);
	    } else if(rn.getRight() != null) {
		promoteRight(rn);
	    } else { //node is a leaf
		if(rn == root) {
		    root = null;
		} else if(rn.getParent().getRight() == rn) {
		    rn.getParent().setRight(null);
		} else {
		    rn.getParent().setLeft(null);
		}
	    }
	} else { //node is full:
	    Node left = rn.getLeft();
	    Node next = left;
	    while(next.getRight() != null) {
		next = next.getRight();
	    }
	    rn.setValue(next.getValue());
	    remove(next);
	}
	return true;
    }

 
    /**
     * Removes the first element with the given value from the tree.
     *
     * @param value the value used to find the node to be removed
     * @return true if an valid node is found and removed
     */
    public boolean remove(T value) {
	Node remove = lookupNode(root, value);
	return remove(remove);
    }

    /**
     * Changes the value of the node identified with toChange to the value of newValue.
     * Will do nothing if a node with the value of toChange is not found.
     * This may make the tree usorted, so be careful.
     *
     * @param toChange the current value of the node that is to be modified
     * @param newValue the value that the node will be changed to.
     */
    public void modify(T toChange, T newValue) {
	Node mod = lookupNode(root, toChange);
	if(mod != null) {
	    mod.setValue(newValue);
	}
    }

    /**
     * Returns true if the tree is a valid binary search tree. If the
     * tree is null, it will also return true.
     *
     * @return true if the tree is a valid binary search tree
     */
    public boolean isBST() {
	LinkedList<T> treeList =  getTreeList(root);
	Iterator it = treeList.iterator();
	if(it.hasNext()) {
	    T prev = (T) it.next();
	    while(it.hasNext()) {
		T cur = (T) it.next();
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
     * Will return null if the given node is null.
     *
     * @param current the top node of the tree to be given as a linked list
     * @return An ordered linked list of the data in the tree
     */
    //Note: this method is here for historic value and is not expected to be used:
    public LinkedList<T> getTreeList(Node current) {
	LinkedList<T> list = new LinkedList<T>();
	if(current.getLeft() != null) {
	    list.addAll(getTreeList(current.getLeft()));
	}
	list.add((T)current.getValue());
	if(current.getRight() != null) {
	    list.addAll(getTreeList(current.getRight()));
	}
	return list;
    }

    //Should there be a place for a copyToArray() method?
    //will be implemented when tree implements list

    /**
     * Modifies the given array by copying all of the tree's values in order to replace the values already in the array.
     * The copied values are shallow, so the tree may become unsorted if these
     * values are changed.
     *
     * @throws IllegalArgumentException thrown when the given array is smaller than the size of the tree.
     * @param array the array that the tree will be copied to
     */
    public void copyToArray(T[] array) {
	if(array.length < size) {
	    throw new IllegalArgumentException("array passed to copyToArray(E[]) must be the same size or larger than the size of the tree.");
	} else {
	    int index = 0;
	    Stack stack = new Stack(); //replicate call stack
	    Node current = root;
	    while(stack.size() != 0 || current != null) {
		if(current != null) {
		    stack.push(current);
		    current = current.getLeft();
		} else {
		    current = (Node) stack.pop();
		    array[index] = (T) current.getValue();
		    index++;
		    current = current.getRight();
		}
	    }
	}
    }

    /**
     * Returns the number of elements in the tree.
     * @return the size of the tree
     */
    public int size() {
	return size;
    }
}
