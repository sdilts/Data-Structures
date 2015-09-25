/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skipList;

/**
 *
 * @author stuart
 * @param <K>
 * @param <V>
 **/
public class Node<K extends Comparable<K>, V> implements Comparable{
    
    private final K key;
    private V value;
    private Node<K,V> up, down, next, prev;
    //private int level;
    
    public Node(K key,V value) {
        this.key = key;
        this.value = value;
        //this.level = level;
    }
    
    public Node(K key, V value, Node prev, Node next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
    
    @Override
    public int compareTo(Object o) {
        if(key == null) {
            return -1;
        } else { 
            return key.compareTo((K) o);
        }
    }
    
    public void setValue(V value) {
        this.value = value;
    }
    
    
    public void setPrev(Node prev) {
        this.prev = prev;
    }
    
    public void setNext(Node next) {
        this.next = next;
    }
    
    public void setDown(Node down) {
        this.down = down;
    }
    
    public void setUp(Node up) {
        this.up = up; 
    }
    
    //public void setLevel(int level) {this.level = level;}
    
    public K key() {return key;}
    
    //public int level() {return level;}
    
    public V value() {return value;}
    
    public Node<K,V> prev() {return prev;}
    
    public Node<K,V> next() {return next;}
    
    public Node<K,V> down() {return down;}
    
    public Node<K,V> up() {return up;};
}