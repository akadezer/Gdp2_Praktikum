package studiplayer.audio;


public class NotPlayableException extends Exception {
	private String pathname;
	private String msg;
	public NotPlayableException(String pathname, String msg) {
		super(msg);
		this.msg = msg;
		this.pathname = pathname;

	}

	public NotPlayableException(String pathname, Throwable t) {
		
		super(t);
		this.pathname = pathname;
	}

	public NotPlayableException(String pathname, String msg, Throwable t) {
		
		super(msg, t);
		this.pathname = pathname;

	}

	public NotPlayableException() {
		super();
	}

	public String getPathname() {
		return pathname;
	}

	public String getMsg() {
		return msg;
	}
	
	@Override
	public String toString() {
		return msg + super.toString() + pathname;
	}
}
