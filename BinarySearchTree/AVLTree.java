package binarySearchTree;

/**
 * A balanced AVL binary tree implementation
 * @author sdilts
 */
public class AVLTree<E extends Comparable> {
    //class for extending our node functionality:
    protected class BalNode<E> extends Node<E> {
	//extra data needed for ballancing:
	int aux = 0;

	public Node(E val) {
	    super(val);
	}
	
	public void setAux(int val) {
	    aux = val;
	}

	public int getAux() {
	    return aux;
	}
    }
    //----------END OF BALNODE CLASS---------

    //get new createNode mehtod for lower functions:
    protected Node<E> createNode(E e) {
        return new BalNode<E>(e);
    }

    public AVLTree() {
	super();
    }
}
