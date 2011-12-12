package evaluationserver.server.inspection;

import java.io.File;

public class Solution {

	private final File solutionData;
	private final File inputData;
	private final File outputData;
	private final File evaluationProgram;

	public Solution(File solutionData, File inputData, File outputData, File evaluationProgram) {
		this.solutionData = solutionData;
		this.inputData = inputData;
		this.outputData = outputData;
		this.evaluationProgram = evaluationProgram;
	}

	public File getEvaluationProgram() {
		return evaluationProgram;
	}

	public File getInputData() {
		return inputData;
	}

	public File getOutputData() {
		return outputData;
	}

	public File getSolutionData() {
		return solutionData;
	}
}
