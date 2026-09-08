/* ============================================================
   RED DE MONITOREO AMBIENTAL URBANO - MODULO DE INGESTA
   Version 0.1 - "funciona en mi maquina"

   Este programa lee el archivo lecturas.csv con los datos
   crudos de las estaciones de sensores y produce un reporte
   de calidad del aire.

   NO MODIFIQUES ESTE ARCHIVO ANTES DE LA FASE 0.
   Primero se predice, despues se ejecuta.
   ============================================================ */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Lee mediciones ambientales desde un archivo CSV y genera un reporte.
 *
 * <p>El programa calcula promedios de temperatura, humedad y PM2.5.
 * Tambien identifica la estacion que tiene la lectura de PM2.5 mas alta.</p>
 */
public class IngestaSensores {

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos recibidos desde la linea de comandos; este
     *             programa no necesita argumentos
     * @throws IOException si ocurre un problema al abrir, leer o cerrar el CSV
     */
    public static void main(String[] args) throws IOException {
        String linea =
                "EST-001,2026-09-07 08:00,18.5,75.2,32.4";

        String[] campos = separarCampos(linea);

        if (!tieneNumeroCorrectoDeCampos(campos)) {
            System.out.println("Registro inválido");
            return;
        }

        LecturaSensor lectura = crearLectura(campos);

        imprimirLectura(lectura);
    }

}
