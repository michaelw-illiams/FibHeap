import java.util.LinkedList;
import java.util.List;

/**
 * File: FibHeap2.java
 * @author Michael Williams, Carson Nannini
 * Purpose: The purpose of this file is to use a circular linked list in 
 * order to produce a standard Fibonacci Heap implementation. 
 */
public class FibHeap {
    
    private FibNode2 rootList, minNode;
    private int totalFibNode2s; 

   /**
    * Return the minimum node, O(1). 
    * @return, minimum node (FibNode2)
    */
    public FibNode2 peek() {
        return minNode;
    }
    
    /**
     * Extract the minimum node from the Fibonacci Heap and consolidate the new 
     * heap, O(log n).
     * @return, the minimum node that was removed, (FibNode2). 
     */
    public FibNode2 dequeue() {
        FibNode2 temp = minNode;
        if (temp != null) {
            if (temp.child != null) {
                // Attach child nodes to the root list
                List<FibNode2> children = iterate(temp.child);
                for (FibNode2 child : children) {
                    mergeWithRootList(child);
                    child.parent = null;
                }
            }
            removeFromRootList(temp);
            // Set new min node in heap
            if (temp == temp.right) {
                minNode = rootList = null;
            } else {
                minNode = temp.right;
                consolidate();
            }
            totalFibNode2s--;
        }
        return temp;
    }
    
    /**
     * Insert a node into the the root list of the Fibonacci Heap, O(1). 
     * @param name, Name of new node being inserted, (String). 
     * @param priority, Priority of new node being inserted, (int). 
     * @return, new node being inserted, (FibNode2). 
     */
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

    /**
     * Decrease the priority of a specified node in the Fibonacci Heap, O(1). 
     * @param node, node being modified, (FibNode2).
     * @param newPriority, new priority of node, (int).
     */
    public void decreasePriority(String name, int newPriority) {
    	FibNode2 node = this.findNode(name, minNode);
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

    /**
     * Merge the current Fibonacci Heap with another (h2) by joining the 
     * root lists together. The root list of h2 is appended to the end of 
     * the current Fibonacci Heap's root list, O(1). 
     * @param h2, Fibonacci Heap being merged, (FibHeap2). 
     * @return, new merged Fibonacci Heap, (FibHeap2). 
     */
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

    /**
     * Iterate through the root list of the Fibonacci Heap. 
     * @param head, Head/Root of Fibonacci Heap, (FibNode2).
     * @return, List of root list in the Fibonacci Heap, (List<FibNodes2>).
     */
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
    
    public FibNode2 findNode(String name, FibNode2 start) {
        List<FibNode2> nodes = iterate(start);
        
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals(name)) {
                FibNode2 foundNode = nodes.get(i);
                return foundNode;
            }
            if (nodes.get(i).child != null) {
                FibNode2 foundInChild = findNode(name, nodes.get(i).child);
                if (foundInChild != null) {
                    return foundInChild;
                }
            }
        }
        return null;
    }


    
    /**
     * Cut the child node and merge it with the root list if it is smaller than its
     * parent node to keep the minimums in the root list, O(1). 
     * @param node1, node being moved to root list, (FibNode2). 
     * @param node2, node being moved to child list, (FibNode2). 
     */
    private void cut(FibNode2 node1, FibNode2 node2) {
        removeFromChildList(node2, node1);
        node2.degree--;
        mergeWithRootList(node1);
        node1.parent = null;
        node1.mark = false;
    }

    /**
     * Cuts the parent node
     * @param node node to be cut
     * does not return anything, does cutting in place
     */
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

    /**
     * Merge root nodes with the same degrees after extracting the minimum node.
     * Find new minimum node. 
     */
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
        for (FibNode2 node : nodeList) {
            if (node != null) {
                if (node.priority < minNode.priority) {
                    minNode = node;
                }
            }
        }
    }

    /**
     * Link a node to the root list and remove from child list. 
     * @param node1, node being added to root list, (FibNode2). 
     * @param node2, node being moved to child list, (FibNode2).
     */
    private void heapLink(FibNode2 node1, FibNode2 node2) {
        removeFromRootList(node1);
        node1.left = node1.right = node1;
        mergeWithChildList(node2, node1);
        node2.degree++;
        node1.parent = node2;
        node1.mark = false;
    }

    /**
     * Merge node with the root list, O(1).
     * @param node, node being merged, (FibNode2). 
     */
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

    /**
     * Merge node with a specified child list, O(1). 
     * @param parent, parent node of child list, (FibNode2).
     * @param node, node being added, (FibNode2).
     */
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

    /**
     * Remove a node from the root list, O(1).
     * @param node, node being removed, (FibNode2).
     */
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
}
