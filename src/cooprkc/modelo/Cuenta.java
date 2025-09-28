package cooprkc.modelo;

public class Cuenta {
    private final String numeroCuenta;
    protected double saldo;

    public Cuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException("Número de cuenta inválido");
        }
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        saldo += monto;
    }

    public void retirar(double monto) throws cooprkc.transacciones.Excepciones.InsufficientFundsException {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        if (monto > saldo) {
            throw new cooprkc.transacciones.Excepciones.InsufficientFundsException(
                "Saldo insuficiente en la cuenta " + numeroCuenta + " (saldo: " + saldo + ", intento: " + monto + ")"
            );
        }
        saldo -= monto;
    }

    @Override
    public String toString() {
        return "Cuenta{numero='" + numeroCuenta + "', saldo=" + saldo + "}";
    }
}
