public class FibNode2 {
    int priority;
    String name; // name of the patient
    FibNode2 parent, child, left, right;
    int degree;
    boolean mark;

    public FibNode2(int priority, String name) {
        this.priority = priority;
        this.name = name;
        this.parent = this.child = this.left = this.right = null;
        this.degree = 0;
        this.mark = false;
    }
}