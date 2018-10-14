package guardiaomobile.security.util;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String type;
	private int code;
	
	public ErrorMessage(String message, String type, int code) {
		this.message = message;
		this.type = type;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
