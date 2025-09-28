package cooprkc.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Socio {
    private final String nombre;
    private final String cedula;
    private final List<Cuenta> cuentas;

    public Socio(String nombre, String cedula) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        if (cedula == null || cedula.isBlank()) throw new IllegalArgumentException("Cédula inválida");
        this.nombre = nombre;
        this.cedula = cedula;
        this.cuentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void abrirCuenta(Cuenta cuenta) {
        if (cuenta == null) throw new IllegalArgumentException("Cuenta inválida");
        this.cuentas.add(cuenta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socio socio = (Socio) o;
        return Objects.equals(cedula, socio.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return "Socio{nombre='" + nombre + "', cedula='" + cedula + "', cuentas=" + cuentas.size() + "}";
    }
}
