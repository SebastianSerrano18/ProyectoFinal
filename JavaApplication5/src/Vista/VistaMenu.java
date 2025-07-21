/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controlador10Consumo;
import Controlador.ControladorConsumoKWh;
import Controlador.ControladorConsumoSoles;
import Controlador.ControladorTarifa;
import Modelo.ModeloLogin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class VistaMenu {
    
     // Scanner para leer datos ingresados por el usuario
    private final Scanner lector = new Scanner(System.in);
    // Ruta del archivo CSV con los datos de consumo
    private final String rutaDatosCSV = "src/Datos.csv";

    // Método principal para iniciar sesión e ingresar al sistema
    public void iniciar() {
        System.out.println("----------- INICIO DE SESION ---------");
        int intentos = 0;         // Contador de intentos
        boolean acceso = false;   // Variable para saber si el login fue exitoso

        // Permite 3 intentos de inicio de sesión
        while (intentos < 3 && !acceso) {
            System.out.print("Usuario: ");
            String usuario = lector.nextLine(); // Leer usuario
            System.out.print("Clave: ");
            String clave = lector.nextLine();   // Leer clave

            // Se crea un modelo con las credenciales
            ModeloLogin modelo = new ModeloLogin(usuario, clave);
            acceso = validarCredenciales(modelo); // Validamos credenciales
            intentos++;

            // Si las credenciales no son válidas
            if (!acceso) {
                System.out.println("Usuario o clave incorrectos. Intento número: " + intentos);
                if (intentos == 3) {
                    System.out.println("----------------------");
                    System.out.println("Sesión suspendida.");
                    System.out.println("----------------------");
                }
            }
        }

        // Si el login fue correcto, se muestra el menú principal
        if (acceso) {
            mostrarMenuPrincipal();
        }
    }

    // Valida las credenciales ingresadas contra el archivo usuarios.txt
    private boolean validarCredenciales(ModeloLogin modeloLogin) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String usuario = partes[0].trim();
                    String clave = partes[1].trim();
                    if (usuario.equals(modeloLogin.getUsuario()) && clave.equals(modeloLogin.getClave())) {
                        return true; // Credenciales correctas
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios.");
        }
        return false; // Si no encontró las credenciales
    }

    // Muestra el menú principal del sistema
    private void mostrarMenuPrincipal() {
        int opcion = 0;

        // Menú principal en formato de texto
        String menuPrincipal = """
            
            --------------- MENÚ PRINCIPAL ----------------
            1. Promedio de consumo por distrito (kWh)
            2. Promedio de consumo en soles por distrito
            3. Top 10 usuarios con mayor consumo
            4. Total de consumo por tarifa
            5. Salir
            """;

        // Bucle para mantener el menú activo hasta que se elija "Salir"
        do {
            System.out.println(menuPrincipal);
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(lector.nextLine()); // Leemos opción como número
                switch (opcion) {
                    case 1, 2, 3, 4 -> mostrarSubmenu(opcion); // Llama al submenú
                    case 5 -> System.out.println("Saliendo del programa.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }

        } while (opcion != 5);
    }

    // Muestra el submenú para elegir entre mostrar en pantalla o exportar archivo
    private void mostrarSubmenu(int tipoControlador) {
        int opcionSub;

        String submenu = """
            
            -------- SUBMENÚ --------
            1. Mostrar en pantalla
            2. Exportar a archivo plano
            3. Volver al menú principal
            """;

        do {
            System.out.println(submenu);
            System.out.print("Seleccione una opción: ");

            try {
                opcionSub = Integer.parseInt(lector.nextLine());

                switch (opcionSub) {
                    case 1 -> ejecutarControlador(tipoControlador, false);
                    case 2 -> ejecutarControlador(tipoControlador, true);
                    case 3 -> System.out.println("Regresando al menú principal.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                opcionSub = 0;
            }

        } while (opcionSub != 3);
    }

    // Ejecuta el controlador correspondiente según el tipo de reporte
    private void ejecutarControlador(int tipo, boolean exportar) {
        try {
            switch (tipo) {
                case 1 -> new ControladorConsumoKWh().procesarArchivo(rutaDatosCSV, exportar);
                case 2 -> new ControladorConsumoSoles().procesarArchivo(rutaDatosCSV, exportar);
                case 3 -> new Controlador10Consumo().procesarArchivo(rutaDatosCSV, exportar);
                case 4 -> new ControladorTarifa().procesarArchivo(rutaDatosCSV, exportar);
                default -> System.out.println("Tipo de controlador no válido.");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar el reporte.");
        }
    }
}