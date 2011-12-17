package evaluationserver.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamString {

	private final BufferedReader reader;
	
	public InputStreamString(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	public String readAll() throws IOException {
		StringBuilder sb = new StringBuilder();
		char[] buffer = new char[256];
		int length;
		while ((length = reader.read(buffer)) != -1)
			sb.append(buffer, 0, length);
		return sb.toString();
	}
	
}
