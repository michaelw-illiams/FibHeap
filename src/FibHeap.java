/**
 *  Implements a fibonaccia heap
 *  last update- 10/31 @ 8:50
 **/
 
/* Fibonacci Heap Node **/
class HeapNode {
    FibonacciHeapNode child, left, right, parent;    
    int element;
 
    public FibonacciHeapNode(int element) {
        this.right = this;
        this.left = this;
        this.element = element;
    }    
}
 
class FibHeap {
    private HeapNode root;
    private int count;    
 
    public FibonacciHeap() {
        root = null;
        count = 0;
    }
 
    /** Check if heap is empty **/
    public boolean isEmpty() {
        return root == null;
    }
 
    /** Make heap empty **/ 
    public void clear() {
        root = null;
        count = 0;
    }

    public void insert(int element) {
        FibonacciHeapNode node = new FibonacciHeapNode(element);
        node.element = element;
 
        if (root != null) {
            node.left = root;
            node.right = root.right;
            root.right = node;
            node.right.left = node;
            if (element < root.element) 
                root = node;            
        }
        else 
            root = node;
        count++;
    }   
 
    public String toString() {
        FibonacciHeapNode ptr = root;
        if (ptr == null) {
            return "EMPTY";
        }    
        String temp = "";    
        while (ptr != root && ptr.right != null) {

            temp += (ptr.element +", ");
            ptr = ptr.right;
        }
        temp += ptr.element
        return temp;
    } 
}    