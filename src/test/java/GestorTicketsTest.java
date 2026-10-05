import org.junit.jupiter.api.Test;
import DWES.GestorTickets;
import DWES.Ticket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorTicketsTest {

    @Test
    void elGestorDebeComenzarVacio() {

        GestorTickets gestor = new GestorTickets();

        assertTrue(gestor.getTickets().isEmpty());
        assertEquals(0, gestor.getTotal());
        assertEquals(1, gestor.getSiguienteIdentificador());
    }

    @Test
    void losIdentificadoresDebenSerConsecutivos() {

        GestorTickets gestor = new GestorTickets();

        Ticket primero = gestor.crearTicket("Primera incidencia");
        Ticket segundo = gestor.crearTicket("Segunda incidencia");

        assertEquals(1, primero.getIdentificador());
        assertEquals(2, segundo.getIdentificador());
        assertEquals(3, gestor.getSiguienteIdentificador());
    }

    @Test
    void buscarDebeDevolverElMismoObjeto() {

        GestorTickets gestor = new GestorTickets();

        Ticket creado = gestor.crearTicket("Problema de red");

        Ticket encontrado = gestor.buscarPorId(
                creado.getIdentificador()
        );

        assertSame(creado, encontrado);
    }

    @Test
    void buscarUnIdentificadorInexistenteDebeDevolverNull() {

        GestorTickets gestor = new GestorTickets();

        gestor.crearTicket("Problema de red");

        assertNull(gestor.buscarPorId(999));
    }

    @Test
    void unaCreacionInvalidaNoDebeCambiarColeccionNiContador() {

        GestorTickets gestor = new GestorTickets();

        gestor.crearTicket("Primera incidencia");

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.crearTicket("   ")
        );

        assertEquals(1, gestor.getTotal());
        assertEquals(2, gestor.getSiguienteIdentificador());

        Ticket siguiente = gestor.crearTicket("Segunda incidencia");

        assertEquals(2, siguiente.getIdentificador());
    }

    @Test
    void laListaDevueltaDebeSerUnaCopiaPeroMantenerLosMismosObjetos() {

        GestorTickets gestor = new GestorTickets();

        Ticket ticket = gestor.crearTicket("Incidencia");

        List<Ticket> copia = gestor.getTickets();

        copia.clear();

        assertEquals(1, gestor.getTotal());
        assertSame(ticket, gestor.getTickets().getFirst());
    }

    @Test
    void anadirTicketRecuperadoDebeContinuarDesdeElMayorId() {

        GestorTickets gestor = new GestorTickets();

        gestor.añadirTicket(
                new Ticket(
                        7,
                        "Recuperado",
                        true
                )
        );

        Ticket nuevo = gestor.crearTicket("Nuevo");

        assertEquals(8, nuevo.getIdentificador());
    }

    @Test
    void noDebePermitirIdentificadoresRepetidosAlCargar() {

        GestorTickets gestor = new GestorTickets();

        gestor.añadirTicket(
                new Ticket(4, "Uno")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.añadirTicket(
                        new Ticket(4, "Dos")
                )
        );

        assertEquals(1, gestor.getTotal());
        assertEquals(5, gestor.getSiguienteIdentificador());
    }
}