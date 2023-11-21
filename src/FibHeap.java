/**
 *  Implements a fibonaccia heap
 *  last update- 10/31 @ 8:50
 **/
import java.lang.Math;
import java.util.*;


public class FibonacciHeap<T extends Comparable<T>> {
    private FibonacciNode<T> min;
    private int size;

    public FibonacciHeap() {
    }

    /**
     * Check if heap is empty
     **/
    public boolean isEmpty() {
        return min == null;
    }


    /**
     * Make heap empty
     **/
    public void clear() {
        min = null;
        size = 0;
    }

    public void insert(T key) {
        FibonacciNode<T> node = new FibonacciNode<>(key);
        if (min == null) {
            min = node;
        } else {
            // Merge with circular doubly linked root list
            node.next = min.next;
            node.prev = min;
            min.next.prev = node;
            min.next = node;
            if (key.compareTo(min.key) < 0) {
                min = node;
            }
        }
        size++;
    }

    // This should have 4 steps to do.
    // Remove the minimum node from the root list.
    // Add the children of the minimum node to the root list.
    // Consolidate the trees in the heap, so that no two trees have the same degree.
    // Set min to the node with the smallest key in the root list.
    public T removeMin() {
        // This method should remove and return the minimum key
       
        // to ensure the heap properties are maintained.
        // TODO: 
        return null;
    }

    // This is going to be used during decreaseKey().
    // when the new key of a node is less than its parent's key.
    // The node is cut from its parent and added to the root list
    private void cut(FibonacciNode<T> x, FibonacciNode<T> y) {
        // remove x from the child list of y
        removeNode(x);
        y.degree--;

        // add x to the root list of heap
        addNodeToRootList(x);

        x.parent = null;
        x.marked = false;
    }

    // Cut-child process might cascade up to the ancestors if they are marked
    private void cascadingCut(FibonacciNode<T> y) {
        FibonacciNode<T> z = y.parent;
        if (z != null) {
            if (!y.marked) {
                y.marked = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    // Consolidation:
    // performed during the removeMin operation.
    // After removing the minimum node, consolidation combines
    // trees of equal degree until there are no two trees
    // of the same degree.
    private void consolidate() {
        // Calculate the maximum possible degree for any node in the heap.
        int arraySize = ((int) Math.floor(Math.log(size) / Math.log(2))) + 1;

        // Create a table to store nodes by their degree.
        List<FibonacciNode<T>> degreeTable = new ArrayList<>(Collections.nCopies(arraySize, null));

        // Create a list to traverse through all nodes in the root list.
        List<FibonacciNode<T>> toVisit = new ArrayList<>();
        FibonacciNode<T> current = min;

        // Populate the toVisit list with all nodes in the root list.
        if (current != null) {
            do {
                toVisit.add(current);
                current = current.next;
            } while (current != min);
        }

        // Traverse through nodes in the root list.
        for (FibonacciNode<T> node : toVisit) {
            FibonacciNode<T> x = node;
            int degree = x.degree;

            // If there's already a node in the degree table with the same degree as x, merge them.
            while (degreeTable.get(degree) != null) {
                FibonacciNode<T> y = degreeTable.get(degree);

                // Ensure that x always represents the node with smaller key.
                if (x.key.compareTo(y.key) > 0) {
                    FibonacciNode<T> temp = x;
                    x = y;
                    y = temp;
                }

                // If y was the minimum, update the minimum pointer to x.
                if (y == min) {
                    min = x;
                }

                // Link y as a child of x.
                link(y, x);

                // Clear the degree table entry and increase the degree since x has gained a new child.
                degreeTable.set(degree, null);
                degree++;
            }

            // Update the degree table with the node x.
            degreeTable.set(degree, x);
        }

        // Reconstruct the root list from nodes in the degree table.
        min = null;
        for (FibonacciNode<T> node : degreeTable) {
            if (node != null) {
                if (min == null) {
                    min = node;
                } else {
                    addNodeToRootList(node);
                    if (node.key.compareTo(min.key) < 0) {
                        min = node;
                    }
                }
            }
        }
    }

    private void link(FibonacciNode<T> y, FibonacciNode<T> x) {
        // Remove y from the root list.
        removeNode(y);

        // Make y a child of x.
        y.prev = y.next = y;
        y.parent = x;

        // Insert y into the child list of x.
        if (x.child == null) {
            x.child = y;
        } else {
            y.next = x.child;
            y.prev = x.child.prev;
            x.child.prev.next = y;
            x.child.prev = y;
        }

        // Increment the degree of x since it has gained a new child.
        x.degree++;

        // Mark y as not being previously cut from its parent.
        y.marked = false;
    }

    private void addNodeToRootList(FibonacciNode<T> node) {
        // Add node to the root list.
        if (min != null) {
            node.next = min;
            node.prev = min.prev;
            min.prev.next = node;
            min.prev = node;
        } else {
            min = node;
        }
    }

    private void removeNode(FibonacciNode<T> node) {
        // Remove node from a doubly linked list.
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

// above is code in my end, if you need any explanation please contact me.

 
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