# CoopRKC (Consola)
Proyecto Java con menús por consola para ADMIN y SOCIO.

## Linck del video 
https://youtu.be/TzlIsZZp_S8

## Estructura
src/

└── cooprkc/

├── app/ # 📌 Aplicación principal

│ ├── MainApp.java # Clase con el método main

│ └── Cooperativa.java

│

├── modelo/ # 🏦 Modelo de negocio

│ ├── Socio.java

│ ├── Cuenta.java

│ └── CuentaAhorros.java

│

└── transacciones/ # 💳 Gestión de transacciones

├── Transaccion.java

├── Deposito.java

├── Retiro.java

└── Excepciones.java

## Clonar Repositorio 

Clonar Repositorio
```bash
git 
cd 
```

## Compilar y ejecutar (sin IDE)

### Windows (PowerShell)
```powershell
# Estás en C:\Users\Sistemas\Downloads\cooprkc_console
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content sources.txt
javac -d out --% @sources.txt
java -cp out cooprkc.app.MainApp

```

## Qué incluye el menú
- ADMIN:
  1. Registrar socio
  2. Abrir cuenta de AHORROS a socio (pide cédula, número e interés)
  3. Listar NOMBRES de socios (map + forEach)
  4. Mostrar cuentas con saldo > 500.000
  5. Calcular TOTAL dinero (reduce)
  6. Aplicar INTERÉS a todas las cuentas de ahorro
  7. Listar socios y sus cuentas
  0. Volver

- SOCIO:
  1. Listar mis cuentas
  2. Depositar
  3. Retirar  (con manejo de error por saldo insuficiente)
  4. Consultar TOTAL de la cooperativa (reduce)
  0. Volver

## Seed de ejemplo (ya cargado al iniciar)
- Socios: Karen (1001), Nicolás (1002), Valeria (1003)
- Cuentas de ahorro: A-001 (5%), A-002 (4%), A-003 (3.5%)
- Saldos iniciales: 600k, 450k, 520k

# cooprkc_console
