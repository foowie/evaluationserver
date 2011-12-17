package evaluationserver.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.IllegalStateException;
import junit.framework.Assert;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

@RunWith(JMock.class)
public class SystemCommandTest {
	
	private Mockery context;
	
	private Process process;
	private Runtime runtime;
	
	public SystemCommandTest() {
	}

	@Before
	public void setUp() throws IOException, InterruptedException {
		context = new JUnit4Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};		
		process = context.mock(Process.class);
		runtime = context.mock(Runtime.class);
		context.checking(new Expectations() {{
			allowing(runtime).exec("ls"); will(returnValue(process));
			allowing(process).waitFor(); will(returnValue(9));
			allowing(process).exitValue(); will(returnValue(9));
			atMost(1).of(process).getInputStream(); will(returnValue(new ByteArrayInputStream("Successfully executed".getBytes())));
			atMost(1).of(process).getErrorStream(); will(returnValue(new ByteArrayInputStream("Error messages".getBytes())));
		}});
	}

	@Test
	public void testExecuteCode() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		int code = command.exec("ls");
		Assert.assertEquals(9, code);
	}

	@Test
	public void testExecuteInput() throws IOException, InterruptedException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		context.checking(new Expectations() {{
			oneOf(process).getOutputStream(); will(returnValue(baos));
		}});		
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls", "input text");
		
		Assert.assertEquals("input text", baos.toString());
	}
	
	@Test
	public void testExecuteReturnCode() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls");
		Assert.assertEquals(9, command.getReturnCode());
	}
	
	@Test
	public void testExecuteTwoTimes() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		Assert.assertEquals(9, command.exec("ls"));
		Assert.assertEquals(9, command.exec("ls"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testExecuteReturnCodeBeforeExecute() {
		SystemCommand command = new SystemCommand(runtime);
		command.getReturnCode();
	}
	
	@Test
	public void testExecuteGetOutput() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls");
		Assert.assertEquals("Successfully executed", command.getOutput());
	}	
	
	@Test
	public void testExecuteGetOutputTwoTimes() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls");
		Assert.assertEquals("Successfully executed", command.getOutput());
		Assert.assertEquals("Successfully executed", command.getOutput());
	}	
	
	@Test(expected=IllegalStateException.class)
	public void testExecuteGetOutputBeforeExecute() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.getOutput();
	}	
	
	@Test
	public void testExecuteGetError() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls");
		Assert.assertEquals("Error messages", command.getError());
	}	
	
	@Test
	public void testExecuteGetErrorTwoTimes() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.exec("ls");
		Assert.assertEquals("Error messages", command.getError());
		Assert.assertEquals("Error messages", command.getError());
	}	
	
	@Test(expected=IllegalStateException.class)
	public void testExecuteGetErrorBeforeExecute() throws IOException, InterruptedException {
		SystemCommand command = new SystemCommand(runtime);
		command.getError();
	}	
}
