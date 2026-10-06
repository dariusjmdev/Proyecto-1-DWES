package DWES;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la colección de incidencias del sistema HelpDesk.
 *
 * <p>Es responsable de crear incidencias, asignar identificadores
 * consecutivos, buscar incidencias, cerrarlas y calcular estadísticas.</p>
 *
 * <p>La colección interna permanece encapsulada y nunca se devuelve
 * directamente al código externo.</p>
 *
 * @version 1.0
 */
public class GestorTickets {

    /**
     * Colección interna de incidencias.
     */
    private final List<Ticket> tickets;

    /**
     * Identificador que se asignará a la siguiente incidencia creada.
     */
    private int siguienteIdentificador;

    /**
     * Crea un gestor vacío.
     */
    public GestorTickets() {
        tickets = new ArrayList<>();
        siguienteIdentificador = 1;
    }

    /**
     * Crea una nueva incidencia.
     *
     * <p>El identificador no se consume si la creación de la incidencia
     * falla por una descripción no válida.</p>
     *
     * @param descripcion descripción de la incidencia.
     * @return incidencia creada.
     * @throws IllegalArgumentException si la descripción no es válida.
     */
    public Ticket crearTicket(String descripcion) {

        Ticket nuevoTicket = new Ticket(
                siguienteIdentificador,
                descripcion
        );

        tickets.add(nuevoTicket);
        siguienteIdentificador++;

        return nuevoTicket;
    }

    /**
     * Añade una incidencia recuperada desde un fichero.
     *
     * <p>El siguiente identificador se actualiza para garantizar que
     * las nuevas incidencias tengan un identificador superior al mayor
     * identificador recuperado.</p>
     *
     * @param ticket incidencia que se desea añadir.
     * @throws IllegalArgumentException si el ticket es nulo o si ya
     *         existe una incidencia con el mismo identificador.
     */
    public void añadirTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "No se puede añadir una incidencia nula."
            );
        }

        if (buscarPorId(ticket.getIdentificador()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una incidencia con el identificador "
                            + ticket.getIdentificador()
                            + "."
            );
        }

        tickets.add(ticket);

        if (ticket.getIdentificador() >= siguienteIdentificador) {
            siguienteIdentificador =
                    ticket.getIdentificador() + 1;
        }
    }

    /**
     * Busca una incidencia mediante su identificador.
     *
     * @param identificador identificador que se desea buscar.
     * @return incidencia encontrada o {@code null} si no existe.
     */
    public Ticket buscarPorId(int identificador) {

        if (identificador <= 0) {
            return null;
        }

        for (Ticket ticket : tickets) {

            if (ticket.getIdentificador() == identificador) {
                return ticket;
            }
        }

        return null;
    }

    /**
     * Cierra una incidencia.
     *
     * @param identificador identificador de la incidencia.
     * @return {@code true} si se ha cerrado correctamente;
     *         {@code false} si no existe o ya estaba cerrada.
     */
    public boolean cerrarTicket(int identificador) {

        Ticket ticket = buscarPorId(identificador);

        if (ticket == null) {
            return false;
        }

        if (ticket.estaCerrado()) {
            return false;
        }

        ticket.cerrar();

        return true;
    }

    /**
     * Obtiene una copia de la colección de incidencias.
     *
     * <p>La lista devuelta es una copia, por lo que modificarla no afecta
     * a la colección interna del gestor. Los objetos {@link Ticket}
     * contenidos son las mismas instancias.</p>
     *
     * @return copia de la colección de incidencias.
     */
    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    /**
     * Obtiene el número total de incidencias.
     *
     * @return número total de incidencias.
     */
    public int getTotalTickets() {
        return tickets.size();
    }

    /**
     * Calcula el número de incidencias abiertas.
     *
     * @return número de incidencias abiertas.
     */
    public int getTicketsAbiertos() {

        int abiertos = 0;

        for (Ticket ticket : tickets) {

            if (ticket.estaAbierto()) {
                abiertos++;
            }
        }

        return abiertos;
    }

    /**
     * Calcula el número de incidencias cerradas.
     *
     * @return número de incidencias cerradas.
     */
    public int getTicketsCerrados() {

        int cerrados = 0;

        for (Ticket ticket : tickets) {

            if (ticket.estaCerrado()) {
                cerrados++;
            }
        }

        return cerrados;
    }
}