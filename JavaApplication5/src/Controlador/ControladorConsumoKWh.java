/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ModeloConsumoKWh;
import Modelo.ModeloErrores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author ProyectoFinal Grupo6
 */
public class ControladorConsumoKWh {    
   
     // Aquí se guarda el reporte final con los promedios
    private final String archivoSalida = "consumoKWh.txt";
    // Aquí se registran los errores
    private final String archivoLog = "src/auditoria.log";

    //Este método lee el archivo, saca el promedio de consumo por distrito y lo muestra en consola o lo guarda en un archivo de texto.
    public void procesarArchivo(String rutaArchivo, boolean exportar) {
        // Título del reporte
        String resultado = String.format("%-30s | %s\n", "Distrito", "Promedio (kWh)");
        resultado += "-----------------------------------------------\n";

        try (BufferedReader br1 = new BufferedReader(new FileReader(rutaArchivo))) {
            br1.readLine(); // Nos saltamos la primera línea (nombres de columnas)
            String linea;
            String distritosProcesados = ""; // Aquí vamos guardando los distritos que ya hicimos

            // Recorremos el archivo línea por línea
            while ((linea = br1.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 22) {
                    String distrito = partes[12].trim(); // Tomamos el distrito de la columna 13

                    // Verificamos si ya procesamos este distrito
                    if (!distritosProcesados.contains("[" + distrito + "]")) {
                        distritosProcesados += "[" + distrito + "]"; // Marcamos este distrito como ya revisado

                        double suma = 0;    // Para sumar los consumos de este distrito
                        int contador = 0;   // Para contar cuántos usuarios hay en el distrito

                        // Volvemos a abrir el archivo para sumar todos los consumos de este distrito
                        try (BufferedReader br2 = new BufferedReader(new FileReader(rutaArchivo))) {
                            br2.readLine(); // Saltamos otra vez la cabecera
                            String l2;
                            while ((l2 = br2.readLine()) != null) {
                                String[] p2 = l2.split(";");
                                // Si es el mismo distrito, sumamos el consumo
                                if (p2.length > 22 && p2[12].trim().equals(distrito)) {
                                    double consumo = Double.parseDouble(p2[22].replace(",", ".").trim());
                                    suma += consumo;
                                    contador++;
                                }
                            }
                        }

                        // Calculamos el promedio (suma / cantidad)
                        double promedio = (contador > 0) ? suma / contador : 0;
                        ModeloConsumoKWh modelo = new ModeloConsumoKWh(distrito, promedio);
                        resultado += modelo.toString() + "\n"; // Agregamos el resultado de este distrito
                    }
                }
            }

            // Guardamos el reporte o lo mostramos en pantalla
            if (exportar) {
                try (FileWriter fw = new FileWriter(archivoSalida)) {
                    fw.write(resultado);
                }
                System.out.println("Reporte exportado a " + archivoSalida);
            } else {
                System.out.println(resultado);
            }

        } catch (Exception e) {
            // Si pasa algún error, lo registramos en el archivo log
            registrarError("ControladorConsumoKWh", e.getMessage());
            System.out.println("Ocurrió un error al procesar el archivo.");
        }
    }

// Este método guarda los errores en el archivo log para revisar después.
    private void registrarError(String clase, String mensaje) {
        try (FileWriter fw = new FileWriter(archivoLog, true)) {
            fw.write(new ModeloErrores(clase, mensaje).toString() + "\n");
        } catch (IOException ex) {
            System.err.println("Error al registrar auditoría: " + ex.getMessage());
        }
    }
}