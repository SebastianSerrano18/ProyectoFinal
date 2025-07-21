/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ModeloTopConsumo;
import Modelo.ModeloErrores;

import java.io.*;

/**
 *
 * @author ProyectoFinal Grupo6
 */
public class Controlador10Consumo {
    
// Ruta del archivo de salida donde se exportará el resultado si se desea
private final String archivoSalida = "top10consumo.txt";

// Ruta del archivo de log donde se registrarán errores
private final String archivoLog = "src/auditoria.log";

// Método principal que procesa el archivo de datos y genera el Top 10
public void procesarArchivo(String rutaArchivo, boolean exportar) {
    // Cabecera del resultado con formato de tabla
    String resultado = String.format("%-30s | %s\n", "Usuario", "Consumo (kWh)");
    resultado += "--------------------------------------------------------\n";

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        // Se salta la primera línea del archivo (cabecera CSV)
        br.readLine();

        // Arreglos para almacenar los 10 mayores consumos y sus respectivos usuarios
        double[] mayores = new double[10];
        String[] usuarios = new String[10];

        String linea;
        // Se lee línea por línea el archivo
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(";"); // Separamos los campos por ";"

            // Verificamos que haya suficientes columnas (más de 22)
            if (partes.length > 22) {
                String nombre = partes[2].trim(); // Obtenemos el nombre del usuario
                double consumo = Double.parseDouble(partes[22].replace(",", ".").trim()); // Obtenemos y convertimos el consumo a double

                // Insertar el consumo en el arreglo si está entre los 10 mayores
                for (int i = 0; i < 10; i++) {
                    if (consumo > mayores[i]) {
                        // Desplazar los elementos actuales hacia abajo para hacer espacio
                        for (int j = 9; j > i; j--) {
                            mayores[j] = mayores[j - 1];
                            usuarios[j] = usuarios[j - 1];
                        }
                        // Insertar el nuevo valor en su posición correcta
                        mayores[i] = consumo;
                        usuarios[i] = nombre;
                        break; // Salir del bucle una vez insertado
                    }
                }
            }
        }

        // Construir el resultado con los modelos ya ordenados
        for (int i = 0; i < 10; i++) {
            if (usuarios[i] != null) {
                ModeloTopConsumo modelo = new ModeloTopConsumo(usuarios[i], mayores[i]);
                resultado += modelo.toString() + "\n";
            }
        }

        // Si se eligió exportar a archivo
        if (exportar) {
            try (FileWriter fw = new FileWriter(archivoSalida)) {
                fw.write(resultado); // Se escribe el resultado en el archivo plano
            }
            System.out.println("Top 10 exportado a " + archivoSalida);
        } else {
            // Si no, se muestra directamente en pantalla
            System.out.println(resultado);
        }

    } catch (Exception e) {
        // En caso de error, se registra el error en el log y se informa por consola
        registrarError("Controlador10Consumo", e.getMessage());
        System.out.println("Error al procesar el top 10.");
    }
}

// Método privado para registrar errores en el archivo de log
private void registrarError(String clase, String mensaje) {
    try (FileWriter fw = new FileWriter(archivoLog, true)) {
        fw.write(new ModeloErrores(clase, mensaje).toString() + "\n");
    } catch (IOException ex) {
        System.err.println("Error al registrar auditoría: " + ex.getMessage());
    }
}
}