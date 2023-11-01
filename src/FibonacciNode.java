
public class FibonacciNode<T extends Comparable<T>> {
    T key;
    int degree;
    FibonacciNode<T> parent;
    FibonacciNode<T> child;
    FibonacciNode<T> next;
    FibonacciNode<T> prev;
    boolean marked;

    public FibonacciNode(T key) {
        this.key = key;
        this.next = this;
        this.prev = this;
    }
}
