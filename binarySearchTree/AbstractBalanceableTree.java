/**>
 * A balanced AVL binary tree
 *
 * @author Dilts, Dilts, and Stowe
 */
package hashTable.binarySearchTree;

/**
 *
 * @author stuart
 * @param <T>
 */
public abstract class AbstractBalanceableTree<T extends Comparable<T>> extends BinarySearchTree<T> {
        //class for extending our node functionality:
    protected static class BalNode<E extends Comparable<E>> extends Node<E> {
    	//extra data needed for ballancing:
    	int aux = 0;

    	public BalNode(E val) {
    	    super(val);
    	}
	
    	public void setAux(int val) {
    	    aux = val;
    	}

    	public int getAux() {
    	    return aux;
    	}
    }
    //----------END OF BalNode CLASS---------
    
    /**
     *
     * @param e
     * @return
     */
    @Override
    protected abstract BalNode<T> createNode(T e);

    public void rotateNode(T val) {
	rotate(getNode(val));
    }

    protected void attach(Node<T> parent, Node<T> child, boolean makeLeftChild) {
	if(child != null) {
	    child.setParent(parent);
	}
	if(makeLeftChild) {
	    parent.setLeft(child);
	} else parent.setRight(child);
    }

    protected void rotate(Node<T> pivot) {
	Node<T> parent = pivot.getParent();
	Node<T> grandpa = parent.getParent(); //may be null

	if(grandpa == null) {
	    root = pivot;
	    pivot.setParent(null);
	} else {
	    attach(grandpa, pivot, parent == grandpa.getLeft());
	}
	//there are no possible duplicat nodes: == is okay
	if(parent.getLeft() == pivot) {
	    attach(parent,pivot.getRight(), true);
	    attach(pivot, parent, false);
	} else {
	    attach(parent, pivot.getLeft(), false);
	    attach(pivot, parent, true);
	}
    }

    public Node<T> restructure(Node<T> pivot) {
	//check nodes are in straigth line:
	//if both statments are true or if both statements are false:
	if((pivot.equals(pivot.getParent().getRight()))                               //if pivot is to the right of parent
	   == (pivot.getParent().equals(pivot.getParent().getParent().getRight()))) { //if parent is to the right of grandpa
	    rotate(pivot.getParent());
	    return pivot.getParent();
	} else { //Nodes are not in straight line:
	    rotate(pivot);
	    rotate(pivot);
	    return pivot;
	}
    }
}