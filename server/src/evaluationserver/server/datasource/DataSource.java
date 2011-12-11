package evaluationserver.server.datasource;

import evaluationserver.server.entities.Solution;

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
	void setResult(Solution solution, Result result);
}
