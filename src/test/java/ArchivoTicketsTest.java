import org.junit.jupiter.api.Test;
import DWES.Ticket;
import DWES.ArchivoTicket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias de la clase {@link ArchivoTicket}.
 *
 * <p>Comprueba la escritura y lectura de incidencias, la recuperación
 * de estados, la conservación de identificadores y la detección
 * de datos inválidos.</p>
 *
 * @version 1.0
 */
class ArchivoTicketsTest {

    /**
     * Comprueba que guardar y cargar conserva las incidencias.
     *
     * @throws Exception si se produce un error durante la prueba.
     */
    @Test
    void guardarYCargarDebeConservarLasIncidencias()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            Ticket primera = new Ticket(
                    1,
                    "El teclado no funciona"
            );

            Ticket segunda = new Ticket(
                    2,
                    "No hay conexión de red",
                    true
            );

            archivo.guardar(
                    List.of(primera, segunda)
            );

            List<Ticket> recuperadas =
                    archivo.cargar();

            assertEquals(
                    2,
                    recuperadas.size()
            );

            assertEquals(
                    1,
                    recuperadas.get(0).getIdentificador()
            );

            assertEquals(
                    "El teclado no funciona",
                    recuperadas.get(0).getDescripcion()
            );

            assertTrue(
                    recuperadas.get(0).estaAbierto()
            );

            assertEquals(
                    2,
                    recuperadas.get(1).getIdentificador()
            );

            assertEquals(
                    "No hay conexión de red",
                    recuperadas.get(1).getDescripcion()
            );

            assertTrue(
                    recuperadas.get(1).estaCerrado()
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que un fichero inexistente se interpreta como
     * una colección vacía.
     *
     * @throws Exception si se produce un error durante la prueba.
     */
    @Test
    void ficheroInexistenteDebeProducirColeccionVacia()
            throws Exception {

        Path fichero = Files.createTempDirectory(
                "helpdesk-test"
        ).resolve("inexistente.txt");

        ArchivoTicket archivo =
                new ArchivoTicket(fichero);

        List<Ticket> tickets =
                archivo.cargar();

        assertTrue(tickets.isEmpty());
    }

    /**
     * Comprueba que una descripción que contiene punto y coma
     * se conserva correctamente.
     *
     * @throws Exception si se produce un error durante la prueba.
     */
    @Test
    void descripcionConPuntoYComaDebeConservarse()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            Ticket ticket = new Ticket(
                    1,
                    "Error de red; no hay conexión"
            );

            archivo.guardar(
                    List.of(ticket)
            );

            List<Ticket> recuperadas =
                    archivo.cargar();

            assertEquals(
                    1,
                    recuperadas.size()
            );

            assertEquals(
                    "Error de red; no hay conexión",
                    recuperadas.get(0).getDescripcion()
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que un identificador duplicado en el fichero
     * provoca un error.
     *
     * @throws Exception si se produce un error de acceso al fichero.
     */
    @Test
    void identificadoresDuplicadosDebenSerRechazados()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            Files.writeString(
                    fichero,
                    "1;false;El teclado no funciona\n"
                            + "1;true;No hay conexión de red\n"
            );

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            assertThrows(
                    IllegalArgumentException.class,
                    archivo::cargar
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que un estado distinto de true o false
     * provoca un error.
     *
     * @throws Exception si se produce un error de acceso al fichero.
     */
    @Test
    void estadoInvalidoDebeSerRechazado()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            Files.writeString(
                    fichero,
                    "1;abierto;El teclado no funciona\n"
            );

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            assertThrows(
                    IllegalArgumentException.class,
                    archivo::cargar
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que una línea con formato incorrecto
     * provoca un error.
     *
     * @throws Exception si se produce un error de acceso al fichero.
     */
    @Test
    void lineaConFormatoIncorrectoDebeSerRechazada()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            Files.writeString(
                    fichero,
                    "1;false\n"
            );

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            assertThrows(
                    IllegalArgumentException.class,
                    archivo::cargar
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que un identificador no numérico provoca un error.
     *
     * @throws Exception si se produce un error de acceso al fichero.
     */
    @Test
    void identificadorNoNumericoDebeSerRechazado()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            Files.writeString(
                    fichero,
                    "abc;false;El teclado no funciona\n"
            );

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            assertThrows(
                    IllegalArgumentException.class,
                    archivo::cargar
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que una descripción inválida almacenada en el fichero
     * también es rechazada.
     *
     * @throws Exception si se produce un error de acceso al fichero.
     */
    @Test
    void descripcionInvalidaEnFicheroDebeSerRechazada()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            Files.writeString(
                    fichero,
                    "1;false;2\n"
            );

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            assertThrows(
                    IllegalArgumentException.class,
                    archivo::cargar
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que guardar reemplaza el contenido anterior
     * del fichero.
     *
     * @throws Exception si se produce un error durante la prueba.
     */
    @Test
    void guardarDebeReemplazarElContenidoAnterior()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            Ticket primero = new Ticket(
                    1,
                    "El teclado no funciona"
            );

            archivo.guardar(
                    List.of(primero)
            );

            Ticket segundo = new Ticket(
                    2,
                    "No hay conexión de red"
            );

            archivo.guardar(
                    List.of(segundo)
            );

            List<Ticket> recuperadas =
                    archivo.cargar();

            assertEquals(
                    1,
                    recuperadas.size()
            );

            assertEquals(
                    2,
                    recuperadas.get(0).getIdentificador()
            );

            assertFalse(
                    recuperadas.get(0).getIdentificador() == 1
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }

    /**
     * Comprueba que el fichero puede guardar una colección vacía
     * y posteriormente recuperarla.
     *
     * @throws Exception si se produce un error durante la prueba.
     */
    @Test
    void guardarColeccionVaciaDebeVaciarElFichero()
            throws Exception {

        Path fichero = Files.createTempFile(
                "helpdesk-test",
                ".txt"
        );

        try {

            ArchivoTicket archivo =
                    new ArchivoTicket(fichero);

            archivo.guardar(
                    List.of(
                            new Ticket(
                                    1,
                                    "El teclado no funciona"
                            )
                    )
            );

            archivo.guardar(
                    List.of()
            );

            List<Ticket> recuperadas =
                    archivo.cargar();

            assertTrue(
                    recuperadas.isEmpty()
            );

        } finally {

            Files.deleteIfExists(fichero);
        }
    }
}