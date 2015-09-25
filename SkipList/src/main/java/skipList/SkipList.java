/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skipList;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Deque;
import java.util.LinkedList;
/**
 *
 * @author stuart
 * @version 9.15.15
 * @param <K>
 * @param <V>
 **/
public class SkipList<K extends Comparable<K>, V> {
    
    private Node<K,V> head;
    private int size = 0;
    private int height = 0;
    private final Random r;
    
    public SkipList() {
        r = new Random();
        head = new Node<>(null, null);
        head.setNext(null);
    }
    
    public void put(K key, V value) {
        insert(key,value);
    }
    /**
     * Helper method that remov(defun toggle-fullscreen ()
  "Toggle full screen on X11"
  (interactive)
  (when (eq window-system 'x)
    (set-frame-parameter
     nil 'fullscreen
     (when (not (frame-parameter nil 'fullscreen)) 'fullboth))))

(global-set-key [f11] 'toggle-fullscreen)es a single node, and then cleans up after
     * itself.
     * @param n Node to be removed
     */
    private void removeNode(Node n) {
        
    }
    
    public void remove(K key) {
        Node<K,V> found = find(key);
        if(found.compareTo(key) != 0) {
            throw new NoSuchElementException();
        } else {
            //find() returns the topmost node in a tower:
        }
    }
    
    public V get(K key) throws NoSuchElementException {
        Node<K,V> found = find(key);
        if(found.compareTo(key) != 0) {
            throw new NoSuchElementException();
        } else return found.value();
    }
    
    
    //update nodes around newNode:
//            if(found.next() != null) {
//                found.next().setPrev(newNode);
//            }
//            found.setNext(newNode);
//            
    /**
     * Inserts a given node after oldNode
     * @param newNode
     * @param oldNode
     * @return 
     */
    private Node insertNode(Node newNode, Node oldNode) {
        //update nodes around newNode:r
        if(oldNode.next() != null) {
            oldNode.next().setPrev(newNode);
        }
        oldNode.setNext(newNode);
        return newNode;
    }
    
    public void insert(K key, V value) {
        Node <K,V> found = find(key);
        if(found.compareTo(key) != 0) {
            //make sure you are at the bottom of the lists:
            while(found.down() != null) {
                found = found.down();
            }
            //create new node that is connected where it needs to be:
            Node<K,V> newNode = insertNode(new Node<>(key, value, found, found.next()),
                                           found);
            int nodeHeight = 0;
            while(nodeHeight < 3 && r.nextBoolean()) { //change nodeHeight before release based on known data size
                while(found.prev() != null && found.up() == null) { //find the next tallest list
                    found = found.prev(); 
                }
                if(found.equals(head)) {
                    Node newHead = new Node(null, null);
                    newHead.setDown(head);
                    head.setUp(newHead);
                    head = newHead;
                    found = newHead;
                    height++;
                } else {
                    found = found.up();
                }
                Node tower = insertNode(new Node<>(key, value, found, found.next()),
                                     found);
                tower.setDown(newNode);
                newNode.setUp(tower);
                newNode = tower;
                nodeHeight++;
            }
            size++;
        } else found.setValue(value);
    }
    /**
     * Returns either the node with the supplied key, or if the key doesn't exist, 
     * the node with the next lowest key value. This is useful when adding nodes,
     * as it will return the node whose pointers need to be changed to add the new
     * node. Thus, if searching for a specific key, the returned nodes' key will
     * have to be compared to supplied key.
     * @param key the key being searched for 
     * @return the node that contains key, or the node with the next lowest key in the list
     */
    private Node find(K key) {
        Node<K,V> current = head;
	//Deque stack = new LinkedList();
        while(true) {
            if(current.next() != null) {
                int compare = current.next().compareTo(key);
                if(compare == 0) {
                    return new Pack(stack, current);
                } else if(compare > 0) {
		    //stack.push(current);
                    current = current.next();
                } else if(current.down() != null) {
                    current = current.down();
                } else return current; //new Pack(stack, current)
            } else if(current.down() != null) {
                current = current.down();
            } else return current; //new Pack(stack, current);
        }
    }
    
    public void printList() {
        Node current = head;
        while(current != null) {
            Node printed = current;
            while(printed != null) {
                System.out.printf("[%s]", printed.key());
                printed = printed.next();
            }
            System.out.println();
            current = current.down();
        }
        System.out.println();
    }
    
    public void printTower() {
        Node current = head;
        while(current.down() != null) {
            current = current.down();
        }
        
        while(current != null) {
            Node printed = current;
            while(printed != null) {
                System.out.printf("[%s]", printed.key());
                printed = printed.up();
            }
            System.out.print("\n|\n");
            current = current.next();
        }
        System.out.println();
    }
    
    public int size() {return size;}
    
    public int height() {return height;}
    
    // protected class Pack<F,S> {
    // 	private F first;
    // 	private S second;

    // 	public Pack(F first, S second) {
    // 	    this.second = second;
    // 	    this.first = first;
    // 	}

    // 	public F first() {return first;}
    // 	public S second() {return second;}
    // }
}