public class Test1 {
    //Jiacong Zhou
    public static void testCaseBasicFunctionality() {
        HospitalQueue queue = new HospitalQueue();
        // After each operation, print the heap's state
        queue.addPatient("Alice", 5);
        System.out.println(queue.fibHeap.toString());

        queue.addPatient("Bob", 3);
        System.out.println(queue.fibHeap.toString());

        queue.addPatient("Charlie", 1);
        System.out.println(queue.fibHeap.toString());

        queue.dequeue();
        System.out.println(queue.fibHeap.toString());

//        System.out.println("First patient (should be Charlie): " + queue.firstPatient());
//        queue.dequeue();
//        System.out.println("Next patient (should be Bob): " + queue.firstPatient());
//        queue.dequeue();
//        System.out.println("Next patient (should be Alice): " + queue.firstPatient());
    }

    public static void testCaseDecreasePriority() {
        HospitalQueue queue = new HospitalQueue();
        FibNode2 bob = queue.addPatient("Bob", 3);
        System.out.println(queue.fibHeap.toString());

        queue.decreasePriority(bob, 1);
        System.out.println("After decreasing Bob's priority:");
        System.out.println(queue.fibHeap.toString());

        System.out.println("First patient (should be Bob): " + queue.firstPatient());
        queue.decreasePriority(bob, 1);
        System.out.println("First patient after priority decrease (should be Bob): " + queue.firstPatient());
    }

    public static void testCaseEdgeCases() {
        HospitalQueue queue = new HospitalQueue();
        System.out.println("Queue should be empty (true): " + queue.isEmpty());

        queue.addPatient("Alice", 5);
        System.out.println("Queue should not be empty (false): " + queue.isEmpty());

        queue.dequeue();
        System.out.println("Queue should be empty again (true): " + queue.isEmpty());
    }

    public static void testCaseEmptyQueue() {
        HospitalQueue queue = new HospitalQueue();
        queue.addPatient("Alice", 5);
        queue.addPatient("Bob", 3);
        queue.emptyQueue();
        System.out.println("Queue should be empty after emptying (true): " + queue.isEmpty());
    }

    public static void testCaseLargeNumberOfPatients() {
        HospitalQueue queue = new HospitalQueue();
        for (int i = 1; i <= 100; i++) {
            queue.addPatient("Patient" + i, i);
        }

        System.out.println("First patient (should be Patient1): " + queue.firstPatient());
        for (int i = 1; i <= 100; i++) {
            FibNode2 patient = queue.dequeue();
            System.out.println(patient.name + ": " + patient.priority);
        }
    }


    public static void testCase6() {
        HospitalQueue q = new HospitalQueue();
        q.addPatient("Tim", 2);
        q.addPatient("Jim", 6);
        q.addPatient("Kim", 3);
        // should print Tim
        System.out.println("First patient (should be Tim): " + q.firstPatient());
        q.addPatient("Al", 5);
        q.addPatient("Allen", 7);
        q.addPatient("Timmy", 3);
        q.addPatient("Tommy", 1);
        q.addPatient("Hilfiger", 10);

        // Should print patients in order of priority
        System.out.println("Dequeueing patients in order of priority:");
        while (!q.isEmpty()) {
            FibNode2 patient = q.dequeue();
            System.out.println(patient.name + ": " + patient.priority);
        }
    }

    public static void testCaseConsolidationAndRemoval() {
        HospitalQueue q = new HospitalQueue();
        q.addPatient("Alice", 2);
        q.addPatient("Bob", 6);
        q.addPatient("Charlie", 3);
        q.addPatient("Diana", 1);
        q.addPatient("Ethan", 5);
        q.addPatient("Fiona", 4);

        // Dequeueing the first patient (should be Diana with priority 1)
        System.out.println("First patient being dequeued (should be Diana): " + q.dequeue().name);

        // After removing Diana, the heap will consolidate. Let's dequeue the next patient.
        System.out.println("Second patient being dequeued: " + q.dequeue().name);

        // Continue dequeueing and observe the order of removal
        while (!q.isEmpty()) {
            FibNode2 patient = q.dequeue();
            System.out.println("Dequeueing patient: " + patient.name);
        }
    }

    public static void testCaseAdvancedConsolidation() {
        HospitalQueue q = new HospitalQueue();

        // Inserting patients with varying priorities
        FibNode2 p1 = q.addPatient("Patient1", 1);
        FibNode2 p2 = q.addPatient("Patient2", 2);
        FibNode2 p3 = q.addPatient("Patient3", 3);
        FibNode2 p4 = q.addPatient("Patient4", 4);
        FibNode2 p5 = q.addPatient("Patient5", 5);
        FibNode2 p6 = q.addPatient("Patient6", 6);
        FibNode2 p7 = q.addPatient("Patient7", 7);
        FibNode2 p8 = q.addPatient("Patient8", 8);


        // insert operations ...
        System.out.println("Initial heap:");
        System.out.println(q.fibHeap.toString());
        System.out.println();

        System.out.println("Decrease priority : p3");
        // Decreasing priorities to trigger cascading cuts and force consolidation
        q.decreasePriority(p3, 0);
        q.dequeue(); // Dequeue Patient3 with priority 0
        // ... decrease priority and dequeue operations ...
        System.out.println("Heap after consolidation:");
        System.out.println(q.fibHeap.toString());
        System.out.println();

        System.out.println("Decrease priority :p2");
        q.decreasePriority(p2, 0);
        q.dequeue(); // Dequeue Patient2 with priority 0
        // ... decrease priority and dequeue operations ...
        System.out.println("Heap after consolidation:");
        System.out.println(q.fibHeap.toString());
        System.out.println();

        System.out.println("Decrease priority :p5");
        q.decreasePriority(p5, 0);
        q.dequeue(); // Dequeue Patient5 with priority 0
        // ... decrease priority and dequeue operations ...
        System.out.println("Heap after consolidation:");
        System.out.println(q.fibHeap.toString());
        System.out.println();

        System.out.println("Decrease priority :p7");
        q.decreasePriority(p7, 0);
        q.dequeue(); // Dequeue Patient7 with priority 0
        // ... decrease priority and dequeue operations ...
        System.out.println("Heap after consolidation:");
        System.out.println(q.fibHeap.toString());
        System.out.println();


        // At this point, the heap should have undergone multiple consolidations
        // Continue dequeueing to observe the order of removal
        while (!q.isEmpty()) {
            FibNode2 patient = q.dequeue();
            System.out.println("Dequeueing patient: " + patient.name + " with priority: " + patient.priority);
            // ... decrease priority and dequeue operations ...
            System.out.println("Heap after consolidation:");
            System.out.println(q.fibHeap.toString());
            System.out.println();

        }
    }

}
