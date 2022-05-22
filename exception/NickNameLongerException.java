package exception;

public class NickNameLongerException extends Exception {

	public NickNameLongerException(int number) {
		super("The nickname must contain at least "+number+" characters");
	}

	
}
