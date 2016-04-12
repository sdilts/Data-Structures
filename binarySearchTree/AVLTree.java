package hashTable.binarySearchTree;
import java.util.Iterator;

/**
 * A balanced AVL binary tree implementation
 *
 * @author Dilts, Dilts, and Stowe
 * @param <T>
 */
public class AVLTree<T extends Comparable<T>> extends AbstractBalanceableTree<T> {

    //get new createNode method for lower functions:
    @Override
    protected AbstractBalanceableTree.BalNode<T> createNode(T e) {
        return new BalNode<>(e);
    }

    public AVLTree() {
        super();
    }

    protected int getHeight(Node<T> n) {
        if (n != null) {
            if (n instanceof BalNode) {
                return ((BalNode) n).getAux();
            } else {
                throw new IllegalArgumentException("Node given is not of type 'BalNode'");
            }
        } else {
            return 0;
        }
    }

    protected void computeHeight(Node<T> n) {
        ((BalNode) n).setAux(1 + Math.max(getHeight(n.getLeft()), getHeight(n.getRight())));
    }

    protected boolean isBallanced(Node<T> n) {
        return Math.abs(getHeight(n.getLeft()) - getHeight(n.getRight())) <= 1;
    }

    protected Node<T> tallerChild(Node<T> n) {
        if (getHeight(n.getLeft()) > getHeight(n.getRight())) {
            return n.getLeft();
        } else if (getHeight(n.getLeft()) < getHeight(n.getRight())) {
            return n.getRight();
        } else if (n.equals(root)) {
            return n.getRight(); //doesn't matter
        } else if (n.equals(n.getLeft().getParent())) {
            return n.getLeft();
        } else {
            return n.getRight();
        }
    }

    protected void balance(Node<T> current) {
        int oldHeight, newHeight;
        do {
            oldHeight = getHeight(current);
            if (!isBallanced(current)) {
                current = restructure(tallerChild(tallerChild(current)));
                computeHeight(current.getLeft());
                computeHeight(current.getRight());
            }
            computeHeight(current);
            newHeight = getHeight(current);
            current = current.getParent();
        } while (newHeight != oldHeight && current != null);

    }

    @Override
    public boolean add(T val) {
        //create node to be inserted and ballance it
	Node<T> newNode = insertValue(val);
	if(newNode != null) {
	    balance(newNode);
	    return true;
	} else return false;
    }

    @Override
    public boolean remove(T val) {
        Node<T> remove = lookupNode(root, val);
        if (super.remove(remove)) {
	    //root is gone: check to see if removed has a parent
            if (remove.getParent() != null) {
                balance(remove.getParent());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void remove(T low, T high) {
        super.remove(low, high);
        balance(root);
    }
}