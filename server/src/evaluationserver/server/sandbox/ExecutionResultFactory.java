package evaluationserver.server.sandbox;

import java.util.Date;

public interface ExecutionResultFactory {
	ExecutionResult create(String data, Date start) throws IllegalArgumentException;
}
