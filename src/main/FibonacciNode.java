package main;

public class FibonacciNode {
    int key;
    int degree;
    FibonacciNode parent;
    FibonacciNode child;
    FibonacciNode next;
    FibonacciNode prev;
    boolean marked;

    public FibonacciNode(int key) {
        this.key = key;
        this.next = null;
        this.prev = null;
        this.child = null;
        this.parent = null;
    }
}