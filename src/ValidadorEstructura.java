/**
 * ValidadorEstructura
 *
 * Responsabilidad de Jedreck dentro del proyecto integrador
 * "Red de Sensores IoT" (Semana 1 - Ingesta confiable).
 *
 * Este módulo se encarga ÚNICAMENTE de la VALIDACIÓN DE ESTRUCTURA
 * de un registro crudo proveniente de un sensor, es decir:
 *
 *      1. Separar la línea en campos (por comas).
 *      2. Verificar que la cantidad de campos sea la esperada.
 *      3. Verificar que ningún campo esté vacío o en blanco.
 *
 * IMPORTANTE: este módulo NO convierte datos a número (eso lo hace
 * Juan Pablo en ProcesadorLecturas) y NO valida rangos físicos
 * (eso lo hace Julian en ValidadorDatos). Solo valida que la
 * "forma" del registro sea correcta antes de seguir procesando.
 */
public class ValidadorEstructura {

    // Cantidad de campos que debe tener cada registro de sensor:
    // idEstacion, fechaHora, temperatura, humedad, pm25
    public static final int CANTIDAD_CAMPOS_ESPERADA = 5;

    /**
     * Separa una línea cruda del sensor en sus campos individuales,
     * usando la coma como separador.
     *
     * @param linea línea cruda leída del archivo/CSV, ej:
     *              "EST-001,2026-09-07 08:00,18.5,75.2,32.4"
     * @return arreglo de campos ya separados (aún como texto)
     */
    public static String[] separarCampos(String linea) {

        if (linea == null) {
            return new String[0];
        }

        return linea.split(",", -1);
        // El -1 evita que Java elimine campos vacíos al final,
        // lo cual es importante para poder detectarlos.
    }

    /**
     * Verifica que el registro tenga exactamente la cantidad de
     * campos esperada.
     *
     * @param campos arreglo de campos obtenido con separarCampos()
     * @return true si la cantidad de campos es correcta
     */
    public static boolean tieneNumeroCorrectoDeCampos(String[] campos) {

        if (campos == null) {
            return false;
        }

        return campos.length == CANTIDAD_CAMPOS_ESPERADA;
    }

    /**
     * Verifica que ninguno de los campos recibidos esté vacío
     * o compuesto solo por espacios en blanco.
     *
     * @param campos arreglo de campos ya separados
     * @return true si todos los campos contienen información
     */
    public static boolean tieneCamposVacios(String[] campos) {

        if (campos == null) {
            return true;
        }

        for (String campo : campos) {

            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Valida la ESTRUCTURA completa de un registro: cantidad de
     * campos correcta y ningún campo vacío. Este es el método que
     * el resto del equipo (Juan Felipe, en la integración) debería
     * llamar antes de intentar convertir o validar los datos.
     *
     * @param campos arreglo de campos ya separados
     * @return true si la estructura del registro es válida
     */
    public static boolean esEstructuraValida(String[] campos) {

        if (!tieneNumeroCorrectoDeCampos(campos)) {
            return false;
        }

        if (tieneCamposVacios(campos)) {
            return false;
        }

        return true;
    }

    /**
     * Devuelve un motivo de rechazo legible cuando la estructura
     * NO es válida, o null cuando la estructura es correcta.
     * Pensado para reutilizarse en el reporte de calidad final
     * (Juan Felipe) sin duplicar la lógica de validación.
     *
     * @param campos arreglo de campos ya separados
     * @return motivo del rechazo, o null si la estructura es válida
     */
    public static String obtenerMotivoRechazoEstructura(String[] campos) {

        if (!tieneNumeroCorrectoDeCampos(campos)) {

            int cantidadRecibida =
                    (campos == null) ? 0 : campos.length;

            return "Cantidad incorrecta de campos (se esperaban " +
                    CANTIDAD_CAMPOS_ESPERADA + ", se recibieron " +
                    cantidadRecibida + ")";
        }

        if (tieneCamposVacios(campos)) {
            return "El registro contiene uno o más campos vacíos";
        }

        return null;
    }
}
