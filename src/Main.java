public class Main{

    public static void main(String[] args) {
        System.out.println("Running Test Case 1: Basic Functionality");
        Test1.testCaseBasicFunctionality();
        System.out.println();

        System.out.println("Running Test Case 2: Decreasing Priority");
        Test1.testCaseDecreasePriority();
        System.out.println();

        System.out.println("Running Test Case 3: Edge Cases");
        Test1.testCaseEdgeCases();
        System.out.println();

        System.out.println("Running Test Case 4: Emptying the Queue");
        Test1.testCaseEmptyQueue();
        System.out.println();

        System.out.println("Running Test Case 5: Large Number of Patients");
        Test1.testCaseLargeNumberOfPatients();
        System.out.println();

        System.out.println("Running Test Case 6: Should print patients in order of priority");
        Test1.testCase6();
        System.out.println();
    }
}