package com.codeaspect.puffer.exceptions;

public class PufferException extends RuntimeException{

	private static final long serialVersionUID = -2117360525482033925L;

	/** @see java.lang.RuntimeException#RuntimeException()
     */
    public PufferException() {
        super();
    }

    /** @see java.lang.RuntimeException#RuntimeException(String)
     */
    public PufferException(String message) {
        super(message);
    }

    
    /** @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public PufferException(String message, Throwable cause) {
        super(message, cause);
    }

    /** @see java.lang.RuntimeException#RuntimeException(Throwable)
     */
    public PufferException(Throwable cause) {
        super(cause);
    }
	
}
