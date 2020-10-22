
package com.seekers.seekerback.exception;


public class SearchException extends Exception {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *
	 */
	public SearchException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 * 
	 */
	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @param cause
	 * 
	 */
	public SearchException(Throwable cause) {
		super(cause);
	}
}
