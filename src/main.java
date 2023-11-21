public class main {
    public static void main(String[] args) {
        System.out.println("Running Test Case 1: Basic Functionality");
        testCaseBasicFunctionality();
        System.out.println();

        System.out.println("Running Test Case 2: Decreasing Priority");
        testCaseDecreasePriority();
        System.out.println();

        System.out.println("Running Test Case 3: Edge Cases");
        testCaseEdgeCases();
        System.out.println();

        System.out.println("Running Test Case 4: Emptying the Queue");
        testCaseEmptyQueue();
        System.out.println();

        System.out.println("Running Test Case 5: Large Number of Patients");
        testCaseLargeNumberOfPatients();
        System.out.println();

        System.out.println("Running Test Case 6: Should print patients in order of priority");
        testCase6();
        System.out.println();
    }
}