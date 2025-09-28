package cooprkc.transacciones;

public class Excepciones {
    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class DuplicateAccountException extends Exception {
        public DuplicateAccountException(String message) {
            super(message);
        }
    }
}
