public static void main(String[] args) {
    PriorityQueue priorityQueue = new PriorityQueue();
    priorityQueue.insert(3, "A");
    priorityQueue.insert(5, "B");
    priorityQueue.insert(2, "C");
    priorityQueue.insert(7, "D");

    System.out.println("Extracted Min: " + priorityQueue.extractMin()); // Output: Extracted Min: C
    priorityQueue.decreaseKey("D", 1);
    System.out.println("Extracted Min: " + priorityQueue.extractMin()); // Output: Extracted Min: D
}