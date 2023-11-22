import java.util.LinkedList;
import java.util.List;

/**
 * File: FibHeap.java
 * @author Michael Williams, Carson Nannini
 * Purpose: The purpose of this file is to use a circular linked list in 
 * order to produce a standard Fibonacci Heap implementation. 
 */
public class FibHeap {
    
    private FibNode rootList, minNode;
    private int totalFibNodes; 

   /**
    * Return the minimum node, O(1). 
    * @return, minimum node (FibNode)
    */
    public FibNode peek() {
        return minNode;
    }
    
    /**
     * Extract the minimum node from the Fibonacci Heap and consolidate the new 
     * heap, O(log n).
     * @return, the minimum node that was removed, (FibNode). 
     */
    public FibNode dequeue() {
        FibNode temp = minNode;
        if (temp != null) {
            if (temp.child != null) {
                List<FibNode> children = loop(temp.child);
                for (FibNode child : children) {
                    mergeRoot(child);
                    child.parent = null;
                }
            }
            removeRoot(temp);
            if (temp == temp.right) {
                minNode = rootList = null;
            } else {
                minNode = temp.right;
                consolidate();
            }
            totalFibNodes--;
        }
        return temp;
    }
    
    /**
     * Insert a node into the the root list of the Fibonacci Heap, O(1). 
     * @param name, Name of new node being inserted, (String). 
     * @param priority, Priority of new node being inserted, (int). 
     * @return, new node being inserted, (FibNode). 
     */
    public FibNode insert(String name, int priority) {
        FibNode newNode = new FibNode(name, priority);
        newNode.left = newNode.right = newNode;
        mergeRoot(newNode);
        if (minNode == null || newNode.priority < minNode.priority) {
            minNode = newNode;
        }
        totalFibNodes++;
        return newNode;
    }

    /**
     * Decrease the priority of a specified node in the Fibonacci Heap, O(1). 
     * @param node, node being modified, (FibNode).
     * @param newPriority, new priority of node, (int).
     */
    public void decreasePriority(String name, int newPriority) {
    	FibNode node = this.findNode(name, minNode);
        if (newPriority > node.priority) {
            return;
        }
        node.priority = newPriority;
        FibNode parent = node.parent;
        if (parent != null && node.priority < parent.priority) {
            cut(node, parent);
            cut(parent);
        }
        if (node.priority < minNode.priority) {
            minNode = node;
        }
    }

    /**
     * Merge the current Fibonacci Heap with another (heap) by joining the 
     * root lists together. The root list of heap is appended to the end of 
     * the current Fibonacci Heap's root list, O(1). 
     * @param heap, Fibonacci Heap being merged, (FibHeap). 
     * @return, new merged Fibonacci Heap, (FibHeap). 
     */
    public FibHeap merge(FibHeap heap) {
    	FibHeap newHeap = new FibHeap();
        newHeap.rootList = rootList;
        newHeap.minNode = minNode;
        FibNode last = heap.rootList.left;
        heap.rootList.left = newHeap.rootList.left;
        newHeap.rootList.left.right = heap.rootList;
        newHeap.rootList.left = last;
        newHeap.rootList.left.right = newHeap.rootList;
        if (heap.minNode.priority < newHeap.minNode.priority) {
            newHeap.minNode = heap.minNode;
        }
        newHeap.totalFibNodes = totalFibNodes + heap.totalFibNodes;
        return newHeap;
    }

    /**
     * loop through the root list of the Fibonacci Heap. 
     * @param head, Head/Root of Fibonacci Heap, (FibNode).
     * @return, List of root list in the Fibonacci Heap, (List<FibNodes2>).
     */
    private List<FibNode> loop(FibNode head) {
        List<FibNode> nodes = new LinkedList<>();
        FibNode node = head;
        FibNode stop = head;
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
    
    public FibNode findNode(String name, FibNode start) {
        List<FibNode> nodes = loop(start);
        
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals(name)) {
                FibNode foundNode = nodes.get(i);
                return foundNode;
            }
            if (nodes.get(i).child != null) {
                FibNode foundInChild = findNode(name, nodes.get(i).child);
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
     * @param node1, node being moved to root list, (FibNode). 
     * @param node2, node being moved to child list, (FibNode). 
     */
    private void cut(FibNode node1, FibNode node2) {
        removeChild(node2, node1);
        node2.degree--;
        mergeRoot(node1);
        node1.parent = null;
        node1.mark = false;
    }

    /**
     * Cuts the parent node
     * @param node node to be cut
     * does not return anything, does cutting in place
     */
    private void cut(FibNode node) {
        FibNode parent = node.parent;
        if (parent != null) {
            if (!node.mark) {
                node.mark = true;
            } else {
                cut(node, parent);
                cut(parent);
            }
        }
    }

    /**
     * Merge root nodes with the same degrees after extracting the minimum node.
     * Find new minimum node. 
     */
    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(totalFibNodes) / Math.log(2)) + 1;
        FibNode[] nodeList = new FibNode[maxDegree];
        List<FibNode> nodes = loop(rootList);
        for (FibNode curNode : nodes) {
            int degree = curNode.degree;
            while (nodeList[degree] != null) {
                FibNode tempNode = nodeList[degree];
                if (curNode.priority > tempNode.priority) {
                    FibNode temp = curNode;
                    curNode = tempNode;
                    tempNode = temp;
                }
                link(tempNode, curNode);
                nodeList[degree] = null;
                degree++;
            }
            nodeList[degree] = curNode;
        }
        for (FibNode node : nodeList) {
            if (node != null) {
                if (node.priority < minNode.priority) {
                    minNode = node;
                }
            }
        }
    }

    /**
     * Link a node to the root list and remove from child list. 
     * @param node1, node being added to root list, (FibNode). 
     * @param node2, node being moved to child list, (FibNode).
     */
    private void link(FibNode node1, FibNode node2) {
        removeRoot(node1);
        node1.left = node1.right = node1;
        mergeChild(node2, node1);
        node2.degree++;
        node1.parent = node2;
        node1.mark = false;
    }

    /**
     * Merge node with the root list, O(1).
     * @param node, node being merged, (FibNode). 
     */
    private void mergeRoot(FibNode node) {
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
     * @param parent, parent node of child list, (FibNode).
     * @param node, node being added, (FibNode).
     */
    private void mergeChild(FibNode parent, FibNode node) {
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
     * @param node, node being removed, (FibNode).
     */
    private void removeRoot(FibNode node) {
        if (node == rootList) {
            rootList = node.right;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }
    
    /**
     * Remove child node from a specified parent
     * @param parent, parent of child (FibNode)
     * @param node, node being removed, (FibNode)
     */
    private void removeChild(FibNode parent, FibNode node) {
        if (parent.child == parent.child.right) {
            parent.child = null;
        } else if (parent.child == node) {
            parent.child = node.right;
            node.right.parent = parent;
        }
        node.left.right = node.right;
        node.right.left = node.left;
    }

    /**
     * Turn root list into string (for testing).
     */
    public String toString() {
    	String retval = "Root List: [";
    	List<FibNode> roots = loop(minNode);
    	for(int i = 0; i < roots.size(); i++) {
    		if(i != roots.size() - 1) {
    			retval += roots.get(i).priority + ", ";
    		}
    		else {
    			retval += roots.get(i).priority + "]";
    		}
    	}
    	return retval;	
    }
}
