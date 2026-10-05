package DWES;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la colección de incidencias del HelpDesk.
 *
 * <p>Se encarga de crear tickets, asignar identificadores consecutivos,
 * buscarlos y calcular las estadísticas. No gestiona la entrada por teclado
 * ni la persistencia en disco.</p>
 */
public class GestorTickets {

    private final List<Ticket> tickets;
    private int siguienteIdentificador;

    /**
     * Crea un gestor vacío. El primer identificador será el 1.
     */
    public GestorTickets() {
        tickets = new ArrayList<>();
        siguienteIdentificador = 1;
    }

    /**
     * Crea un ticket y lo añade al gestor.
     *
     * <p>La construcción del ticket se realiza antes de modificar la colección
     * o el contador. Por ello, si la descripción es inválida, no se consume
     * ningún identificador.</p>
     *
     * @param descripcion descripción de la incidencia.
     * @return el ticket recién creado.
     * @throws IllegalArgumentException si la descripción no es válida.
     */
    public Ticket crearTicket(String descripcion) {

        Ticket nuevo = new Ticket(
                siguienteIdentificador,
                descripcion
        );

        tickets.add(nuevo);
        siguienteIdentificador++;

        return nuevo;
    }

    /**
     * Añade al gestor un ticket previamente creado, normalmente procedente
     * de persistencia.
     *
     * <p>Valida que no exista otro ticket con el mismo identificador
     * y actualiza el siguiente identificador.</p>
     *
     * @param ticket ticket que se quiere incorporar.
     * @throws IllegalArgumentException si el ticket es nulo o su identificador ya existe.
     */
    public void añadirTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "El ticket no puede ser nulo."
            );
        }

        if (buscarPorId(ticket.getIdentificador()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un ticket con ese identificador."
            );
        }

        tickets.add(ticket);

        siguienteIdentificador = Math.max(
                siguienteIdentificador,
                ticket.getIdentificador() + 1
        );
    }

    /**
     * Busca un ticket por identificador.
     *
     * @param identificador identificador buscado.
     * @return el mismo objeto Ticket almacenado, o null si no existe.
     */
    public Ticket buscarPorId(int identificador) {

        for (Ticket ticket : tickets) {

            if (ticket.getIdentificador() == identificador) {
                return ticket;
            }
        }

        return null;
    }

    /**
     * Cierra un ticket existente.
     *
     * @param identificador identificador del ticket.
     * @return true si existía y se ha cerrado;
     *         false si no existe o ya estaba cerrado.
     */
    public boolean cerrarTicket(int identificador) {

        Ticket ticket = buscarPorId(identificador);

        if (ticket == null || ticket.estaCerrado()) {
            return false;
        }

        ticket.cerrar();

        return true;
    }

    /**
     * Devuelve una copia de la lista interna.
     *
     * <p>La lista devuelta es independiente de la lista del gestor,
     * pero contiene las mismas referencias a los objetos Ticket.</p>
     *
     * @return copia de la colección de tickets.
     */
    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    /**
     * @return número total de incidencias.
     */
    public int getTotal() {
        return tickets.size();
    }

    /**
     * @return número de incidencias abiertas.
     */
    public int getAbiertas() {

        int contador = 0;

        for (Ticket ticket : tickets) {

            if (ticket.estaAbierto()) {
                contador++;
            }
        }

        return contador;
    }

    /**
     * @return número de incidencias cerradas.
     */
    public int getCerradas() {

        int contador = 0;

        for (Ticket ticket : tickets) {

            if (ticket.estaCerrado()) {
                contador++;
            }
        }

        return contador;
    }

    /**
     * @return identificador que recibirá la siguiente incidencia creada.
     */
    public int getSiguienteIdentificador() {
        return siguienteIdentificador;
    }
}
