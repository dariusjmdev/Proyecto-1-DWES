import org.junit.jupiter.api.Test;
import DWES.GestorTickets;
import DWES.Ticket;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de la funcionalidad nueva de estadísticas.
 */
class GestorTicketsEstadisticasTest {

    @Test
    void gestorVacioDebeTenerTodasLasEstadisticasAZero() {

        GestorTickets gestor =
                new GestorTickets();

        assertEquals(
                0,
                gestor.getTotal()
        );

        assertEquals(
                0,
                gestor.getAbiertas()
        );

        assertEquals(
                0,
                gestor.getCerradas()
        );
    }

    @Test
    void dosIncidenciasAbiertasDebenContabilizarseCorrectamente() {

        GestorTickets gestor =
                new GestorTickets();

        gestor.crearTicket("Primera");
        gestor.crearTicket("Segunda");

        assertEquals(
                2,
                gestor.getTotal()
        );

        assertEquals(
                2,
                gestor.getAbiertas()
        );

        assertEquals(
                0,
                gestor.getCerradas()
        );
    }

    @Test
    void unaIncidenciaCerradaYUnaAbiertaDebenContabilizarseCorrectamente() {

        GestorTickets gestor =
                new GestorTickets();

        Ticket primera =
                gestor.crearTicket("Primera");

        gestor.crearTicket("Segunda");

        primera.cerrar();

        assertEquals(
                2,
                gestor.getTotal()
        );

        assertEquals(
                1,
                gestor.getAbiertas()
        );

        assertEquals(
                1,
                gestor.getCerradas()
        );
    }
}