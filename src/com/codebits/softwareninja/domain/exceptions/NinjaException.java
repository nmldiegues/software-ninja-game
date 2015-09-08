package com.codebits.softwareninja.domain.exceptions;

public class NinjaException extends Throwable {
	private String msg = null;

	public NinjaException(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
