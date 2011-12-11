package evaluationserver.server.inspection;

public interface Inspector {

	InspectionResult execute(Solution solution) throws InspectionException;
	
}
