package evaluationserver.server.inspection;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InspectorImplTest {
	
	public InspectorImplTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of prepareCommand method, of class InspectorImpl.
	 */
	@Test
	public void testPrepareCommand() {
		System.out.println("prepareCommand");
		Solution solution = new Solution(new File("/solutionData"), new File("/inputData"), new File("/outputData"), new File("/evalProgram"));
		InspectorImpl instance = new InspectorImpl("resolver -in -out < solution", "in", "out", "resolver", "solution");
		String expResult = "/evalProgram -/inputData -/outputData < /solutionData";
		String result = instance.prepareCommand(solution);
		assertEquals(expResult, result);
	}
}
