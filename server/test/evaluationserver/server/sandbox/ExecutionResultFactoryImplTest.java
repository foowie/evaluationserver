package evaluationserver.server.sandbox;

import evaluationserver.server.execution.Reply;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExecutionResultFactoryImplTest {
	
	public ExecutionResultFactoryImplTest() {
	}

	@Test(expected=NullPointerException.class)
	public void testCreateNullData() {
		System.out.println("create");
		String data = null;
		Date start = new Date();
		ExecutionResultFactoryImpl instance = new ExecutionResultFactoryImpl();
		ExecutionResult result = instance.create(data, start, "");
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateNullDate() {
		System.out.println("create");
		String data = "AC 153 10021421";
		Date start = null;
		ExecutionResultFactoryImpl instance = new ExecutionResultFactoryImpl();
		ExecutionResult result = instance.create(data, start, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateInvalidInput() {
		System.out.println("create");
		String data = "XY AV GG";
		Date start = new Date();
		ExecutionResultFactoryImpl instance = new ExecutionResultFactoryImpl();
		ExecutionResult result = instance.create(data, start, "");
	}
	
	@Test
	public void testCreate() {
		System.out.println("create");
		String data = "AC 153 10021421";
		Date start = new Date();
		ExecutionResultFactoryImpl instance = new ExecutionResultFactoryImpl();
		ExecutionResult result = instance.create(data, start, "x");
		ExecutionResult expected = new ExecutionResult(Reply.ACCEPTED, start, 153, 10021421, 0, "x");
		assertEquals(expected, result);
	}
	
}
