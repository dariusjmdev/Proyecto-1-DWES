package DWES;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Se encarga exclusivamente de leer y escribir tickets en un archivo de texto.
 *
 * <p>El formato de cada línea es:</p>
 *
 * <pre>
 * id;estado;descripcion
 * </pre>
 *
 * <p>El estado se representa mediante {@code true} para cerrado y
 * {@code false} para abierto.</p>
 *
 * <p>La descripción puede contener punto y coma, por lo que el separador
 * solo se interpreta en las dos primeras posiciones.</p>
 */
public class ArchivoTicket {

    private final Path ruta;

    /**
     * Crea un gestor de archivo que utiliza tickets.txt.
     */
    public ArchivoTicket() {
        this(Path.of("tickets.txt"));
    }

    /**
     * Crea un gestor de archivo para una ruta concreta.
     *
     * @param ruta ruta del archivo de persistencia.
     */
    public ArchivoTicket(Path ruta) {

        if (ruta == null) {
            throw new IllegalArgumentException(
                    "La ruta no puede ser nula."
            );
        }

        this.ruta = ruta;
    }

    /**
     * Carga todas las incidencias del archivo.
     *
     * <p>Si el archivo no existe, se devuelve una colección vacía.
     * Si existe pero contiene algún dato inválido o identificadores repetidos,
     * se lanza una excepción y no se devuelve una carga parcial.</p>
     *
     * @return lista completa de tickets recuperados.
     * @throws IOException si el archivo no puede leerse o contiene datos inválidos.
     */
    public List<Ticket> cargar() throws IOException {

        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        List<Ticket> cargados = new ArrayList<>();
        Set<Integer> identificadores = new HashSet<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

            String linea;
            int numeroLinea = 0;

            while ((linea = reader.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty()) {
                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": datos inválidos."
                    );
                }

                /*
                 * Se divide como máximo en tres partes.
                 *
                 * Esto permite que la descripción contenga ';'.
                 */
                String[] partes = linea.split(";", 3);

                if (partes.length != 3) {
                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": formato inválido."
                    );
                }

                int identificador;

                try {
                    identificador = Integer.parseInt(
                            partes[0].trim()
                    );

                } catch (NumberFormatException e) {

                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": identificador inválido.",
                            e
                    );
                }

                if (identificador <= 0) {
                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": el identificador debe ser positivo."
                    );
                }

                if (!identificadores.add(identificador)) {
                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": identificador repetido: "
                                    + identificador
                    );
                }

                boolean cerrado;

                if ("true".equalsIgnoreCase(partes[1].trim())) {

                    cerrado = true;

                } else if ("false".equalsIgnoreCase(partes[1].trim())) {

                    cerrado = false;

                } else {

                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": estado inválido."
                    );
                }

                try {

                    cargados.add(
                            new Ticket(
                                    identificador,
                                    partes[2],
                                    cerrado
                            )
                    );

                } catch (IllegalArgumentException e) {

                    throw new IOException(
                            "Línea " + numeroLinea +
                                    ": ticket inválido.",
                            e
                    );
                }
            }
        }

        return cargados;
    }

    /**
     * Guarda la colección completa, sustituyendo el contenido anterior.
     *
     * @param tickets tickets que se desean guardar.
     * @throws IOException si no se puede escribir el archivo.
     * @throws IllegalArgumentException si la colección o alguno de sus
     *                                  tickets es nulo.
     */
    public void guardar(List<Ticket> tickets) throws IOException {

        if (tickets == null) {
            throw new IllegalArgumentException(
                    "La colección no puede ser nula."
            );
        }

        Set<Integer> identificadores = new HashSet<>();

        for (Ticket ticket : tickets) {

            if (ticket == null) {
                throw new IllegalArgumentException(
                        "La colección no puede contener tickets nulos."
                );
            }

            if (!identificadores.add(ticket.getIdentificador())) {
                throw new IllegalArgumentException(
                        "No se pueden guardar identificadores repetidos."
                );
            }
        }

        Path padre = ruta.toAbsolutePath().getParent();

        if (padre != null) {
            Files.createDirectories(padre);
        }

        /*
         * newBufferedWriter() sustituye el contenido anterior.
         */
        try (BufferedWriter writer =
                     Files.newBufferedWriter(
                             ruta,
                             StandardCharsets.UTF_8
                     )) {

            for (Ticket ticket : tickets) {

                writer.write(
                        ticket.getIdentificador()
                                + ";"
                                + ticket.estaCerrado()
                                + ";"
                                + ticket.getDescripcion()
                );

                writer.newLine();
            }
        }
    }

    /**
     * @return ruta utilizada para la persistencia.
     */
    public Path getRuta() {
        return ruta;
    }
}