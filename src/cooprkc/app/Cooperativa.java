package cooprkc.app;

import cooprkc.modelo.Cuenta;
import cooprkc.modelo.CuentaAhorros;
import cooprkc.modelo.Socio;
import cooprkc.transacciones.Excepciones.DuplicateAccountException;
import cooprkc.transacciones.Excepciones.InsufficientFundsException;

import java.util.*;
import java.util.stream.Collectors;

public class Cooperativa {
    private final String nombre;
    private final List<Socio> socios;

    public Cooperativa(String nombre) {
        this.nombre = nombre;
        this.socios = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void registrarSocio(Socio socio) {
        socios.add(socio);
    }

    public Optional<Socio> buscarSocioPorCedula(String cedula) {
        return socios.stream().filter(s -> s.getCedula().equals(cedula)).findFirst();
    }

    public List<Socio> listarSocios() {
        return socios;
    }

    public boolean existeNumeroCuenta(String numeroCuenta) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    public void abrirCuentaAhorrosParaSocio(String cedula, String numCuenta, double interes) throws DuplicateAccountException {
        if (existeNumeroCuenta(numCuenta)) {
            throw new DuplicateAccountException("Número de cuenta repetido: " + numCuenta);
        }
        Socio socio = buscarSocioPorCedula(cedula).orElseGet(() -> {
            Socio nuevo = new Socio("Nuevo Socio", cedula);
            registrarSocio(nuevo);
            return nuevo;
        });
        socio.abrirCuenta(new CuentaAhorros(numCuenta, interes));
    }

    public void depositar(String numCuenta, double monto) {
        Optional<Cuenta> c = obtenerCuenta(numCuenta);
        c.ifPresent(cuenta -> cuenta.depositar(monto));
    }

    public void retirar(String numCuenta, double monto) {
        Optional<Cuenta> c = obtenerCuenta(numCuenta);
        if (c.isPresent()) {
            try {
                c.get().retirar(monto);
            } catch (InsufficientFundsException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        } else {
            System.out.println("[WARN] No existe la cuenta " + numCuenta);
        }
    }

    public Optional<Cuenta> obtenerCuenta(String numCuenta) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(c -> c.getNumeroCuenta().equals(numCuenta))
                .findFirst();
    }

    public double totalSaldosReduce() {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .map(Cuenta::getSaldo)
                .reduce(0.0, Double::sum);
    }

    public List<Cuenta> cuentasConSaldoMayor(double umbral) {
        return socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(c -> c.getSaldo() > umbral)
                .collect(Collectors.toList());
    }

    public void aplicarInteresATodasLasAhorros() {
        socios.stream()
                .flatMap(s -> s.getCuentas().stream())
                .filter(c -> c instanceof CuentaAhorros)
                .map(c -> (CuentaAhorros) c)
                .forEach(CuentaAhorros::aplicarInteres);
    }

    public void listarNombresSociosMapForEach() {
        socios.stream()
                .map(Socio::getNombre)
                .forEach(n -> System.out.println(" - " + n));
    }
}
