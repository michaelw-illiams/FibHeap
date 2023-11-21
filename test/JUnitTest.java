import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JUnitTest {
	
	private static FibHeap2 fh;
	
	@Test
	void testMinimum() {
//		fh = new FibHeap2();
//		assertEquals(null, fh.peek());
//		
//		fh.insert(new FibNode2("n1", 100));
//		fh.insert(new FibNode2("n2", 50));
//		assertEquals(50, fh.peek().priority);
//		
//		fh.insert(new FibNode2("n3", 20));
//		fh.insert(new FibNode2("n4", 50));
//		assertEquals(20, fh.peek().priority);
//		
//		fh.insert(new FibNode2("n5", 300));
//		fh.insert(new FibNode2("n6", 400));
//		assertEquals(20, fh.peek().priority);
//		
//		fh.insert(new FibNode2("n5", 10));
//		fh.insert(new FibNode2("n6", 5));
//		assertEquals(5, fh.peek().priority);
	}
	
	@Test
	void testInsert() {
		fh = new FibHeap2();
		fh.insert(new FibNode2("n1", 1));
		assertEquals(1, fh.size());
		
		fh.insert(new FibNode2("n2", 2));
		fh.insert(new FibNode2("n3", 3));
		fh.insert(new FibNode2("n4", 4));
		fh.insert(new FibNode2("n5", 5));
		fh.insert(new FibNode2("n6", 6));
		fh.insert(new FibNode2("n7", 7));
		assertEquals(7, fh.size());
	}
	
	@Test
	void testRemoveMin() {
		for(int i = 1; i < 5; i++) {
			System.out.println("index = " + i);
			fh.getMin();
			System.out.println(fh.toString());
			assertEquals(i + 1, fh.peek().priority);
		}
	}
}
