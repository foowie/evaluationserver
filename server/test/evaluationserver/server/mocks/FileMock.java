package evaluationserver.server.mocks;

import java.io.File;

public class FileMock extends File {
	
	public static final int EXISTS = 1;
	public static final int READABLE = 2;
	public static final int WRITABLE = 4;
	public static final int EXECUTABLE = 8;
	public static final int FILE = 16;
	
	private int mode;
	
	public FileMock(String string) {
		this(string, 0);
	}
	
	public FileMock(String string, int mode) {
		super(string);
		this.mode = mode;
	}

	@Override
	public boolean exists() {
		return getMode(EXISTS);
	}
	
	@Override
	public boolean canRead() {
		return getMode(READABLE);
	}
	
	@Override
	public boolean canWrite() {
		return getMode(WRITABLE);
	}
	
	@Override
	public boolean canExecute() {
		return getMode(EXECUTABLE);
	}
	
	@Override
	public boolean isFile() {
		return getMode(FILE) && exists();
	}
	
	@Override
	public boolean isDirectory() {
		return !getMode(FILE) && exists();
	}
	
	@Override
	public boolean setWritable(boolean bln, boolean bln1) {
		return this.setWritable(bln);
	}

	@Override
	public boolean setWritable(boolean bln) {	
		this.setMode(WRITABLE, bln);
		return true;
	}
	
	@Override
	public boolean setReadable(boolean bln, boolean bln1) {
		return this.setReadable(bln);
	}

	@Override
	public boolean setReadable(boolean bln) {	
		this.setMode(READABLE, bln);
		return true;
	}
	
	@Override
	public boolean setExecutable(boolean bln, boolean bln1) {
		return this.setExecutable(bln);
	}

	@Override
	public boolean setExecutable(boolean bln) {	
		this.setMode(EXECUTABLE, bln);
		return true;
	}
	
	private boolean getMode(int what) {
		return (this.mode & what) > 0;
	}
	private void setMode(int what, boolean state) {
		this.mode = state ? (this.mode & ~what) : (this.mode | what);
	}
}
