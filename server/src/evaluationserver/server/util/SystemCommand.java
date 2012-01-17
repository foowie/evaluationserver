package evaluationserver.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SystemCommand {

	private final Runtime runtime;
	private String command = null;
	private Process process = null;
	private String output = null;
	private String error = null;

	public SystemCommand(Runtime runtime) {
		this.runtime = runtime;
	}

	public SystemCommand() {
		this(Runtime.getRuntime());
	}

	public int exec(String command, String input) throws IOException, InterruptedException {
		this.command = command;

		this.output = null;
		this.error = null;

		process = runtime.exec(command);
		if (input != null) {
			OutputStream outputStream = process.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.close();
		}
		return process.waitFor();
	}

	public int exec(String command) throws IOException, InterruptedException {
		return exec(command, null);
	}

	public int getReturnCode() {
		if (process == null) {
			throw new IllegalStateException("Execute command before 'getReturnCode' call");
		}
		return process.exitValue();
	}

	public String getOutput() throws IOException {
		if (process == null) {
			throw new IllegalStateException("Execute command before 'getOutput' call");
		}
		if (output == null) {
			InputStream inputStream = process.getInputStream();
			output = new InputStreamString(inputStream).readAll();
			inputStream.close();
		}
		return output;
	}

	public String getError() throws IOException {
		if (process == null) {
			throw new IllegalStateException("Execute command before 'getOutput' call");
		}
		if (error == null) {
			InputStream errorStream = process.getErrorStream();
			error = new InputStreamString(errorStream).readAll();
			errorStream.close();
		}
		return error;
	}

	public String getCommand() {
		return command;
	}
}
