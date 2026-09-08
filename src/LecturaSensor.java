/**
 * Clase que representa el modelo de datos de una lectura de sensor IoT.
 * Sirve como entidad para transportar la información de una estación
 * de forma encapsulada y segura a lo largo del sistema.
 */
public class LecturaSensor {

    // Atributos privados (Encapsulamiento)
    private String idEstacion;
    private String fechaHora;
    private double temperatura;
    private double humedad;
    private double pm25;

    /**
     * Constructor para inicializar una lectura completa de sensor.
     *
     * @param idEstacion  Identificador único de la estación (ej. "EST-001")
     * @param fechaHora   Fecha y hora del registro en formato AAAA-MM-DD HH:MM
     * @param temperatura Valor de la temperatura reportada
     * @param humedad     Porcentaje de humedad relativa reportado
     * @param pm25        Concentración de material particulado PM2.5
     */
    public LecturaSensor(String idEstacion, String fechaHora, double temperatura, double humedad, double pm25) {
        this.idEstacion = idEstacion;
        this.fechaHora = fechaHora;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.pm25 = pm25;
    }

    // Getters para consultar los datos (sin setters, los datos son inmutables tras crearse)

    public String getIdEstacion() {
        return idEstacion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getPm25() {
        return pm25;
    }

    /**
     * Método auxiliar opcional para facilitar la impresión rápida en consola.
     */
    @Override
    public String toString() {
        return "LecturaSensor {" +
                "idEstacion='" + idEstacion + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                ", temperatura=" + temperatura +
                ", humedad=" + humedad +
                ", pm25=" + pm25 +
                '}';
    }
}