package skipList;

public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SkipList<Integer,Integer> s = new SkipList<>();
        s.remove(100);
	s.put(5,5);
	s.put(6,6);
	s.put(7,7);
	System.out.println(s.put(7,30));
        s.printTower();
        System.out.println();
        s.printList();
        System.out.println(s.get(5));
        System.out.println(s.get(7));
	System.out.println(s.get(100));
        System.out.println(s.size());
    }
}