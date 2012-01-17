package evaluationserver.server.sandbox;

import java.util.Date;
import evaluationserver.server.execution.Reply;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SandboxImplTest {
	
	private ExecutionResultFactory resultFactory;
	
	public SandboxImplTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		this.resultFactory = new ExecutionResultFactoryImpl();
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of createResult method, of class SandboxImpl.
	 */
	@Test
	public void testCreateResult() throws ExecutionException {
		System.out.println("createResult");
		Date start = new Date();
		SandboxImpl instance = new SandboxImpl("run", this.resultFactory);
		ExecutionResult result = resultFactory.create("AC 153 10021421", start); // code time[ms] memory[byte]
		
		assertEquals(Reply.ACCEPTED, result.getReply());
		assertEquals(153, result.getTime());
		assertEquals(10021421, result.getMemory());
		assertEquals(start, result.getStart());
	}

	/**
	 * Test of prepareCommand method, of class SandboxImpl.
	 */
	@Test
	public void testPrepareCommand() {
		System.out.println("prepareCommand");
		Solution solution = new Solution("c", new File("/program"), new File("/inputdata"), new File("/solutiondata"), 11, 22, 33);
		SandboxImpl instance = new SandboxImpl("sandbox %program% %inputData% %solutionData% log %timeLimit% %memoryLimit% %outputLimit%", resultFactory);
		String expResult = "sandbox /program /inputdata /solutiondata log 11 22 33";
		String result = instance.prepareCommand(solution);
		assertEquals(expResult, result);
	}
}
