/**
 * Clase encargada de la conversión segura de datos y manejo de excepciones.
 * Protege al sistema contra errores de ejecución producidos por datos no numéricos.
 */
public class ProcesadorLecturas {

    /**
     * Intenta convertir una cadena de texto a un valor numérico decimal (double).
     * Implementa manejo de excepciones para evitar fallos de ejecución.
     *
     * @param textoCadena Texto a convertir a número
     * @return El valor numérico si es válido, o Double.NaN si el formato es incorrecto
     */
    public static double convertirANumero(String textoCadena) {
        if (textoCadena == null || textoCadena.trim().isEmpty()) {
            System.err.println("Error de conversión: El campo está vacío.");
            return Double.NaN;
        }

        try {
            return Double.parseDouble(textoCadena.trim());
        } catch (NumberFormatException e) {
            System.err.println("Error de conversión: '" + textoCadena + "' no es un número decimal válido.");
            return Double.NaN;
        }
    }

    /**
     * Convierte de forma segura un arreglo de campos en un objeto LecturaSensor.
     * Captura errores si los valores numéricos de los campos son inválidos.
     *
     * @param campos Arreglo de strings con los datos del sensor
     * @return Un objeto LecturaSensor si los datos se pudieron convertir, o null si ocurrió un error
     */
    public static LecturaSensor crearLecturaSegura(String[] campos) {
        try {
            String id = campos[0];
            String fechaHora = campos[1];

            double temperatura = convertirANumero(campos[2]);
            double humedad = convertirANumero(campos[3]);
            double pm25 = convertirANumero(campos[4]);

            // Si alguno de los valores numéricos falló la conversión (es NaN), retornamos null
            if (Double.isNaN(temperatura) || Double.isNaN(humedad) || Double.isNaN(pm25)) {
                return null;
            }

            return new LecturaSensor(id, fechaHora, temperatura, humedad, pm25);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: El arreglo de campos no contiene la cantidad esperada de elementos.");
            return null;
        }
    }
}