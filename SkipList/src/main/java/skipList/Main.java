package skipList;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SkipList<Integer,Integer> list = new SkipList<>();
        list.insert(5, 5);
        list.printList();
        list.printTower();
        list.insert(10, 10);
        list.insert(20,20);
        list.insert(30,30);
        list.insert(15,15);
        list.insert(9,9);
        list.insert(9,9);
        list.printList();
        list.printTower();
        System.out.println(list.height());
        System.out.println(list.size());
        //list.insert(10, 7);
        //list.insert(4, 20);
        //System.out.println(list.get(5));
        //System.out.println(list.get(10));
        //System.out.println(list.get(4));
    }
}
