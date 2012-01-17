package evaluationserver.server.sandbox;

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
public class SandboxResolverImplTest {
	
	private Mockery context;
	
	public SandboxResolverImplTest() {
	}
	
	@Before
	public void setUp() {
		context = new JUnit4Mockery();
	}
	
	protected Map<String, Sandbox> prepareSandboxes() {
		Map<String, Sandbox> sandboxes = new HashMap<String, Sandbox>();
		
		final Sandbox sandboxCpp = context.mock(Sandbox.class, "compilerCpp");
		sandboxes.put("cpp", sandboxCpp);
		
		final Sandbox sandboxC = context.mock(Sandbox.class, "compilerC");
		sandboxes.put("c", sandboxC);
		
		final Sandbox sandboxJava = context.mock(Sandbox.class, "compilerJava");
		sandboxes.put("java", sandboxJava);
		
		return sandboxes;
	}

	@Test(expected=NoSandboxException.class)
	public void testGetSandboxNoSandbox() throws Exception {
		System.out.println("getSandbox");
		String languageKey = "c";
		SandboxResolverImpl instance = new SandboxResolverImpl(new HashMap<String, Sandbox>());
		instance.getSandbox(languageKey);
	}
	
	@Test
	public void testGetSandboxSuccess() throws Exception {
		System.out.println("getSandbox");
		String languageKey = "c";
		Map<String, Sandbox> sandboxes = prepareSandboxes();
		SandboxResolverImpl instance = new SandboxResolverImpl(sandboxes);
		Sandbox sandbox = instance.getSandbox(languageKey);
		
		Assert.assertEquals(sandboxes.get("c"), sandbox);
	}
	
	@Test(expected=NoSandboxException.class)
	public void testGetSandboxNotFound() throws Exception {
		System.out.println("getSandbox");
		String languageKey = "xyz";
		Map<String, Sandbox> sandboxes = prepareSandboxes();
		SandboxResolverImpl instance = new SandboxResolverImpl(sandboxes);
		Sandbox sandbox = instance.getSandbox(languageKey);
	}
}
