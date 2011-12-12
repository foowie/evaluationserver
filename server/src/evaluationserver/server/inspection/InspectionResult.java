package evaluationserver.server.inspection;

import evaluationserver.server.execution.Reply;

public class InspectionResult {

	private final Reply reply;

	public InspectionResult(Reply reply) {
		this.reply = reply;
	}

	public Reply getReply() {
		return reply;
	}
}
