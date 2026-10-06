package DWES;

/**
 * Representa una incidencia del sistema HelpDesk.
 *
 * <p>Cada incidencia tiene un identificador, una descripción y un estado.
 * Una incidencia comienza siempre abierta y puede pasar a estado cerrado
 * mediante el método {@link #cerrar()}.</p>
 *
 * <p>El identificador y la descripción son inmutables después de crear
 * la incidencia.</p>
 *
 * @version 1.0
 */
public class Ticket {

    /**
     * Longitud mínima permitida para una descripción.
     */
    private static final int MIN_LONGITUD_DESCRIPCION = 10;

    /**
     * Longitud máxima permitida para una descripción.
     */
    private static final int MAX_LONGITUD_DESCRIPCION = 200;

    /**
     * Identificador único de la incidencia.
     */
    private final int identificador;

    /**
     * Descripción de la incidencia.
     */
    private final String descripcion;

    /**
     * Indica si la incidencia está cerrada.
     */
    private boolean cerrado;

    /**
     * Crea una nueva incidencia abierta.
     *
     * @param identificador identificador positivo de la incidencia.
     * @param descripcion descripción de la incidencia.
     * @throws IllegalArgumentException si el identificador o la descripción
     *         no son válidos.
     */
    public Ticket(int identificador, String descripcion) {
        this(identificador, descripcion, false);
    }

    /**
     * Crea una incidencia con el estado indicado.
     *
     * <p>Este constructor se utiliza también para recuperar incidencias
     * almacenadas en el fichero.</p>
     *
     * @param identificador identificador positivo de la incidencia.
     * @param descripcion descripción de la incidencia.
     * @param cerrado indica si la incidencia está cerrada.
     * @throws IllegalArgumentException si el identificador o la descripción
     *         no son válidos.
     */
    public Ticket(int identificador, String descripcion, boolean cerrado) {

        validarIdentificador(identificador);
        validarDescripcion(descripcion);

        this.identificador = identificador;
        this.descripcion = descripcion.trim();
        this.cerrado = cerrado;
    }

    /**
     * Valida un identificador.
     *
     * @param identificador identificador que se desea validar.
     * @throws IllegalArgumentException si el identificador no es positivo.
     */
    private static void validarIdentificador(int identificador) {

        if (identificador <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser un número positivo."
            );
        }
    }

    /**
     * Valida una descripción.
     *
     * <p>La descripción no puede ser nula, vacía, demasiado corta,
     * demasiado larga, estar formada únicamente por números ni estar
     * formada por un único carácter repetido.</p>
     *
     * @param descripcion descripción que se desea validar.
     * @throws IllegalArgumentException si la descripción no es válida.
     */
    private static void validarDescripcion(String descripcion) {

        if (descripcion == null) {
            throw new IllegalArgumentException(
                    "La descripción no puede ser nula."
            );
        }

        String texto = descripcion.trim();

        if (texto.isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripción no puede estar vacía."
            );
        }

        if (texto.length() < MIN_LONGITUD_DESCRIPCION) {
            throw new IllegalArgumentException(
                    "La descripción debe tener al menos "
                            + MIN_LONGITUD_DESCRIPCION
                            + " caracteres."
            );
        }

        if (texto.length() > MAX_LONGITUD_DESCRIPCION) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar los "
                            + MAX_LONGITUD_DESCRIPCION
                            + " caracteres."
            );
        }

        if (texto.matches("\\d+")) {
            throw new IllegalArgumentException(
                    "La descripción no puede contener únicamente números."
            );
        }

        if (!texto.matches(".*[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ].*")) {
            throw new IllegalArgumentException(
                    "La descripción debe contener texto."
            );
        }

        if (todosLosCaracteresSonIguales(texto)) {
            throw new IllegalArgumentException(
                    "La descripción no puede estar formada "
                            + "por un único carácter repetido."
            );
        }
    }

    /**
     * Comprueba si todos los caracteres de un texto son iguales.
     *
     * @param texto texto que se desea comprobar.
     * @return {@code true} si todos los caracteres son iguales;
     *         {@code false} en caso contrario.
     */
    private static boolean todosLosCaracteresSonIguales(String texto) {

        char primero = texto.charAt(0);

        for (int i = 1; i < texto.length(); i++) {

            if (texto.charAt(i) != primero) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtiene el identificador de la incidencia.
     *
     * @return identificador de la incidencia.
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Obtiene la descripción de la incidencia.
     *
     * @return descripción de la incidencia.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Comprueba si la incidencia está cerrada.
     *
     * @return {@code true} si está cerrada;
     *         {@code false} si está abierta.
     */
    public boolean estaCerrado() {
        return cerrado;
    }

    /**
     * Comprueba si la incidencia está abierta.
     *
     * @return {@code true} si está abierta;
     *         {@code false} si está cerrada.
     */
    public boolean estaAbierto() {
        return !cerrado;
    }

    /**
     * Cierra la incidencia.
     *
     * <p>Si la incidencia ya estaba cerrada, permanece cerrada.</p>
     */
    public void cerrar() {
        cerrado = true;
    }

    /**
     * Obtiene el estado textual de la incidencia.
     *
     * @return {@code "CERRADA"} si está cerrada o
     *         {@code "ABIERTA"} si está abierta.
     */
    public String getEstado() {
        return cerrado ? "CERRADA" : "ABIERTA";
    }
}