package binarySearchTree;

public abstract class AbstractBalanceableTree<T extends Comparable> extends BinarySearchTree<T> {
        //class for extending our node functionality:
    /**
     * An extension of the Node class found in the BinarySearchTree class.
     * Gives an extra instance varible that helps ballance the tree.
     */
    protected static class BalNode<E extends Comparable> extends Node<E> {
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
    
    protected abstract BalNode<T> createNode(T e);

    /** 
     * reassign's a Node's pointers for a rotation
     *
     * @param parent the Node that will be a parent after a rotation
     * @param child the Node that will be the child of the parent specified in the parent argument
     * @param makeLeftChild if true, the child node will become the left child of the parent
     */
    protected void attach(Node parent, Node child, boolean makeLeftChild) {
	if(child != null) {
	    child.setParent(parent);
	}
	if(makeLeftChild) {
	    parent.setLeft(child);
	} else parent.setRight(child);
    }

    /** 
     * Rotates the given node so it is in the loctation of its parent
     *
     * @param pivot the node that is used as a pivot for the rotation
     */
    protected void rotate(Node pivot) {
	Node parent = pivot.getParent();
	Node grandpa = parent.getParent(); //may be null

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

    /** 
     * Determines how a node should be rotated for the tree to be balanced
     *
     * @param pivot the node that will be rotated
     */
    public Node restructure(Node pivot) {
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
