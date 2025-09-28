package cooprkc.modelo;

public class CuentaAhorros extends Cuenta {
    private final double interes; // Ej: 0.05 => 5%

    public CuentaAhorros(String numeroCuenta, double interes) {
        super(numeroCuenta);
        if (interes < 0) throw new IllegalArgumentException("El interés no puede ser negativo");
        this.interes = interes;
    }

    public double getInteres() {
        return interes;
    }

    public void aplicarInteres() {
        double intereses = getSaldo() * interes;
        if (intereses > 0) {
            depositar(intereses);
        }
    }

    @Override
    public String toString() {
        return "CuentaAhorros{numero='" + getNumeroCuenta() + "', saldo=" + getSaldo() + ", interes=" + (interes*100) + "%}";
    }
}
