
public class FibNode2 {
    String name;
    int priority;
    int degree;
    FibNode2 parent;
    FibNode2 child;
    FibNode2 next;
    FibNode2 prev;
    boolean marked;

    public FibNode2(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.next = this;
        this.prev = this;
    }
}
