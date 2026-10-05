import DWES.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketTest {

    @Test
    void ticketNuevoDebeEstarAbierto() {

        Ticket ticket = new Ticket(1, "Falla el teclado");

        assertTrue(ticket.estaAbierto());
        assertFalse(ticket.estaCerrado());
        assertEquals("ABIERTA", ticket.getEstado());
    }

    @Test
    void cerrarDebeCambiarElEstado() {

        Ticket ticket = new Ticket(1, "Falla el teclado");

        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
        assertFalse(ticket.estaAbierto());
        assertEquals("CERRADA", ticket.getEstado());
    }

    @Test
    void cerrarDosVecesDebeMantenerElTicketCerrado() {

        Ticket ticket = new Ticket(1, "Falla el teclado");

        ticket.cerrar();
        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
        assertFalse(ticket.estaAbierto());
    }

    @Test
    void descripcionVaciaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "")
        );
    }

    @Test
    void descripcionConEspaciosDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "     ")
        );
    }

    @Test
    void descripcionNulaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, null)
        );
    }

    @Test
    void identificadorCeroDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(0, "Falla el teclado")
        );
    }

    @Test
    void identificadorNegativoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(-1, "Falla el teclado")
        );
    }

    @Test
    void identificadorYDescripcionDebenConservarse() {

        Ticket ticket = new Ticket(
                25,
                "No funciona Internet"
        );

        assertEquals(25, ticket.getIdentificador());
        assertEquals(
                "No funciona Internet",
                ticket.getDescripcion()
        );
    }
}