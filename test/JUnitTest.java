package JUnitTest;
import main.FibHeap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JUnitTest {

	
	@Test
	void testMinimum() {
		FibHeap fh = new FibHeap();
		fh.insert(100);
		fh.insert(50);
		assertEquals(fh.minimum(), 50);
		fh.insert(200);
		fh.insert(10);
		assertEquals(fh.minimum(), 10);
		fh.insert(1);
		assertEquals(fh.minimum(), 1);
	}
	
	@Test
	void testInsert() {
		FibHeap fh = new FibHeap();
		fh.insert(5);
		fh.insert(3);
		fh.insert(10);
		fh.insert(1);
		assertEquals(fh.toString(), "5, 10, 1, 3, ");
	}

}
