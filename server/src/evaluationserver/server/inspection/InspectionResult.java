package evaluationserver.server.inspection;

public class InspectionResult {

	private final String resultKey;

	public InspectionResult(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getResultKey() {
		return resultKey;
	}
}
