package evaluationserver.server.util;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class ThreadLogFormatter extends SimpleFormatter {
	
	@Override
	public synchronized String format(LogRecord lr) {
		return "Thread=" + Thread.currentThread().getName() + " " + super.format(lr);
	}
	
}
