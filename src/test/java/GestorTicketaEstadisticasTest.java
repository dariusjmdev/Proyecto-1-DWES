import org.junit.jupiter.api.Test;
import DWES.GestorTickets;
import DWES.Ticket;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de las estadísticas calculadas por {@link GestorTickets}.
 *
 * <p>Comprueba los casos de colección vacía, incidencias abiertas
 * e incidencias abiertas y cerradas.</p>
 *
 * @version 1.0
 */
class GestorTicketsEstadisticasTest {

    /**
     * Comprueba las estadísticas cuando no existen incidencias.
     */
    @Test
    void estadisticasDeGestorVacioDebenSerCero() {

        GestorTickets gestor = new GestorTickets();

        assertEquals(
                0,
                gestor.getTotalTickets()
        );

        assertEquals(
                0,
                gestor.getTicketsAbiertos()
        );

        assertEquals(
                0,
                gestor.getTicketsCerrados()
        );
    }

    /**
     * Comprueba las estadísticas cuando existen dos incidencias
     * abiertas.
     */
    @Test
    void dosIncidenciasAbiertasDebenContabilizarseCorrectamente() {

        GestorTickets gestor = new GestorTickets();

        gestor.crearTicket(
                "El teclado no funciona"
        );

        gestor.crearTicket(
                "No hay conexión de red"
        );

        assertEquals(
                2,
                gestor.getTotalTickets()
        );

        assertEquals(
                2,
                gestor.getTicketsAbiertos()
        );

        assertEquals(
                0,
                gestor.getTicketsCerrados()
        );
    }

    /**
     * Comprueba las estadísticas cuando existe una incidencia cerrada
     * y otra abierta.
     */
    @Test
    void unaCerradaYUnaAbiertaDebenContabilizarseCorrectamente() {

        GestorTickets gestor = new GestorTickets();

        Ticket primera = gestor.crearTicket(
                "El teclado no funciona"
        );

        gestor.crearTicket(
                "No hay conexión de red"
        );

        gestor.cerrarTicket(
                primera.getIdentificador()
        );

        assertEquals(
                2,
                gestor.getTotalTickets()
        );

        assertEquals(
                1,
                gestor.getTicketsAbiertos()
        );

        assertEquals(
                1,
                gestor.getTicketsCerrados()
        );
    }

    /**
     * Comprueba que la suma de incidencias abiertas y cerradas
     * coincide con el número total.
     */
    @Test
    void abiertasMasCerradasDebeCoincidirConElTotal() {

        GestorTickets gestor = new GestorTickets();

        Ticket primera = gestor.crearTicket(
                "El teclado no funciona"
        );

        gestor.crearTicket(
                "No hay conexión de red"
        );

        Ticket tercera = gestor.crearTicket(
                "La pantalla no muestra imagen"
        );

        gestor.cerrarTicket(
                primera.getIdentificador()
        );

        gestor.cerrarTicket(
                tercera.getIdentificador()
        );

        assertEquals(
                gestor.getTotalTickets(),
                gestor.getTicketsAbiertos()
                        + gestor.getTicketsCerrados()
        );
    }
}