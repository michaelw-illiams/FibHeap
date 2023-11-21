import java.util.LinkedList;
import java.util.List;

public class FibHeap2 {

    // Pointer to the head and minimum node in the root list
    private FibNode2 rootList, minNode;

    // Maintain total node count in full Fibonacci heap
    private int totalFibNode2s;

    // Return min node in O(1) time
    public FibNode2 peek() {
        return minNode;
    }
    // Extract (delete) the min node from the heap in O(log n) time
    public FibNode2 dequeue() {
        FibNode2 tempNode = minNode;
        if (tempNode != null) {
            if (tempNode.child != null) {
                // Attach child nodes to the root list
                List<FibNode2> children = iterate(tempNode.child);
                for (FibNode2 child : children) {
                    mergeWithRootList(child);
                    child.parent = null;
                }
            }
            removeFromRootList(tempNode);
            // Set new min node in heap
            if (tempNode == tempNode.right) {
                minNode = rootList = null;
            } else {
                minNode = tempNode.right;
                consolidate();
            }
            totalFibNode2s--;
        }
        return tempNode;
    }

    // Insert new node into the unordered root list in O(1) time
    // Returns the node so that it can be used for decreasepriority later
    public FibNode2 insert(String name, int priority) {
        FibNode2 newNode = new FibNode2(name, priority);
        newNode.left = newNode.right = newNode;
        mergeWithRootList(newNode);
        if (minNode == null || newNode.priority < minNode.priority) {
            minNode = newNode;
        }
        totalFibNode2s++;
        return newNode;
    }

    // Modify the priority of some node in the heap in O(1) time
    public void decreasepriority(FibNode2 node, int newPriority) {
        if (newPriority > node.priority) {
            return;
        }
        node.priority = newPriority;
        FibNode2 parent = node.parent;
        if (parent != null && node.priority < parent.priority) {
            cut(node, parent);
            cascadingCut(parent);
        }
        if (node.priority < minNode.priority) {
            minNode = node;
        }
    }

    // Merge two Fibonacci heaps in O(1) time by concatenating the root lists
    // The root of the new root list becomes equal to the first list, and the second
    // list is simply appended to the end (then the proper min node is determined)
    public FibHeap2 merge(FibHeap2 h2) {
        FibHeap2 H = new FibHeap2();
        H.rootList = rootList;
        H.minNode = minNode;
        // Fix pointers when merging the two heaps
        FibNode2 last = h2.rootList.left;
        h2.rootList.left = H.rootList.left;
        H.rootList.left.right = h2.rootList;
        H.rootList.left = last;
        H.rootList.left.right = H.rootList;
        // Update min node if needed
        if (h2.minNode.priority < H.minNode.priority) {
            H.minNode = h2.minNode;
        }
        // Update total nodes
        H.totalFibNode2s = totalFibNode2s + h2.totalFibNode2s;
        return H;
    }

    // Function to iterate through a doubly linked list
    private List<FibNode2> iterate(FibNode2 head) {
        List<FibNode2> nodes = new LinkedList<>();
        FibNode2 node = head;
        FibNode2 stop = head;
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

    // If a child node becomes smaller than its parent node, we
    // cut this child node off and bring it up to the root list
    private void cut(FibNode2 node1, FibNode2 node2) {
        removeFromChildList(node2, node1);
        node2.degree--;
        mergeWithRootList(node1);
        node1.parent = null;
        node1.mark = false;
    }

    // Cascading cut of parent node to obtain good time bounds
    private void cascadingCut(FibNode2 node) {
        FibNode2 parent = node.parent;
        if (parent != null) {
            if (!node.mark) {
                node.mark = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }

    // Combine root nodes of equal degree to consolidate the heap
    // by creating a list of unordered binomial trees
    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(totalFibNode2s) / Math.log(2)) + 1;
        FibNode2[] nodeList = new FibNode2[maxDegree];
        List<FibNode2> nodes = iterate(rootList);
        for (FibNode2 curNode : nodes) {
            int degree = curNode.degree;
            while (nodeList[degree] != null) {
                FibNode2 tempNode = nodeList[degree];
                if (curNode.priority > tempNode.priority) {
                    FibNode2 temp = curNode;
                    curNode = tempNode;
                    tempNode = temp;
                }
                heapLink(tempNode, curNode);
                nodeList[degree] = null;
                degree++;
            }
            nodeList[degree] = curNode;
        }
        // Find new min node - no need to reconstruct new root list below
        // because the root list was iteratively changing as we were moving
        // nodes around in the above loop
        for (FibNode2 node : nodeList) {
            if (node != null) {
                if (node.priority < minNode.priority) {
                    minNode = node;
                }
            }
        }
    }

    // Actual linking of one node to another in the root list
    // while also updating the child linked list
    private void heapLink(FibNode2 node1, FibNode2 node2) {
        removeFromRootList(node1);
        node1.left = node1.right = node1;
        mergeWithChildList(node2, node1);
        node2.degree++;
        node1.parent = node2;
        node1.mark = false;
    }

    // Merge a node with the doubly linked root list
    private void mergeWithRootList(FibNode2 node) {
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
    private void mergeWithChildList(FibNode2 parent, FibNode2 node) {
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
    private void removeFromRootList(FibNode2 node) {
        if (node == rootList) {
            rootList = node.right;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }

    // Remove a node from the doubly linked child list
    private void removeFromChildList(FibNode2 parent, FibNode2 node) {
        if (parent.child == parent.child.right) {
            parent.child = null;
        } else if (parent.child == node) {
            parent.child = node.right;
            node.right.parent = parent;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }
    // toString method to print the heap's structure
    @Override
    public String toString() {
        if (rootList == null) {
            return "Heap is empty";
        }

        StringBuilder sb = new StringBuilder();
        FibNode2 current = rootList;
        do {
            sb.append(printTree(current, 0));
            current = current.right;
            if (current != rootList) {
                sb.append("\n");
            }
        } while (current != rootList);

        return sb.toString();
    }

    // Helper method to print the structure of the tree rooted at a given node
    private String printTree(FibNode2 node, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(" - ".repeat(level))
                .append(String.format("Node{name='%s', priority=%d, degree=%d}", node.name, node.priority, node.degree));

        if (node.child != null) {
            sb.append("\n").append(printTree(node.child, level + 1));
            FibNode2 child = node.child.right;
            while (child != node.child) {
                sb.append("\n").append(printTree(child, level + 1));
                child = child.right;
            }
        }
        return sb.toString();
    }
}
