package service.exception;

public class InvalidCardActionException extends GameException {

	public InvalidCardActionException(String message) {
		super(message);
	}

	public InvalidCardActionException(String message, Throwable cause) {
		super(message, cause);
	}
}
