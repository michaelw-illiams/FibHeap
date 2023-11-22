/**
 * File: FibHeap.java
 * @author Michael Williams, Carson Nannini
 * Purpose: A custom node structure providing the needed attributes for a heap
 */
public class FibNode {
    int priority;
    String name;
    FibNode2 parent, child, left, right;
    int degree;
    boolean mark;

    /**
     * Constructor to initialize FibNode2
     * @param name, name of FibNode2
     * @param priority, priority of FibNode2. 
     */
    public FibNode(String name, int priority) {
        this.priority = priority;
        this.name = name;
        this.parent = null;
        this.child = null;
        this.left = null;
        this.right = null;
        this.degree = 0;
        this.mark = false;
    }
}
