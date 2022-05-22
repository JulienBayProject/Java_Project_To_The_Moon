package exception;

public class WalletPlayerNegativeException extends Exception {

	public WalletPlayerNegativeException(int wallet) {
		super("Transaction impossible, you have "+wallet+" available");
	}

	
}
