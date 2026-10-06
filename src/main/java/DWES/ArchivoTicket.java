package DWES;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gestiona la persistencia de las incidencias en un fichero.
 *
 * <p>El formato utilizado para cada incidencia es:</p>
 *
 * <pre>
 * identificador;estado;descripcion
 * </pre>
 *
 * <p>Por ejemplo:</p>
 *
 * <pre>
 * 1;false;El teclado no funciona
 * 2;true;No tengo conexión a Internet
 * </pre>
 *
 * <p>Se utiliza {@code split(";", 3)} para permitir que una descripción
 * contenga el carácter {@code ;}.</p>
 *
 * @version 1.0
 */
public class ArchivoTicket {

    /**
     * Ruta del fichero de persistencia.
     */
    private final Path ruta;

    /**
     * Crea un gestor utilizando {@code tickets.txt} como fichero.
     */
    public ArchivoTicket() {
        this(Path.of("tickets.txt"));
    }

    /**
     * Crea un gestor utilizando una ruta determinada.
     *
     * @param ruta ruta del fichero.
     * @throws IllegalArgumentException si la ruta es nula.
     */
    public ArchivoTicket(Path ruta) {

        if (ruta == null) {
            throw new IllegalArgumentException(
                    "La ruta del fichero no puede ser nula."
            );
        }

        this.ruta = ruta;
    }

    /**
     * Carga las incidencias almacenadas en el fichero.
     *
     * <p>Si el fichero no existe, se devuelve una lista vacía.</p>
     *
     * <p>Todo el contenido se valida antes de devolver las incidencias.
     * De esta forma, un fichero con datos inválidos no produce una carga
     * parcial.</p>
     *
     * @return lista de incidencias cargadas.
     * @throws IOException si se produce un error de lectura.
     * @throws IllegalArgumentException si el contenido del fichero
     *         no es válido.
     */
    public List<Ticket> cargar() throws IOException {

        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        if (!Files.isRegularFile(ruta)) {
            throw new IOException(
                    "La ruta indicada no corresponde a un fichero válido."
            );
        }

        List<String> lineas = Files.readAllLines(
                ruta,
                StandardCharsets.UTF_8
        );

        List<Ticket> ticketsCargados = new ArrayList<>();
        Set<Integer> identificadores = new HashSet<>();

        int numeroLinea = 0;

        for (String linea : lineas) {

            numeroLinea++;

            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] partes = linea.split(";", 3);

            if (partes.length != 3) {
                throw new IllegalArgumentException(
                        "Formato incorrecto en la línea "
                                + numeroLinea
                                + "."
                );
            }

            int identificador;

            try {

                identificador = Integer.parseInt(
                        partes[0].trim()
                );

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                        "El identificador de la línea "
                                + numeroLinea
                                + " no es válido."
                );
            }

            if (identificador <= 0) {
                throw new IllegalArgumentException(
                        "El identificador de la línea "
                                + numeroLinea
                                + " debe ser positivo."
                );
            }

            if (!identificadores.add(identificador)) {
                throw new IllegalArgumentException(
                        "Identificador duplicado: "
                                + identificador
                                + "."
                );
            }

            String estado = partes[1]
                    .trim()
                    .toLowerCase();

            boolean cerrado;

            if (estado.equals("true")) {
                cerrado = true;
            } else if (estado.equals("false")) {
                cerrado = false;
            } else {
                throw new IllegalArgumentException(
                        "Estado inválido en la línea "
                                + numeroLinea
                                + "."
                );
            }

            String descripcion = partes[2];

            try {

                Ticket ticket = new Ticket(
                        identificador,
                        descripcion,
                        cerrado
                );

                ticketsCargados.add(ticket);

            } catch (IllegalArgumentException e) {

                throw new IllegalArgumentException(
                        "Datos inválidos en la línea "
                                + numeroLinea
                                + ": "
                                + e.getMessage(),
                        e
                );
            }
        }

        return ticketsCargados;
    }

    /**
     * Guarda las incidencias en el fichero.
     *
     * <p>El contenido anterior del fichero se sustituye completamente.</p>
     *
     * @param tickets incidencias que se desean guardar.
     * @throws IOException si se produce un error de escritura.
     * @throws IllegalArgumentException si la lista es nula, contiene
     *         elementos nulos o contiene identificadores duplicados.
     */
    public void guardar(List<Ticket> tickets) throws IOException {

        if (tickets == null) {
            throw new IllegalArgumentException(
                    "La lista de incidencias no puede ser nula."
            );
        }

        Set<Integer> identificadores = new HashSet<>();
        List<String> lineas = new ArrayList<>();

        for (Ticket ticket : tickets) {

            if (ticket == null) {
                throw new IllegalArgumentException(
                        "La lista no puede contener incidencias nulas."
                );
            }

            if (!identificadores.add(
                    ticket.getIdentificador()
            )) {
                throw new IllegalArgumentException(
                        "No se puede guardar porque hay "
                                + "identificadores duplicados."
                );
            }

            String linea =
                    ticket.getIdentificador()
                            + ";"
                            + ticket.estaCerrado()
                            + ";"
                            + ticket.getDescripcion();

            lineas.add(linea);
        }

        Path padre = ruta.toAbsolutePath().getParent();

        if (padre != null && !Files.exists(padre)) {
            Files.createDirectories(padre);
        }

        Files.write(
                ruta,
                lineas,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        );
    }

    /**
     * Obtiene la ruta del fichero utilizado.
     *
     * @return ruta del fichero.
     */
    public Path getRuta() {
        return ruta;
    }
}