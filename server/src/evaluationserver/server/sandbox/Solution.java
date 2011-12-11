package evaluationserver.server.sandbox;

import java.io.File;

public class Solution {

	private final String languageKey;
	private final File program;
	private final File inputData;
	private final int timeLimit;
	private final int memoryLimit;
	private final int outputLimit;

	public Solution(String languageKey, File program, File inputData, int timeLimit, int memoryLimit, int outputLimit) {
		this.languageKey = languageKey;
		this.program = program;
		this.inputData = inputData;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.outputLimit = outputLimit;
	}

	public File getInputData() {
		return inputData;
	}

	public String getLanguageKey() {
		return languageKey;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public File getProgram() {
		return program;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public int getOutputLimit() {
		return outputLimit;
	}
}
