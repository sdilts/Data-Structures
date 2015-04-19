package binarySearchTree;

/**
 * A balanced AVL binary tree implementation
 * @author sdilts
 */
public class AVLTree<T extends Comparable> extends AbstractBalanceableTree<T> {
    //get new createNode mehtod for lower functions:
    @Override
    protected BalNode<T> createNode(T e) {
        return new BalNode<T>(e);
    }

    public AVLTree() {
	super();
    }
    
    public int getHeight(Node n) {
	if(n instanceof BalNode) {
	    return ((BalNode)n).getAux();
	} else throw new IllegalArgumentException("Node given is not of type 'BalNode'");
    }
}
