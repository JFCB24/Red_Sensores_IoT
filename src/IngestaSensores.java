import java.io.IOException;

/**
 * Lee mediciones ambientales desde un archivo CSV y genera un reporte.
 * <p>El programa calcula promedios de temperatura, humedad y PM2.5.
 * También identifica la estación que tiene la lectura de PM2.5 más alta.</p>
 */
public class IngestaSensores {
    private static final double TEMPERATURA_MINIMA = -40.0;
    private static final double TEMPERATURA_MAXIMA = 60.0;

    private static final double HUMEDAD_MINIMA = 0.0;
    private static final double HUMEDAD_MAXIMA = 100.0;

    private static final double PM25_MINIMO = 0.0;
    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos recibidos desde la línea de comandos
     * @throws IOException si ocurre un problema al abrir, leer o cerrar el CSV
     */
    public static void main(String[] args) throws IOException {
        String linea = "EST-001,2026-09-07 08:00,18.5,75.2,32.4";

        String[] campos = separarCampos(linea);

        if (!tieneNumeroCorrectoDeCampos(campos)) {
            System.out.println("Registro inválido");
            return;
        }

        // Ahora crearLectura retorne un objeto LecturaSensor correctamente
        LecturaSensor lectura = crearLectura(campos);
        String motivo = obtenerMotivoInvalidez(lectura);

        if (motivo != null) {
            System.out.println("Registro rechazado: " + motivo);
            return;
        }
        imprimirLectura(lectura);
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    public static boolean esTemperaturaValida(double temperatura) {
        return temperatura >= TEMPERATURA_MINIMA &&
                temperatura <= TEMPERATURA_MAXIMA;
    }

    public static boolean esHumedadValida(double humedad) {
        return humedad >= HUMEDAD_MINIMA &&
                humedad <= HUMEDAD_MAXIMA;
    }

    public static boolean esPm25Valido(double pm25) {
        return pm25 >= PM25_MINIMO;
    }

    public static String obtenerMotivoInvalidez(LecturaSensor lectura) {

        if (!esTemperaturaValida(lectura.getTemperatura())) {
            return "Temperatura fuera de rango";
        }

        if (!esHumedadValida(lectura.getHumedad())) {
            return "Humedad fuera de rango";
        }

        if (!esPm25Valido(lectura.getPm25())) {
            return "PM2.5 negativo";
        }

        return null;
    }
    /**
     * Separa una línea de texto CSV separada por comas.
     */
    public static String[] separarCampos(String linea) {
        return linea.split(",");
    }

    /**
     * Valida si el registro tiene exactamente 5 campos.
     */
    public static boolean tieneNumeroCorrectoDeCampos(String[] campos) {
        return campos != null && campos.length == 5;
    }

    /**
     * Parsea los campos de texto y construye un objeto de tipo LecturaSensor.
     */
    public static LecturaSensor crearLectura(String[] campos) {
        String id = campos[0];
        String fechaHora = campos[1];
        double temperatura = Double.parseDouble(campos[2]);
        double humedad = Double.parseDouble(campos[3]);
        double pm25 = Double.parseDouble(campos[4]);

        return new LecturaSensor(id, fechaHora, temperatura, humedad, pm25);
    }

    /**
     * Imprime en consola los datos procesados del objeto LecturaSensor.
     */
    public static void imprimirLectura(LecturaSensor lectura) {
        System.out.println("Estación: " + lectura.getIdEstacion());
        System.out.println("Fecha y Hora: " + lectura.getFechaHora());
        System.out.println("Temperatura: " + lectura.getTemperatura() + " °C");
        System.out.println("Humedad: " + lectura.getHumedad() + " %");
        System.out.println("PM2.5: " + lectura.getPm25() + " µg/m³");
    }
}
