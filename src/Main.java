
public class Main {
	public static void main(String[] args) {

		/*
		 * Test for loop at 1000000 iterations:
		 * 
		 * Simplexer took: 8215ms REGEX | Simplexer took: 22666ms.
		 * 
		 * Recompiling REGEX Patterns takes its toll.
		 */

		/* One simple test, not a test class - use JUnit Later */
		final int TESTING_FACTOR = 1000000;

		System.out.println("Testing Simplexer...");

		long start1 = System.currentTimeMillis();
		for (int i = 0; i < TESTING_FACTOR; i++) {
			Simplexer s = new Simplexer("if (x = 5) y = x / 5 + 1 else if(x = false) y = 0 else return y");
			Token t;
			while (s.hasNext()) {
				t = s.next();
				// System.out.println("TOKEN: " + t.getText() + ", " + t.getType());
			}
		}
		long end1 = System.currentTimeMillis();

		System.out.println("Testing REGEX Simplexer...\n");

		long start2 = System.currentTimeMillis();
		for (int i = 0; i < TESTING_FACTOR; i++) {
			RegexSimplexer rs = new RegexSimplexer("if (x = 5) y = x / 5 + 1 else if(x = false) y = 0 else return y");
			Token t;
			while (rs.hasNext()) {
				t = rs.next();
				// System.out.println("TOKEN: " + t.getText() + ", " + t.getType());
			}
		}
		long end2 = System.currentTimeMillis();

		System.out.println("\nSimplexer took: " + (end1 - start1) + "ms.");
		System.out.println("REGEX Simplexer took: " + (end2 - start2) + "ms.");

	}
}
