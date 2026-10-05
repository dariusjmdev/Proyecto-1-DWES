package DWES;

/**
 * Representa una incidencia del sistema HelpDesk.
 *
 * <p>Un ticket tiene un identificador positivo y único dentro del gestor,
 * una descripción de una sola línea y un estado. Al crearse comienza abierto.
 * El identificador y la descripción son inmutables.</p>
 */
public class Ticket {

    private final int identificador;
    private final String descripcion;
    private boolean cerrado;

    /**
     * Crea un ticket abierto.
     *
     * @param identificador identificador positivo del ticket.
     * @param descripcion descripción de la incidencia.
     * @throws IllegalArgumentException si el identificador no es positivo,
     *                                  o la descripción es nula, vacía o solo contiene espacios.
     */
    public Ticket(int identificador, String descripcion) {
        validarIdentificador(identificador);
        validarDescripcion(descripcion);

        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cerrado = false;
    }

    /**
     * Crea un ticket recuperado desde persistencia con su estado original.
     *
     * @param identificador identificador positivo.
     * @param descripcion descripción válida.
     * @param cerrado true si el ticket estaba cerrado.
     * @throws IllegalArgumentException si los datos no son válidos.
     */
    public Ticket(int identificador, String descripcion, boolean cerrado) {
        this(identificador, descripcion);
        this.cerrado = cerrado;
    }

    /**
     * @return identificador del ticket.
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * @return descripción del ticket.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return true si el ticket está cerrado.
     */
    public boolean estaCerrado() {
        return cerrado;
    }

    /**
     * @return true si el ticket está abierto.
     */
    public boolean estaAbierto() {
        return !cerrado;
    }

    /**
     * Cierra el ticket.
     *
     * <p>Cerrar un ticket que ya está cerrado no produce ningún cambio.</p>
     */
    public void cerrar() {
        cerrado = true;
    }

    /**
     * Devuelve el estado en texto para mostrarlo por consola.
     *
     * @return "CERRADA" o "ABIERTA".
     */
    public String getEstado() {
        return cerrado ? "CERRADA" : "ABIERTA";
    }

    /**
     * Valida un identificador de ticket.
     *
     * @param identificador identificador a validar.
     * @throws IllegalArgumentException si no es positivo.
     */
    private static void validarIdentificador(int identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser positivo."
            );
        }
    }

    /**
     * Valida una descripción de ticket.
     *
     * @param descripcion descripción a validar.
     * @throws IllegalArgumentException si es nula, vacía o solo contiene espacios.
     */
    private static void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripción no puede estar vacía."
            );
        }

        if (descripcion.contains("\n") || descripcion.contains("\r")) {
            throw new IllegalArgumentException(
                    "La descripción debe ocupar una sola línea."
            );
        }
    }

    /**
     * Representación textual del ticket.
     *
     * @return datos principales de la incidencia.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "identificador=" + identificador +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + getEstado() +
                '}';
    }
}