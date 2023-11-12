public class main {

    public static void main(String[] args) {
        FibHeap2 fibHeap = new FibHeap2();
        fibHeap.insert(new FibNode2("A", 10));
        fibHeap.insert(new FibNode2("B", 5));
        fibHeap.insert(new FibNode2("C", 7));
        fibHeap.insert(new FibNode2("hey", 3));
        fibHeap.insert(new FibNode2("h", 80));
        fibHeap.insert(new FibNode2("i", 2));
        fibHeap.insert(new FibNode2("Cj", 1));
        FibNode2 removal = fibHeap.findNode("Cj");
        System.out.println(removal.name);
        fibHeap.removeNode(removal);
        System.out.println(fibHeap.getMin().name);
    }
}