package evaluationserver.server.sandbox;

import java.io.File;

public class Solution {

	private final String languageKey;
	private final File program;
	private final File inputData;
	private final File outputData;
	private final File evaluationProgram;
	private final int timeLimit;
	private final int memoryLimit;

	public Solution(String languageKey, File program, File inputData, File outputData, File evaluationProgram, int timeLimit, int memoryLimit) {
		this.languageKey = languageKey;
		this.program = program;
		this.inputData = inputData;
		this.outputData = outputData;
		this.evaluationProgram = evaluationProgram;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
	}

	public File getEvaluationProgram() {
		return evaluationProgram;
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

	public File getOutputData() {
		return outputData;
	}

	public File getProgram() {
		return program;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

}
