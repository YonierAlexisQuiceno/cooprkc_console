package cooprkc.transacciones;

import cooprkc.modelo.Cuenta;

public class Deposito implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Deposito(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        cuenta.depositar(monto);
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Deposito{cuenta=" + cuenta.getNumeroCuenta() + ", monto=" + monto + "}";
    }
}
