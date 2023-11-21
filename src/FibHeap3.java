import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FibHeap3 {

    // Internal node class
    public class Node {
        int key;
        Object value; // Change Object to the appropriate type for your values
        Node parent, child, left, right;
        int degree;
        boolean mark;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
            this.parent = this.child = this.left = this.right = null;
            this.degree = 0;
            this.mark = false;
        }
    }

    // Function to iterate through a doubly linked list
    private List<Node> iterate(Node head) {
        List<Node> nodes = new LinkedList<>();
        Node node = head;
        Node stop = head;
        boolean flag = false;

        do {
            if (node == stop && flag) {
                break;
            } else if (node == stop) {
                flag = true;
            }
            nodes.add(node);
            node = node.right;
        } while (true);

        return nodes;
    }

    // Pointer to the head and minimum node in the root list
    private Node rootList, minNode;

    // Maintain total node count in full Fibonacci heap
    private int totalNodes;

    // Return min node in O(1) time
    public Node findMin() {
        return minNode;
    }

    // Extract (delete) the min node from the heap in O(log n) time
    // Amortized cost analysis can be found here (http://bit.ly/1ow1Clm)
    public Node extractMin() {
        Node z = minNode;
        if (z != null) {
            if (z.child != null) {
                // Attach child nodes to the root list
                List<Node> children = iterate(z.child);
                for (Node child : children) {
                    mergeWithRootList(child);
                    child.parent = null;
                }
            }
            removeFromRootList(z);
            // Set new min node in heap
            if (z == z.right) {
                minNode = rootList = null;
            } else {
                minNode = z.right;
                consolidate();
            }
            totalNodes--;
        }
        return z;
    }

    // Insert new node into the unordered root list in O(1) time
    // Returns the node so that it can be used for decreaseKey later
    public Node insert(int key, Object value) {
        Node n = new Node(key, value);
        n.left = n.right = n;
        mergeWithRootList(n);
        if (minNode == null || n.key < minNode.key) {
            minNode = n;
        }
        totalNodes++;
        return n;
    }

    // Modify the key of some node in the heap in O(1) time
    public void decreaseKey(Node x, int k) {
        if (k > x.key) {
            return;
        }
        x.key = k;
        Node y = x.parent;
        if (y != null && x.key < y.key) {
            cut(x, y);
            cascadingCut(y);
        }
        if (x.key < minNode.key) {
            minNode = x;
        }
    }

    // Merge two Fibonacci heaps in O(1) time by concatenating the root lists
    // The root of the new root list becomes equal to the first list, and the second
    // list is simply appended to the end (then the proper min node is determined)
    public FibHeap3 merge(FibHeap3 h2) {
    	FibHeap3 H = new FibHeap3();
        H.rootList = rootList;
        H.minNode = minNode;
        // Fix pointers when merging the two heaps
        Node last = h2.rootList.left;
        h2.rootList.left = H.rootList.left;
        H.rootList.left.right = h2.rootList;
        H.rootList.left = last;
        H.rootList.left.right = H.rootList;
        // Update min node if needed
        if (h2.minNode.key < H.minNode.key) {
            H.minNode = h2.minNode;
        }
        // Update total nodes
        H.totalNodes = totalNodes + h2.totalNodes;
        return H;
    }

    // If a child node becomes smaller than its parent node, we
    // cut this child node off and bring it up to the root list
    private void cut(Node x, Node y) {
        removeFromChildList(y, x);
        y.degree--;
        mergeWithRootList(x);
        x.parent = null;
        x.mark = false;
    }

    // Cascading cut of parent node to obtain good time bounds
    private void cascadingCut(Node y) {
        Node z = y.parent;
        if (z != null) {
            if (!y.mark) {
                y.mark = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    // Combine root nodes of equal degree to consolidate the heap
    // by creating a list of unordered binomial trees
    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(totalNodes) / Math.log(2)) + 1;
        Node[] A = new Node[maxDegree];
        List<Node> nodes = iterate(rootList);
        for (Node x : nodes) {
            int d = x.degree;
            while (A[d] != null) {
                Node y = A[d];
                if (x.key > y.key) {
                    Node temp = x;
                    x = y;
                    y = temp;
                }
                heapLink(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
        }
        // Find new min node - no need to reconstruct new root list below
        // because the root list was iteratively changing as we were moving
        // nodes around in the above loop
        for (Node node : A) {
            if (node != null) {
                if (node.key < minNode.key) {
                    minNode = node;
                }
            }
        }
    }

    // Actual linking of one node to another in the root list
    // while also updating the child linked list
    private void heapLink(Node y, Node x) {
        removeFromRootList(y);
        y.left = y.right = y;
        mergeWithChildList(x, y);
        x.degree++;
        y.parent = x;
        y.mark = false;
    }

    // Merge a node with the doubly linked root list
    private void mergeWithRootList(Node node) {
        if (rootList == null) {
            rootList = node;
        } else {
            node.right = rootList.right;
            node.left = rootList;
            rootList.right.left = node;
            rootList.right = node;
        }
    }

    // Merge a node with the doubly linked child list of a root node
    private void mergeWithChildList(Node parent, Node node) {
        if (parent.child == null) {
            parent.child = node;
        } else {
            node.right = parent.child.right;
            node.left = parent.child;
            parent.child.right.left = node;
            parent.child.right = node;
        }
    }

    // Remove a node from the doubly linked root list
    private void removeFromRootList(Node node) {
        if (node == rootList) {
            rootList = node.right;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }

    // Remove a node from the doubly linked child list
    private void removeFromChildList(Node parent, Node node) {
        if (parent.child == parent.child.right) {
            parent.child = null;
        } else if (parent.child == node) {
            parent.child = node.right;
            node.right.parent = parent;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }
}
