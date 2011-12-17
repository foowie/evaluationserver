package evaluationserver.server.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputStreamStringTest {
	
	
	private final String singleLineText = "Onle single line";
	private final String threeLineText = "First line\nSecond line\nThird line";
	
	public InputStreamStringTest() {
	}
	
	@Before
	public void setUp() {
	}

	@Test
	public void testReadAllSingleLineText() throws IOException {
		System.out.println("readAll");
		
		InputStreamString sts = new InputStreamString(this.getStringStream(singleLineText));
		String value = sts.readAll();
		
		Assert.assertEquals(singleLineText, value);
	}
	
	@Test
	public void testReadAllThreeLineText() throws IOException {
		System.out.println("readAll");
		
		InputStreamString sts = new InputStreamString(this.getStringStream(threeLineText));
		String value = sts.readAll();
		
		Assert.assertEquals(threeLineText, value);
	}

	@Test(expected=NullPointerException.class)
	public void testReadAllNull() {
		System.out.println("constructor");
		new InputStreamString(null);
	}
	
	
	private InputStream getStringStream(String text) {
		return new ByteArrayInputStream(text.getBytes());
	}
}
