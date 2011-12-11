package evaluationserver.server.datasource;

import evaluationserver.entities.Solution;
import evaluationserver.server.sandbox.ExecutionResult;

public interface DataSource {
	
	/**
	 * Load next free solution to test
	 * @return null when no solution is present
	 */
	Solution takeNextUnresolvedSolution();
	
	/**
	 * Update solution with given result
	 * @param solution
	 * @param result 
	 */
	void setResult(Solution solution, ExecutionResult result);
}
