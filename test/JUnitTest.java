import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JUnitTest {
	
	private static HospitalQueue hq = new HospitalQueue();
	private static String[] testOne = {"Chandler Bing", "Adam Sandler", "Chris Hensworth", "Tom Hanks",
			"Chris Rock", "Jennifer Anieston", "Will Ferrell", "Sandra Bullock", "Pete Davidson",
			"Daniel Craig", "Jack Black"};
	
	private static String[] testTwo = {"Chandler Bing", "Adam Sandler", "Chris Hensworth", "Tom Hanks",
			"Chris Rock", "Jennifer Anieston", "Will Ferrell", "Sandra Bullock", "Pete Davidson",
			"Daniel Craig", "Jack Black", "Will Smith", "Billie Eilish", "Paul Rudd", "Taylor Swift", 
			"Robert Plant", "Travis Kelce", "James Harden", "Lionel Messi", "Tyreek Hill", "Stan Lee",
			"Kendrick Lamar", "Kanye West", "Lebron James", "Benny Blanco", "Tyler Joseph", "Travis Scott",
			"Katy Perry", "Lady Gaga", "Lana Del Ray", "Emma Stone", "Adele", "John Legend", "Brad Pitt", 
			"Drake", "Leonardo DiCaprio"};
	private static int[] testTwoVals = {20, 10, 50, 13, 103, 4, 8, 86, 61, 410, 666, 300, 9, 55, 72, 1002, 
			887, 634, 104, 523, 98, 100, 63, 96, 8, 220, 999, 3001, 891, 693, 465, 24, 83, 10000, 2021, 919};

	/**
	* Tests the ability to add consecutive values to the queue and also checks if the order is properly maintained
	*/
	@Test
	void testAdd() {
		// One add
		hq.addPatient("Chandler Bing", 1);
		assertEquals("Chandler Bing", hq.firstPatient());
		assertEquals(1, hq.firstPriority());
		
		// Another add
		hq.addPatient("Adam Sandler", 2);
		assertEquals("Chandler Bing", hq.firstPatient());
		assertEquals(1, hq.firstPriority());
		
		// Add node with smaller priority
		hq.addPatient("Chris Rock", 0);
		assertEquals("Chris Rock", hq.firstPatient());
		assertEquals(0, hq.firstPriority());
	}

	/**
	* Tests the ability to empty the queue
	*/
	@Test
	void testEmpty() {
		assertEquals(3, hq.numPatients());
		hq.emptyQueue();
		assertEquals(0, hq.numPatients());
		assertEquals(null, hq.firstPatient());
	}

	/**
	* Tests the ability to get the number of patients
	*/
	@Test
	void testNumPatients() {
		// Get size of queue with 100 patients
		for(int i = 0; i < 100; i++) {
			hq.addPatient("patient", i);
		}
		assertEquals(100, hq.numPatients());
		
		// Get size of empty queue
		hq.emptyQueue();
		assertEquals(0, hq.numPatients());
	}

	/**
	* Tests the ability to dequeue consecutive values to the queue and also checks if the order is properly maintained
	*/
	@Test
	void testDequeue() {
		// Test One
		testOne();
		assertEquals(11, hq.numPatients());
		// checks that dequeued values update properly
		for(int i = 0; i < 10; i++) {
			int j = i + 1;
			assertEquals(testOne[i], hq.dequeue().name);
			assertEquals(testOne[j], hq.peek().name);
		}
		assertEquals("Jack Black", hq.dequeue().name);
		assertEquals(null, hq.peek());
		hq.emptyQueue();
		
		
		// Test Two
		assertEquals(0, hq.numPatients());
		testTwo();
		// insertion sort to initialize the test case, also parses strings
		for (int i = 1; i < testTwoVals.length; ++i) {
			
            int keyVal = testTwoVals[i];
            String keyName = testTwo[i];
            int j = i - 1;
            while (j >= 0 && testTwoVals[j] > keyVal) {
            	testTwo[j + 1] = testTwo[j];
            	testTwoVals[j + 1] = testTwoVals[j];
                j = j - 1;
            }
            testTwoVals[j + 1] = keyVal;
            testTwo[j + 1] = keyName;
        }
		for(int i = 0; i < testTwo.length - 1; i++) {
			int j = i + 1;
			assertEquals(testTwo[i], hq.dequeue().name);
			assertEquals(testTwo[j], hq.peek().name);
		}
		assertEquals("Brad Pitt", hq.dequeue().name);
		assertEquals(null, hq.peek());
		hq.emptyQueue();
		hq.dequeue();
		assertEquals(null, hq.peek());	
	}

	/**
	* Tests the ability to merge to lists together
	*/ 
	@Test
	void testMerge() {
		// Merge two small Hospital Queues
		HospitalQueue hq1 = new HospitalQueue();
		HospitalQueue hq2 = new HospitalQueue();
		hq1.addPatient("Michael Jackson", 10);
		hq1.addPatient("Jo Jonas", 8);
		hq1.addPatient("Anna Kendrick", 16);
		hq2.addPatient("Jim Parsons", 2);
		hq2.addPatient("Damon Alburn", 19);
		hq2.addPatient("Sam Smith", 11);
		hq1.merge(hq2);
		assertEquals(6, hq1.numPatients());
		assertEquals("Jim Parsons", hq1.firstPatient());
		
		// Merge hq1 with test two Hospital Queue
		testTwo();
		hq.merge(hq1);
		assertEquals(42, hq.numPatients());
		assertEquals("Jim Parsons", hq.firstPatient());
		hq.dequeue();
		assertEquals(41, hq.numPatients());
		assertEquals("Jennifer Anieston", hq.firstPatient());
		hq.emptyQueue();
	}

	/**
	* Tests the ability to decrease the priority of a node given a name
	*/ 
	@Test
	void testDecreasePriority() {
		hq.emptyQueue();
		testTwo();
		hq.dequeue();
		hq.decreasePriority("Billie Eilish", 1);
		assertEquals("Billie Eilish", hq.peek().name);
		assertEquals(1, hq.findPriority("Billie Eilish"));
		
		// Priority should stay at 1
		hq.decreasePriority("Billie Eilish", 30);
		assertEquals("Billie Eilish", hq.peek().name);
		assertEquals(1, hq.findPriority("Billie Eilish"));
		hq.decreasePriority("Will Smith", 0);
		assertEquals("Will Smith", hq.peek().name);
		assertEquals(0, hq.findPriority("Will Smith"));
		hq.dequeue();
		hq.dequeue();
		hq.dequeue();
		
		for(int i = 0; i < hq.numPatients(); i++) {
			FibNode2 node = hq.peek();
			hq.decreasePriority(node.name, 1000);
			hq.decreasePriority(node.name, 40);
			hq.decreasePriority(node.name, 20);
			hq.decreasePriority(node.name, 10);
			hq.dequeue();
		}
	}
	/**
	* Tests the ability to check for empty q
	*/ 
	@Test
	void testIsEmpty() {
		hq.emptyQueue();
		assertEquals(true, hq.isEmpty());
	}
	
	/**
	* Constructor for list of names
	*/ 
	void testOne() {
		for(int i = 0; i < testOne.length; i++) {
			hq.addPatient(testOne[i], i + 1);
		}
	}
	/**
	* Constructor for list of names
	*/  
	void testTwo() {
		for(int i = 0; i < testTwo.length; i++) {
			hq.addPatient(testTwo[i], testTwoVals[i]);
		}
	}
}
