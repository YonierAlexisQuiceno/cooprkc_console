package cooprkc.app;

import cooprkc.modelo.Cuenta;
import cooprkc.modelo.CuentaAhorros;
import cooprkc.modelo.Socio;
import cooprkc.transacciones.Excepciones.DuplicateAccountException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Cooperativa coop = new Cooperativa("CoopRKC");

        // === Seed: socios y cuentas para demo directa ===
        seedDemo(coop);

        // === Bucle principal ===
        while (true) {
            System.out.println("\n===== COOPERATIVA " + coop.getNombre() + " =====");
            System.out.println("1) Entrar como ADMIN");
            System.out.println("2) Entrar como SOCIO");
            System.out.println("0) Salir");
            System.out.print("Elige: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1" -> menuAdmin(coop);
                case "2" -> menuSocio(coop);
                case "0" -> { System.out.println("¡Hasta pronto!"); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void seedDemo(Cooperativa coop) {
        Socio s1 = new Socio("Karen Sánchez", "1001");
        Socio s2 = new Socio("Nicolás Otálvaro", "1002");
        Socio s3 = new Socio("Valeria Castaño", "1003");
        coop.registrarSocio(s1);
        coop.registrarSocio(s2);
        coop.registrarSocio(s3);
        try {
            coop.abrirCuentaAhorrosParaSocio("1001", "A-001", 0.05);
            coop.abrirCuentaAhorrosParaSocio("1002", "A-002", 0.04);
            coop.abrirCuentaAhorrosParaSocio("1003", "A-003", 0.035);
        } catch (DuplicateAccountException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        coop.depositar("A-001", 600_000);
        coop.depositar("A-002", 450_000);
        coop.depositar("A-003", 520_000);
    }

    // =================== Menú ADMIN ===================
    private static void menuAdmin(Cooperativa coop) {
        while (true) {
            System.out.println("\n--- ADMIN ---");
            System.out.println("1) Registrar socio");
            System.out.println("2) Abrir cuenta de AHORROS a socio");
            System.out.println("3) Listar NOMBRES de socios (map + forEach)");
            System.out.println("4) Mostrar cuentas con saldo > 500.000");
            System.out.println("5) Calcular TOTAL dinero (reduce)");
            System.out.println("6) Aplicar INTERÉS a todas las cuentas de ahorro");
            System.out.println("7) Listar socios y sus cuentas");
            System.out.println("0) Volver");
            System.out.print("Elige: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1" -> registrarSocio(coop);
                case "2" -> abrirCuentaAhorros(coop);
                case "3" -> {
                    System.out.println("Nombres de socios:");
                    coop.listarNombresSociosMapForEach();
                }
                case "4" -> mostrarCuentasSaldoMayor(coop, 500_000);
                case "5" -> System.out.println("TOTAL en la cooperativa: " + coop.totalSaldosReduce());
                case "6" -> {
                    coop.aplicarInteresATodasLasAhorros();
                    System.out.println("Interés aplicado a todas las cuentas de ahorro.");
                }
                case "7" -> listarSociosYCuentas(coop);
                case "0" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // =================== Menú SOCIO ===================
    private static void menuSocio(Cooperativa coop) {
        System.out.print("Ingrese su cédula: ");
        String cedula = sc.nextLine().trim();
        Optional<Socio> socioOpt = coop.buscarSocioPorCedula(cedula);
        if (socioOpt.isEmpty()) {
            System.out.println("No existe un socio con esa cédula.");
            return;
        }
        Socio socio = socioOpt.get();

        while (true) {
            System.out.println("\n--- SOCIO: " + socio.getNombre() + " (" + socio.getCedula() + ") ---");
            System.out.println("1) Listar mis cuentas");
            System.out.println("2) Depositar");
            System.out.println("3) Retirar");
            System.out.println("4) Consultar total en la cooperativa (reduce)");
            System.out.println("0) Volver");
            System.out.print("Elige: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1" -> listarCuentas(socio);
                case "2" -> operarDeposito(coop, socio);
                case "3" -> operarRetiro(coop, socio);
                case "4" -> System.out.println("TOTAL actual en la cooperativa: " + coop.totalSaldosReduce());
                case "0" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ============== Helpers de ADMIN ==============
    private static void registrarSocio(Cooperativa coop) {
        System.out.print("Nombre del socio: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Cédula del socio: ");
        String cedula = sc.nextLine().trim();
        try {
            coop.registrarSocio(new Socio(nombre, cedula));
            System.out.println("Socio registrado.");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void abrirCuentaAhorros(Cooperativa coop) {
        System.out.print("Cédula del socio: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Número de cuenta: ");
        String num = sc.nextLine().trim();
        System.out.print("Interés (ej 0.05 para 5%): ");
        String sInteres = sc.nextLine().trim();
        try {
            double interes = Double.parseDouble(sInteres);
            coop.abrirCuentaAhorrosParaSocio(cedula, num, interes);
            System.out.println("Cuenta de ahorros abierta.");
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Interés inválido.");
        } catch (DuplicateAccountException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void mostrarCuentasSaldoMayor(Cooperativa coop, double umbral) {
        var lista = coop.cuentasConSaldoMayor(umbral);
        if (lista.isEmpty()) {
            System.out.println("No hay cuentas con saldo mayor a " + umbral);
        } else {
            System.out.println("Cuentas con saldo > " + umbral + ":");
            lista.forEach(c -> System.out.println(" - " + c));
        }
    }

    private static void listarSociosYCuentas(Cooperativa coop) {
        if (coop.listarSocios().isEmpty()) {
            System.out.println("No hay socios.");
            return;
        }
        for (Socio s : coop.listarSocios()) {
            System.out.println("Socio: " + s.getNombre() + " (" + s.getCedula() + ")");
            if (s.getCuentas().isEmpty()) {
                System.out.println("   (sin cuentas)");
            } else {
                for (Cuenta c : s.getCuentas()) {
                    System.out.println("   " + c);
                }
            }
        }
    }

    // ============== Helpers de SOCIO ==============
    private static void listarCuentas(Socio socio) {
        List<Cuenta> cuentas = socio.getCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No tiene cuentas.");
            return;
        }
        for (int i = 0; i < cuentas.size(); i++) {
            System.out.println((i+1) + ") " + cuentas.get(i));
        }
    }

    private static Cuenta elegirCuenta(Socio socio) {
        List<Cuenta> cuentas = socio.getCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No tiene cuentas para operar.");
            return null;
        }
        listarCuentas(socio);
        System.out.print("Elige # de cuenta: ");
        String sIdx = sc.nextLine().trim();
        try {
            int idx = Integer.parseInt(sIdx) - 1;
            if (idx < 0 || idx >= cuentas.size()) {
                System.out.println("Índice inválido.");
                return null;
            }
            return cuentas.get(idx);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }
    }

    private static void operarDeposito(Cooperativa coop, Socio socio) {
        Cuenta c = elegirCuenta(socio);
        if (c == null) return;
        System.out.print("Monto a depositar: ");
        String sMonto = sc.nextLine().trim();
        try {
            double monto = Double.parseDouble(sMonto);
            coop.depositar(c.getNumeroCuenta(), monto);
            System.out.println("Depósito realizado. Nuevo saldo: " + c.getSaldo());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Monto inválido.");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void operarRetiro(Cooperativa coop, Socio socio) {
        Cuenta c = elegirCuenta(socio);
        if (c == null) return;
        System.out.print("Monto a retirar: ");
        String sMonto = sc.nextLine().trim();
        try {
            double monto = Double.parseDouble(sMonto);
            coop.retirar(c.getNumeroCuenta(), monto); // Maneja error de saldo insuficiente
            System.out.println("Operación completada. Saldo actual: " + c.getSaldo());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Monto inválido.");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
