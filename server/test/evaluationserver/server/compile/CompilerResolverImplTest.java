package evaluationserver.server.compile;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CompilerResolverImplTest {
	
	private Mockery context;
	
	public CompilerResolverImplTest() {
	}
	
	@Before
	public void setUp() {
		context = new JUnit4Mockery();
	}
	
	protected Map<String, Compiler> prepareCompilers() {
		Map<String, Compiler> compilers = new HashMap<String, Compiler>();
		
		final Compiler compilerCpp = context.mock(Compiler.class, "compilerCpp");
		compilers.put("cpp", compilerCpp);
		
		final Compiler compilerC = context.mock(Compiler.class, "compilerC");
		compilers.put("c", compilerC);
		
		final Compiler compilerJava = context.mock(Compiler.class, "compilerJava");
		compilers.put("java", compilerJava);
		
		return compilers;
	}

	@Test(expected=NoCompilerException.class)
	public void testGetCompilerNoCompiler() throws Exception {
		System.out.println("getCompiler");
		String languageKey = "c";
		CompilerResolverImpl instance = new CompilerResolverImpl(new HashMap<String, Compiler>());
		instance.getCompiler(languageKey);
	}
	
	@Test
	public void testGetCompilerSuccess() throws Exception {
		System.out.println("getCompiler");
		String languageKey = "c";
		Map<String, Compiler> compilers = prepareCompilers();
		CompilerResolverImpl instance = new CompilerResolverImpl(compilers);
		Compiler compiler = instance.getCompiler(languageKey);
		
		Assert.assertEquals(compilers.get("c"), compiler);
	}
	
	@Test(expected=NoCompilerException.class)
	public void testGetCompilerNotFound() throws Exception {
		System.out.println("getCompiler");
		String languageKey = "xyz";
		Map<String, Compiler> compilers = prepareCompilers();
		CompilerResolverImpl instance = new CompilerResolverImpl(compilers);
		Compiler compiler = instance.getCompiler(languageKey);
	}
}
