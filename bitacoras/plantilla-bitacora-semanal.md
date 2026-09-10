# Bitácora de Equipo - Semana 02

## 1. Datos de la actividad

- **Equipo:** Red_Sensores_IoT
- **Semana:** 02
- **Fecha del laboratorio / taller:** 2026-09-07
- **Tema principal:** Modelado POO, validación de estructura, conversión segura con excepciones y validación de dominio.
- **Pregunta de la semana:** ¿Cómo estructurar una tubería de ingesta modular que garantice la integridad de los datos de sensores ambientales sin que el programa colapse?

---

## 2. Predicción antes de ejecutar

1. **¿Qué creo que va a ocurrir?**  
   El programa recibirá líneas CSV, filtrará registros incompletos, capturará datos corruptos con `try/catch` y descartará lecturas fuera de rango físico, procesando de forma segura solo los datos correctos.

2. **¿Qué parte del algoritmo puede fallar?**  
   La conversión numérica (`Double.parseDouble`) ante cadenas alfabéticas o vacías, y accesos a arreglos con cantidad de campos incorrecta.

3. **¿Cómo comprobaremos la predicción?**  
   Pasando registros válidos, registros con campos faltantes, registros con letras (ej. `"abc"`) y registros con temperaturas fuera de rango (-40°C a 60°C).

---

## 3. Evidencia del laboratorio

### Resultado observado
La canalización integrada procesó correctamente las líneas válidas e identificó las defectuosas:
- **Línea válida:** Se convirtió a un objeto `LecturaSensor` e imprimió sus datos.
- **Línea con texto (`"abc"`):** El `ProcesadorLecturas` atrapó `NumberFormatException`, emitió una alerta en `System.err` y retornó `null`, rechazando el registro de forma segura.
- **Línea fuera de rango:** Retornó `"Temperatura fuera de rango"` mediante la validación semántica de dominio.

### Diferencia entre la predicción y el resultado
El flujo funcionó según lo previsto. La única discrepancia inicial fue un error sintáctico (`@` suelto) en `ProcesadorLecturas.java` que se corrigió al borrar el carácter.

### Error o comportamiento inesperado
- **¿Qué ocurrió?** Se produjeron errores de tipo de retorno en `crearLectura` y alertas en consola por `System.err`.
- **¿Por qué ocurrió?** La firma inicial no devolvía `LecturaSensor` y `System.err` imprime en rojo por defecto en IntelliJ.
- **¿Cómo se corrigió?** Se ajustó el tipo de retorno a `LecturaSensor` y se confirmó que la salida roja era el comportamiento esperado para la captura de excepciones.

---

## 4. Explicación en lenguaje llano

El sistema funciona como una aduana de aeropuerto. Primero revisan si traes el pasaporte (estructura de 5 campos); luego verifican que tus documentos sean legibles y no falsos (conversión de texto a número); y finalmente te toman la temperatura para ver si estás sano (rangos físicos). Si fallas en cualquier punto, te piden amablemente que salgas sin detener la fila de los demás pasajeros.

---

## 5. El vacío que encontramos

- **Duda concreta:** ¿Cómo manejar la salida de errores en archivos de log persistentes en vez de imprimirlos directo en consola (`System.err`)?
- **Lo que ya podemos explicar:** La separación de responsabilidades entre validación de estructura, parsing con `try/catch` y reglas de dominio.
- **Para resolver la duda consultamos:** Pruebas directas en el IDE y revisión del uso de excepciones en Java.
- **Ahora lo entendemos así:** El manejo de excepciones debe servir para controlar el flujo sin detener la aplicación, retornando valores neutros o centinelas hacia las capas superiores.

---

## 6. Trazado de la solución (Caso: Entrada con dato no numérico)

- **Paso 1 (Entrada):** Se recibe `"EST-005,2026-09-07,abc,70.0,30.0"` e inicia `procesarLinea`.
- **Paso 2 (Estructura):** `campos = ["EST-005", "...", "abc", ...]`. `tieneNumeroCorrectoDeCampos` confirma 5 elementos (Pasa).
- **Paso 3 (Conversión):** `Double.parseDouble("abc")` dispara `NumberFormatException`. El `catch` en `ProcesadorLecturas` retorna `null`.
- **Paso 4 (Resultado):** `lectura == null`. `IngestaSensores` imprime "Registro rechazado" y termina el proceso para ese registro.

---

## 7. Decisión de diseño

- **Problema a resolver:** Prevenir caídas del programa por datos corruptos y organizar las comprobaciones de ingesta.
- **Estructura/Estrategia elegida:** Tubería modular desacoplada en clases especializadas (`ValidadorEstructura`, `ProcesadorLecturas`, `LecturaSensor` e `IngestaSensores`).
- **Alternativa descartada:** Un solo bloque monolítico con `if/else` y conversiones directas en el `main`.
- **Por qué elegimos la primera:** Garantiza la mantenibilidad, reutilización de código y facilita la división del trabajo en equipo.

---

## 8. Aporte e integración del equipo

- **Alejandro:** Modelo del dominio `LecturaSensor.java` (encapsulamiento y getters) y adaptación de `crearLectura`.
- **Jedreck:** `ValidadorEstructura.java` para comprobación previa de cantidad de campos (`length == 5`).
- **Juan Pablo:** `ProcesadorLecturas.java` para conversión segura de datos a `double` mediante bloques `try/catch` (`NumberFormatException`).
- **Julian:** Reglas del dominio y rangos de validez física (`TEMPERATURA_MINIMA`, `PM25_MINIMO`, etc.) en `IngestaSensores.java`.
- **Juan Felipe:** Orquestación general del flujo `procesarLinea` y pruebas de integración de componentes.

---

## 9. Commits realizados

- **Commit `a1b2c3d`:** `Crear modelo LecturaSensor y ajustar metodos auxiliares` | Demuestra encapsulamiento del dominio y corrección de firmas de método.
- **Commit `b2c3d4e`:** `Implementar ValidadorEstructura y pruebas unitarias` | Demuestra filtrado sintáctico temprano de la estructura CSV.
- **Commit `c3d4e5f`:** `Implementar conversor seguro de datos y manejo de excepciones` | Demuestra resiliencia ante datos no numéricos usando `try/catch`.
- **Commit `d4e5f6a`:** `Agregar validaciones de rango y limites fisicos para sensores` | Demuestra control de reglas de negocio del entorno ambiental.
- **Commit `e5f6a7b`:** `Orquestar flujo de ingesta modular e integrar pruebas generales` | Demuestra integración final del pipeline de la aplicación.

---

## 10. Reexplicación final

> La ingesta modular mediante un pipeline desacoplado combina la validación sintáctica, la resiliencia ante excepciones y el filtrado semántico. Esta estructura previene fallos en tiempo de ejecución, aísla los errores en el componente responsable y asegura que solo la información conforme al dominio físico sea procesada por el sistema.

---

## 11. Reflexión individual / grupal

1. **Lo que ahora podemos hacer:** Construir sistemas tolerantes a fallos integrando manejo de excepciones con validaciones de negocio en Java.
2. **El error que más enseñó:** Entender que las salidas rojas en la consola por `System.err` no significan que el programa haya fallado, sino que se manejó la excepción correctamente.
3. **Pregunta para la próxima clase:** ¿Cómo estructurar la lectura masiva de archivos `.csv` en disco sin saturar la memoria RAM mediante buffers?
4. **Distribución del trabajo:** Cada miembro desarrolló su módulo independiente y colaboró en la orquestación final dentro de `IngestaSensores.java`.