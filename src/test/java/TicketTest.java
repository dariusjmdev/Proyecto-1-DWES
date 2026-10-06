import DWES.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de la clase {@link Ticket}.
 *
 * <p>Comprueba la creación de incidencias, su estado inicial,
 * el cierre y las reglas de validación.</p>
 *
 * @version 1.0
 */
class TicketTest {

    /**
     * Comprueba que una incidencia nueva comienza abierta.
     */
    @Test
    void unaIncidenciaNuevaDebeEstarAbierta() {

        Ticket ticket = new Ticket(
                1,
                "El teclado no funciona"
        );

        assertTrue(ticket.estaAbierto());
        assertFalse(ticket.estaCerrado());
        assertEquals("ABIERTA", ticket.getEstado());
    }

    /**
     * Comprueba que cerrar una incidencia cambia su estado.
     */
    @Test
    void cerrarIncidenciaDebeCambiarSuEstado() {

        Ticket ticket = new Ticket(
                1,
                "El teclado no funciona"
        );

        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
        assertFalse(ticket.estaAbierto());
        assertEquals("CERRADA", ticket.getEstado());
    }

    /**
     * Comprueba que cerrar varias veces una incidencia no produce
     * un estado incorrecto.
     */
    @Test
    void cerrarUnaIncidenciaVariasVecesDebeSerSeguro() {

        Ticket ticket = new Ticket(
                1,
                "El teclado no funciona"
        );

        ticket.cerrar();
        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
    }

    /**
     * Comprueba que una descripción formada únicamente por espacios
     * es rechazada.
     */
    @Test
    void descripcionSoloConEspaciosDebeSerRechazada() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "          ")
        );
    }

    /**
     * Comprueba que una descripción nula es rechazada.
     */
    @Test
    void descripcionNulaDebeSerRechazada() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, null)
        );
    }

    /**
     * Comprueba que un identificador cero es rechazado.
     */
    @Test
    void identificadorCeroDebeSerRechazado() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(
                        0,
                        "El teclado no funciona"
                )
        );
    }

    /**
     * Comprueba que un identificador negativo es rechazado.
     */
    @Test
    void identificadorNegativoDebeSerRechazado() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(
                        -1,
                        "El teclado no funciona"
                )
        );
    }

    /**
     * Comprueba que una descripción demasiado corta es rechazada.
     */
    @Test
    void descripcionDemasiadoCortaDebeSerRechazada() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "Error")
        );
    }

    /**
     * Comprueba que una descripción formada únicamente por números
     * es rechazada.
     */
    @Test
    void descripcionSoloNumerosDebeSerRechazada() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "1234567890")
        );
    }

    /**
     * Comprueba que una descripción formada por un único carácter
     * repetido es rechazada.
     */
    @Test
    void descripcionConUnSoloCaracterRepetidoDebeSerRechazada() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Ticket(1, "aaaaaaaaaa")
        );
    }

    /**
     * Comprueba que una descripción válida se acepta.
     */
    @Test
    void descripcionValidaDebeSerAceptada() {

        Ticket ticket = new Ticket(
                1,
                "El teclado no funciona"
        );

        assertEquals(
                "El teclado no funciona",
                ticket.getDescripcion()
        );
    }

    /**
     * Comprueba que los espacios exteriores de una descripción
     * se eliminan.
     */
    @Test
    void laDescripcionDebeEliminarEspaciosExteriores() {

        Ticket ticket = new Ticket(
                1,
                "   El teclado no funciona   "
        );

        assertEquals(
                "El teclado no funciona",
                ticket.getDescripcion()
        );
    }
}