
import DWES.GestorTickets;
import DWES.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de la clase {@link GestorTickets}.
 *
 * <p>Comprueba la gestión de la colección, la generación de
 * identificadores, las búsquedas, los cierres y la encapsulación.</p>
 *
 * @version 1.0
 */
class GestorTicketsTest {

    /**
     * Comprueba que un gestor nuevo no contiene incidencias.
     */
    @Test
    void gestorNuevoDebeEstarVacio() {

        GestorTickets gestor = new GestorTickets();

        assertTrue(gestor.getTickets().isEmpty());
        assertEquals(0, gestor.getTotalTickets());
    }

    /**
     * Comprueba que los identificadores se asignan consecutivamente.
     */
    @Test
    void lasIncidenciasDebenRecibirIdentificadoresConsecutivos() {

        GestorTickets gestor = new GestorTickets();

        Ticket primero = gestor.crearTicket(
                "El teclado no funciona"
        );

        Ticket segundo = gestor.crearTicket(
                "No hay conexión de red"
        );

        assertEquals(1, primero.getIdentificador());
        assertEquals(2, segundo.getIdentificador());
    }

    /**
     * Comprueba que buscar una incidencia devuelve la misma instancia
     * que se almacenó en el gestor.
     */
    @Test
    void buscarDebeDevolverLaMismaInstancia() {

        GestorTickets gestor = new GestorTickets();

        Ticket creado = gestor.crearTicket(
                "El teclado no funciona"
        );

        Ticket encontrado = gestor.buscarPorId(
                creado.getIdentificador()
        );

        assertSame(creado, encontrado);
    }

    /**
     * Comprueba que una búsqueda inexistente devuelve null.
     */
    @Test
    void buscarUnaIncidenciaInexistenteDebeDevolverNull() {

        GestorTickets gestor = new GestorTickets();

        assertNull(gestor.buscarPorId(99));
    }

    /**
     * Comprueba que una creación inválida no modifica la colección.
     */
    @Test
    void creacionInvalidaNoDebeModificarLaColeccion() {

        GestorTickets gestor = new GestorTickets();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.crearTicket("2")
        );

        assertEquals(
                0,
                gestor.getTotalTickets()
        );
    }

    /**
     * Comprueba que una creación inválida no consume el siguiente ID.
     */
    @Test
    void creacionInvalidaNoDebeConsumirIdentificador() {

        GestorTickets gestor = new GestorTickets();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.crearTicket("2")
        );

        Ticket ticket = gestor.crearTicket(
                "El teclado no funciona"
        );

        assertEquals(
                1,
                ticket.getIdentificador()
        );
    }

    /**
     * Comprueba que la lista devuelta por el gestor es una copia.
     */
    @Test
    void getTicketsDebeDevolverUnaCopiaDeLaLista() {

        GestorTickets gestor = new GestorTickets();

        Ticket ticket = gestor.crearTicket(
                "El teclado no funciona"
        );

        List<Ticket> copia = gestor.getTickets();

        assertNotSame(
                copia,
                gestor.getTickets()
        );

        copia.clear();

        assertEquals(
                1,
                gestor.getTotalTickets()
        );

        assertTrue(
                gestor.getTickets().contains(ticket)
        );
    }

    /**
     * Comprueba que la copia contiene las mismas instancias Ticket.
     */
    @Test
    void getTicketsDebeConservarLasMismasInstancias() {

        GestorTickets gestor = new GestorTickets();

        Ticket ticket = gestor.crearTicket(
                "El teclado no funciona"
        );

        List<Ticket> copia = gestor.getTickets();

        assertSame(
                ticket,
                copia.get(0)
        );
    }

    /**
     * Comprueba que cerrar una incidencia existente funciona.
     */
    @Test
    void cerrarIncidenciaExistenteDebeFuncionar() {

        GestorTickets gestor = new GestorTickets();

        Ticket ticket = gestor.crearTicket(
                "El teclado no funciona"
        );

        boolean resultado = gestor.cerrarTicket(
                ticket.getIdentificador()
        );

        assertTrue(resultado);
        assertTrue(ticket.estaCerrado());
    }

    /**
     * Comprueba que cerrar una incidencia inexistente devuelve false.
     */
    @Test
    void cerrarIncidenciaInexistenteDebeDevolverFalse() {

        GestorTickets gestor = new GestorTickets();

        assertFalse(
                gestor.cerrarTicket(99)
        );
    }

    /**
     * Comprueba que una incidencia ya cerrada no puede cerrarse
     * nuevamente como si fuera una operación nueva.
     */
    @Test
    void cerrarIncidenciaYaCerradaDebeDevolverFalse() {

        GestorTickets gestor = new GestorTickets();

        Ticket ticket = gestor.crearTicket(
                "El teclado no funciona"
        );

        gestor.cerrarTicket(
                ticket.getIdentificador()
        );

        assertFalse(
                gestor.cerrarTicket(
                        ticket.getIdentificador()
                )
        );
    }

    /**
     * Comprueba que se pueden recuperar incidencias y que el siguiente
     * identificador continúa después del mayor identificador cargado.
     */
    @Test
    void añadirTicketDebeActualizarElSiguienteIdentificador() {

        GestorTickets gestor = new GestorTickets();

        gestor.añadirTicket(
                new Ticket(
                        7,
                        "Incidencia recuperada"
                )
        );

        Ticket nuevo = gestor.crearTicket(
                "Nueva incidencia del sistema"
        );

        assertEquals(
                8,
                nuevo.getIdentificador()
        );
    }

    /**
     * Comprueba que no se permiten identificadores duplicados.
     */
    @Test
    void añadirTicketDuplicadoDebeSerRechazado() {

        GestorTickets gestor = new GestorTickets();

        gestor.añadirTicket(
                new Ticket(
                        1,
                        "Primera incidencia"
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.añadirTicket(
                        new Ticket(
                                1,
                                "Otra incidencia"
                        )
                )
        );
    }

    /**
     * Comprueba que no se puede añadir una incidencia nula.
     */
    @Test
    void añadirTicketNuloDebeSerRechazado() {

        GestorTickets gestor = new GestorTickets();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.añadirTicket(null)
        );
    }
}