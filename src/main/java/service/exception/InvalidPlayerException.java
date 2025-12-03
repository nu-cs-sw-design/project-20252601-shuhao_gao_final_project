package service.exception;

public class InvalidPlayerException extends GameException {

	public InvalidPlayerException(String message) {
		super(message);
	}

	public InvalidPlayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
