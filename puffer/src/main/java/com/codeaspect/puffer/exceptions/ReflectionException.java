package com.codeaspect.puffer.exceptions;

public class ReflectionException extends RuntimeException{

	private static final long serialVersionUID = -90083795655280931L;

	/** @see java.lang.RuntimeException#RuntimeException()
     */
    public ReflectionException() {
        super();
    }

    /** @see java.lang.RuntimeException#RuntimeException(String)
     */
    public ReflectionException(String message) {
        super(message);
    }

    
    /** @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /** @see java.lang.RuntimeException#RuntimeException(Throwable)
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }
	
}
