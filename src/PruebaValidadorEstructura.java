/**
 * PruebaValidadorEstructura
 *
 * Clase de evidencia/pruebas SOLO para el módulo de Jedreck
 * (ValidadorEstructura). Sirve para demostrar que la separación
 * de campos y la validación de estructura funcionan correctamente
 * antes de integrarlas con el resto del equipo.
 *
 * No reemplaza el Main.java final del proyecto (ese lo hace
 * Juan Felipe al integrar todos los módulos).
 */
public class PruebaValidadorEstructura {

    public static void main(String[] args) {

        String[] registrosDePrueba = {
                "EST-001,2026-09-07 08:00,18.5,75.2,32.4", // válida
                "EST-002,2026-09-07 08:00,19.1,70.0",      // faltan campos
                "EST-003,,-999,65.3,41.2",                 // campo vacío
                "EST-004,2026-09-07 08:00,18.7,150.0,35.8",// estructura ok (rango lo valida Julian)
                "EST-005,2026-09-07 08:00,abc,70.0,30.0"   // estructura ok (conversión la valida Juan Pablo)
        };

        for (String registro : registrosDePrueba) {
            probarRegistro(registro);
        }
    }

    public static void probarRegistro(String linea) {

        System.out.println("----------------------------------------");
        System.out.println("Línea recibida: " + linea);

        String[] campos =
                ValidadorEstructura.separarCampos(linea);

        System.out.println("Cantidad de campos: " + campos.length);

        boolean estructuraValida =
                ValidadorEstructura.esEstructuraValida(campos);

        if (estructuraValida) {

            System.out.println(
                    "Resultado: ESTRUCTURA VÁLIDA, se puede continuar " +
                    "con la conversión y validación de datos."
            );

        } else {

            String motivo =
                    ValidadorEstructura.obtenerMotivoRechazoEstructura(campos);

            System.out.println(
                    "Resultado: ESTRUCTURA INVÁLIDA -> " + motivo
            );
        }
    }
}
