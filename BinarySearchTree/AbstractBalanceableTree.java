package binarySearchTree;

public abstract class AbstractBalanceableTree<T extends Comparable> extends BinarySearchTree<T> {
        //class for extending our node functionality:
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

    public void rotateNode(T val) {
	rotate(getNode(val));
    }

    protected void rotate(Node pivot) {
	Node parent = pivot.getParent();
	Node grandpa = parent.getParent(); //may be null

	if(grandpa == null) {
	    root = pivot;
	    pivot.setParent(null);
	} else {
	    if(grandpa.getLeft().equals(parent)) {
		grandpa.setLeft(pivot);
		pivot.setParent(grandpa);
	    } else {
		grandpa.setRight(pivot);
		pivot.setParent(grandpa);
	    }
	}
	if(parent.getLeft().equals(pivot)) {
	    Node s = pivot.getRight();
	    pivot.setRight(parent);
	    parent.setLeft(s);
	} else {
	    Node s = pivot.getLeft();
	    pivot.setLeft(parent);
	    parent.setRight(s);
	}
    }

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
