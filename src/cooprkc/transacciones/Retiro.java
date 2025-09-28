package cooprkc.transacciones;

import cooprkc.modelo.Cuenta;

public class Retiro implements Transaccion {
    private final Cuenta cuenta;
    private final double monto;

    public Retiro(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        try {
            cuenta.retirar(monto);
        } catch (Excepciones.InsufficientFundsException e) {
            System.out.println("[ERROR] Retiro fallido: " + e.getMessage());
        }
    }

    @Override
    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Retiro{cuenta=" + cuenta.getNumeroCuenta() + ", monto=" + monto + "}";
    }
}
