public class Test1 {
    public static void testCaseBasicFunctionality() {
        HospitalQueue queue = new HospitalQueue();
        queue.addPatient("Alice", 5);
        queue.addPatient("Bob", 3);
        queue.addPatient("Charlie", 1);

        System.out.println("First patient (should be Charlie): " + queue.firstPatient());
        queue.dequeue();
        System.out.println("Next patient (should be Bob): " + queue.firstPatient());
        queue.dequeue();
        System.out.println("Next patient (should be Alice): " + queue.firstPatient());
    }

    public static void testCaseDecreasePriority() {
        HospitalQueue queue = new HospitalQueue();
        queue.addPatient("Alice", 5);
        FibNode2 bob = queue.addPatient("Bob", 3); // Updated to receive the returned FibNode2
        queue.addPatient("Charlie", 4);

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
}
