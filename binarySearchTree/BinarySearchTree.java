
package hashTable.binarySearchTree;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;
import java.util.Queue;

/**
 * A Binary Search tree class that stores any single object in an ordered tree.
 * Allows duplicates: the program counts the number of times a certain value with
 * the same key value is inserted into tree. Not safe for objects that are fundamentally
 * different yet have the same value when compareTo is called on them.
 * 
 * @author Dilts, Dilts, and Stowe
 * @param <T> Comparable object that serves as both the value being stored and the key
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>{

    protected static class Node<E extends Comparable<E>> implements Comparable<E> {

	private E value;
	//var can be used to hold many values with the same key: not
	//actually needed, but iterating will break:
	private static final int count = 1;
	private Node<E> left;
	private Node<E> right;
	private Node<E> parent;

	public Node(E value) {
	    this.value = value;
	}

	public int compareTo(Node<E> compare) {
	    return this.getValue().compareTo((E) compare.getValue()); 
	}

        @Override
	public int compareTo(E compare) {
            return this.getValue().compareTo(compare);
        }

	public boolean isLeaf() {
            return left == null && right == null;
	}

	public E getValue() {
	    return value;
	}
    
	public void setValue(E value) {
	    this.value = value;
	}

	// public void plusCount() { //see note above on the count variable
	//     count++;
	// }

	public int getCount() {
	    return count;
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

	public void setParent(Node<E> parent) {
	    this.parent = parent;
	}

	public Node<E> getParent() {
	    return parent;
	}

    }//------------END OF NODE CLASS------------

    //allows us to change behavior of general methods:
    protected Node<T> createNode(T e) {
	return new Node<>(e);
    }

    protected Node<T> root;
    protected int size;

    /**
     * Creates an empty binary search tree
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts the given value into the tree so that the tree is in
     * order. Duplicate values are not allowed; the method will return
     * false if the value could not be inserted.
     * 
     * @param value the value to be inserted into the tree.
     * @return true if the value could be inserted
     */
    public boolean add(T value) {
        return insertValue(value) != null;
    }

    /**
     * Inserts a node with the given value into the tree, and returns
     * the inserted node. If the node cannot be inserted, the method
     * returns a null value.
     * @param value the key value of the node to be inserted
     * @return the node that was inserted. Null if no node was inserted
     **/
    protected Node<T> insertValue(T value) {
	Node<T> newNode = createNode(value);
        if (root == null) {
	    root = newNode;
	} else {
	    Node<T> currentNode = root;
	    boolean placed = false;
	    while (!placed) {
		//if we have already have a node of value:
		if(newNode.compareTo(currentNode) == 0) {
		    return null;
		    //placed = true;
		} else if (newNode.compareTo(currentNode) < 0) {
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
	size++; //always will succeed at inserting an node if this
	        //point is reached
	return newNode;
    }	

    /**
     * Returns the Node below top with the given value.
     *
     * @param top the Node to start at when searching for value.
     * @param search the value for which to search for.
     * @return the node with the value of search
     */
    protected Node<T> lookupNode(Node<T> top, T search) {
        
        while(top != null && search.compareTo(top.getValue()) != 0) {
            if(search.compareTo(top.getValue()) < 0) {
                top = top.getLeft();
            } else {
                top = top.getRight();
            }
        }
        return top;
    }	    

    public boolean contains(T search) {
	return lookupNode(root, search) != null;
    }

    /**
     * Returns the Node with the given value
     * @param search the value for which to search for
     * @return node with the specified value. Null if not found.
     */
    public Node<T> getNode(T search) {
	return lookupNode(root, search);
    }

    /**
     * Removes the first node with the given value from the tree.
     * @param value
     * @return 
     */
    public boolean remove(T value) {
	Node<T> remove = lookupNode(root, value);
	return remove(remove);
    }

    private void promoteLeft(Node<T> rn) {
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

    private void promoteRight(Node<T> rn) {
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
     * @param rn the node to be removed
     * @return true if the node is removed
     */
    public boolean remove(Node<T> rn) {
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
	    Node<T> left = rn.getLeft();
	    Node<T> next = left;
	    while(next.getRight() != null) {
		next = next.getRight();
	    }
	    rn.setValue((T) next.getValue());
	    remove(next);
	}
	size -= rn.getCount();
	return true;
    }
    
    public void remove(T low, T high) {
        Queue<Node<T>> queue = new LinkedList<>();
        Stack<Node<T>> stackie = new Stack<>();
	
        if (root == null) {
            return;
        }
	
        queue.clear();
        queue.add(root);
        while (!queue.isEmpty()) {

            Node<T> node = queue.remove();

            if (node.compareTo(low) >= 0 && node.compareTo(high) <= 0) {
                stackie.add(node);
            }
            if (node.getLeft() != null){// && node.compareTo(low) > 0) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null){// && node.compareTo(high) < 0) {
                queue.add(node.getRight());
            }

        }

        while (!stackie.isEmpty()) {
            remove(stackie.pop());
        }
    }    

    /**
     * Changes the value of the node identified with toChange to the value of newValue.
     * Will do nothing if a node with the value of toChange is not found.
     * This may make the tree unsorted, so be careful.
     *
     * @param toChange the current value of the node that is to be modified
     * @param newValue the value that the node will be changed to.
     */
    public void modify(T toChange, T newValue) {
	Node<T> mod = lookupNode(root, toChange);
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
	Iterator<T> it = treeList.iterator();
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
     * Will return null if current is null.
     *
     * @param current the top node of the tree to be given as a linked list
     * @return An ordered linked list of the data in the tree
     */
    public LinkedList<T> getTreeList(Node<T> current) {
	LinkedList<T> list = new LinkedList<>();
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
	    throw new IllegalArgumentException("array passed to copyToArray(E[]) must be/nthe same size or larger than the size of the tree.");
	} else {
	    int index = 0;
	    Stack<Node<T>> stack = new Stack<>();
	    Node<T> current = root;
	    while(stack.size() != 0 || current != null) {
		if(current != null) {
		    stack.push(current);
		    current = current.getLeft();
		} else {
		    current = stack.pop();
		    for(int i = 0; i < current.getCount(); i++) {
			array[index] = (T) current.getValue();
			index++;
		    }
		    current = current.getRight();
		}
	    }
	}
    }

    /**
     * Returns the number of nodes in the tree.
     * @return the size of the tree
     */
    public int size() {
	return size;
    }

    // public int getOcurrances(T value) {
    //     Node n = getNode(value);
    //     if(n != null) 
    //         return n.getCount();
    //     else
    //         return 0;
    // }
    
    @Override
    public Iterator<T> iterator() {
        return new InorderIterator<>();
    }

    private class InorderIterator<T extends Comparable<T>> implements Iterator<T> {

        Node<T> next;
        int steps = 1;
        int times = 0;

	@SuppressWarnings("unchecked")
	public InorderIterator() {
	    
	    next = (Node<T>) root;
            if (next != null) {
                while (next.getLeft() != null) {
                    next = next.getLeft();
                }
            }
        }

        @Override
        public boolean hasNext() {
            return size != times;
        }

        @Override
        public T next() {
            Node<T> n = next;

            if (steps != n.getCount()) {
                steps++;
                times++;
                return n.getValue();
            } else if (size != times) {
                
                //go right one and left as far as possible
                if (next.getRight() != null) {
                    next = next.getRight();
                    while (next.getLeft() != null) {
                        next = next.getLeft();
                    }
                    //else if we are coming from left
                } else if (next != root && next == next.getParent().getLeft()) {
                    next = next.getParent();
                    //else if we are coming from right
                } else {
                    //move up while we are coming from right
                    while (next != root && next == next.getParent().getRight()) {
                        next = next.getParent();
                    }
                    //go up one parent
                    next = next.getParent();
                }
                
            } 
            times++;
            steps = 1;
            
            return (T) n.getValue();
        }
    }
}