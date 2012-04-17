package evaluationserver.server.compile;

import org.jmock.States;
import java.io.IOException;
import org.jmock.lib.legacy.ClassImposteriser;
import org.jmock.integration.junit4.JMock;
import org.junit.runner.RunWith;
import evaluationserver.server.mocks.FileMock;
import evaluationserver.server.util.SystemCommand;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import java.io.File;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class SystemCompilerTest {
	
	private Mockery context;
	
	private SystemCommand systemCommand;
	private File source;
	private File destination;
	
	public SystemCompilerTest() {
	}
	
	@Before
	public void setUp() throws IOException, InterruptedException {
		context = new JUnit4Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};		
		systemCommand = context.mock(SystemCommand.class);
		final States execState = context.states("execState").startsAs("-1");
		context.checking(new Expectations() {{
			allowing(systemCommand).exec("run /source /destination"); will(returnValue(0)); then(execState.is("0"));
			allowing(systemCommand).exec("fail /source /destination"); will(returnValue(1)); then(execState.is("1"));
			allowing(systemCommand).getReturnCode(); when(execState.is("0")); will(returnValue(0));
			allowing(systemCommand).getReturnCode(); when(execState.is("1")); will(returnValue(1));
			allowing(systemCommand).getOutput(); will(returnValue("Output"));
			allowing(systemCommand).getError(); will(returnValue("Errors"));
		}});
		source = new FileMock("/source", FileMock.EXISTS | FileMock.READABLE);
		destination = new FileMock("/destination", FileMock.WRITABLE);
	}

	@Test(expected=CompilationException.class)
	public void testCompileSourceNotExists() throws Exception {
		System.out.println("compile");
		
		source = new FileMock("/source");
		
		SystemCompiler instance = new SystemCompiler("run IN OUT", "IN", "OUT");
		instance.compile(source, destination, systemCommand);
	}

//	@Test(expected=CompilationException.class)
//	public void testCompileDestinationNotWritable() throws Exception {
//		System.out.println("compile");
//		
//		destination = new FileMock("/destination");
//		
//		SystemCompiler instance = new SystemCompiler("run IN OUT", "IN", "OUT", systemCommand);
//		instance.compile(source, destination);
//	}
	
	
	@Test
	public void testCompile() throws Exception {
		System.out.println("compile");
		
		context.checking(new Expectations() {{
			oneOf(systemCommand).exec("one /source /destination"); will(returnValue(0));
		}});		
		
		SystemCompiler instance = new SystemCompiler("one IN OUT", "IN", "OUT");
		instance.compile(source, destination, systemCommand);
	}
	
	@Test(expected=CompilationException.class)
	public void testCompileFail() throws Exception {
		System.out.println("compile");
		
		SystemCompiler instance = new SystemCompiler("fail IN OUT", "IN", "OUT");
		instance.compile(source, destination, systemCommand);
	}

	@Test
	public void testFormatCommand() {
		System.out.println("formatCommand");
		SystemCompiler instance = new SystemCompiler("command -%input% -%output%");
		String expResult = "command -/source -/destination";
		String result = instance.formatCommand(source, destination);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testFormatCommandCustomMultipleTags() {
		System.out.println("formatCommand");
		SystemCompiler instance = new SystemCompiler("command -IN -IN -OUT", "IN", "OUT");
		String expResult = "command -/source -/source -/destination";
		String result = instance.formatCommand(source, destination);
		assertEquals(expResult, result);
	}

}
