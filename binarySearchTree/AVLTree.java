package binarySearchTree;

/**
 * A balanced AVL binary tree implementation
 *
 * This implementation will not store both a key and value, so consider 
 * using an object with a key and an element whose compareTo method
 * reflects that of its key. Also see the parent class for more information.
 * 
 * @author sdilts
 */
public class AVLTree<T extends Comparable> extends AbstractBalanceableTree<T> {
    //get new createNode method for lower functions:
    @Override
    protected BalNode<T> createNode(T e) {
        return new BalNode<T>(e);
    }

    /**
     * Creates an empty binary search tree
     */
    public AVLTree() {
	super();
    }
    /**
     * returns the height of the node as specified by Node.getAux
     * This is a convienence method- need type conversion everytime 
     * getAux() is called on the node
     *
     * @param n the node you need to get the height of
     * @return the height of the node 
     */
    protected int getHeight(Node n) {
	if(n != null) {
	    if(n instanceof BalNode) { //problably not needed, but it will check for errors:
		return ((BalNode)n).getAux();
	    } else throw new IllegalArgumentException("Node given is not of type 'BalNode'");
	} else return 0;
    }

    protected void computeHeight(Node n) {
	//add one to the tallest child and set it to aux
	((BalNode)n).setAux(1+ Math.max(getHeight(n.getLeft()), getHeight(n.getRight())));
    }

    /**
     * Checks to see if the Node's children's heights differ greater than one.
     *
     * @return true if difference between childrens' heights are not more than one
     */
    protected boolean isBallanced(Node n) {
	return Math.abs(getHeight(n.getLeft()) - getHeight(n.getRight())) <= 1;
    }

    protected Node tallerChild(Node n) {
	if(getHeight(n.getLeft()) > getHeight(n.getRight())) {
	    return n.getLeft();
	} else if(getHeight(n.getLeft()) < getHeight(n.getRight())) {
	    return n.getRight();
	} else if(n.equals(root)) {
	    return n.getRight(); //doesn't matter what node is given
	} else if(n.equals(n.getLeft().getParent())){ //tiebreaker:
	    return n.getLeft(); //ita a left child
	} else return n.getRight(); //its a right child
    }
    /** 
     * Balances the tree starting at the given node and working upwards to the root
     *
     * @param current the first node to vist while rebalancing the tree 
     */
    protected void balance(Node current) {
	int oldHeight,newHeight;
	//will run until balanced or reached root of tree
	do {
	    oldHeight = getHeight(current);
	    if(!isBallanced(current)) {
		current = restructure(tallerChild(tallerChild(current)));
		computeHeight(current.getLeft());
		computeHeight(current.getRight());
	    }
	    computeHeight(current);
	    newHeight = getHeight(current);
	    current = current.getParent(); //move up the tree - everything below should be balanced
	} while(newHeight != oldHeight && current != null);

    }

    /**
     * Inserts the given element into the tree so that the tree is in order.
     *
     * @param newValue the value to be added to the tree.
     */     
    @Override
    public void insert(T val) {
	//create node to be inserted
	Node newNode = createNode(val);
	super.insertNode(newNode);
	balance(newNode);
    }

    /**
     * Removes the first element with the given value from the tree. The tree
     * will remain sorted.
     *
     * @param value the value used to find the node to be removed
     * @return true if an valid node is found and removed
     */
    @Override
    public boolean remove(T val) {
	Node remove = lookupNode(root, val);
	if(super.remove(remove)) {
	    if(!remove.equals(root)) {
		balance(remove.getParent());
	    }
	    return true;
	} else return false;
    }
}
