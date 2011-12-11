package evaluationserver.server.inspection;

public class InspectionException extends Exception {

	public InspectionException(Throwable thrwbl) {
		super(thrwbl);
	}

	public InspectionException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public InspectionException(String string) {
		super(string);
	}

	public InspectionException() {
	}
}
