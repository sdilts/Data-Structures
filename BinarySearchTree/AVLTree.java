package binarySearchTree;

/**
 * A balanced AVL binary tree implementation
 * @author sdilts
 */
public class AVLTree<T extends Comparable> extends AbstractBalanceableTree<T> {
    //get new createNode method for lower functions:
    @Override
    protected BalNode<T> createNode(T e) {
        return new BalNode<T>(e);
    }

    public AVLTree() {
	super();
    }
    
    public int getHeight(Node n) {
	if(n != null) {
	    if(n instanceof BalNode) {
		return ((BalNode)n).getAux();
	    } else throw new IllegalArgumentException("Node given is not of type 'BalNode'");
	} else return 0;
    }

    protected void computeHeight(Node n) {
	((BalNode)n).setAux(1+ Math.max(getHeight(n.getLeft()), getHeight(n.getRight())));
    }

    protected boolean isBallanced(Node n) {
	return Math.abs(getHeight(n.getLeft()) - getHeight(n.getRight())) <= 1;
    }

    protected Node tallerChild(Node n) {
	if(getHeight(n.getLeft()) > getHeight(n.getRight())) {
	    return n.getLeft();
	} else if(getHeight(n.getLeft()) < getHeight(n.getRight())) {
	    return n.getRight();
	} else if(n.equals(root)) {
	    return n.getRight();
	} else if(n.equals(n.getLeft().getParent())){
	    return n.getLeft();
	} else return n.getRight();
    }

    public void insert(T val) {
	//create node to be inserted
	Node current = createNode(val);
	super.insertNode(current);
	int oldHeight,newHeight;
	do {
	    oldHeight = getHeight(current);
	    if(!isBallanced(current)) {
		current = restructure(tallerChild(tallerChild(current)));
		computeHeight(current.getLeft());
		computeHeight(current.getRight());
	    }
	    computeHeight(current);
	    newHeight = getHeight(current);
	    current = current.getParent();
	} while(newHeight != oldHeight && current != null);;
    }
}

