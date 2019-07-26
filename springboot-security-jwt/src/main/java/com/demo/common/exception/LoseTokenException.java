package com.demo.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class LoseTokenException extends BadCredentialsException{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoseTokenException(String msg) {
		super(msg);
	}
	
	@Override
	public void printStackTrace() {
		logger.info("bad credentials exception");
	}

}
