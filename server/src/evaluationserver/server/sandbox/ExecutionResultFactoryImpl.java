package evaluationserver.server.sandbox;

import evaluationserver.server.execution.Reply;
import java.util.Date;
import java.util.StringTokenizer;

public class ExecutionResultFactoryImpl implements ExecutionResultFactory {

	@Override
	public ExecutionResult create(String data, Date start, String log) throws IllegalArgumentException {
		if(data == null || start == null)
			throw new NullPointerException("Inpurt parameters can't be null !");
		
		
		StringTokenizer st = new StringTokenizer(data);

		// reply
		final Reply reply;
		if (!st.hasMoreTokens()) {
			throw new IllegalArgumentException("Missing system reply token in sandbox result + '" + data + "'");
		}
		try {
			reply = Reply.fromCode(st.nextToken());
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Invalid system reply in sandbox result + '" + data + "'");
		}

		// time
		if (!st.hasMoreTokens()) {
			throw new IllegalArgumentException("Missing time token in sandbox result + '" + data + "'");
		}
		int time = Integer.parseInt(st.nextToken());

		// memory
		if (!st.hasMoreTokens()) {
			throw new IllegalArgumentException("Missing memory token in sandbox result + '" + data + "'");
		}
		int memory = Integer.parseInt(st.nextToken());

		return new ExecutionResult(reply, start, time, memory, 0, log);
	}
	
}
