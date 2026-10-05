package DWES;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada de la aplicación de consola HelpDesk.
 *
 * <p>Contiene el menú, la lectura mediante Scanner, los mensajes de consola
 * y la coordinación entre GestorTickets y ArchivoTickets.</p>
 */
public class AplicacionHelpDesk {

    private final GestorTickets gestor;
    private final ArchivoTicket archivo;
    private final Scanner scanner;

    /**
     * Crea una aplicación utilizando tickets.txt.
     */
    public AplicacionHelpDesk() {
        this(
                new GestorTickets(),
                new ArchivoTicket(),
                new Scanner(System.in)
        );
    }

    /**
     * Constructor inyectable.
     *
     * @param gestor gestor de incidencias.
     * @param archivo persistencia de incidencias.
     * @param scanner lector de teclado.
     */
    public AplicacionHelpDesk(
            GestorTickets gestor,
            ArchivoTicket archivo,
            Scanner scanner) {

        if (gestor == null ||
                archivo == null ||
                scanner == null) {

            throw new IllegalArgumentException(
                    "Los componentes de la aplicación no pueden ser nulos."
            );
        }

        this.gestor = gestor;
        this.archivo = archivo;
        this.scanner = scanner;
    }

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de línea de comandos, no utilizados.
     */
    public static void main(String[] args) {

        AplicacionHelpDesk aplicacion =
                new AplicacionHelpDesk();

        if (!aplicacion.cargarDatosIniciales()) {
            return;
        }

        aplicacion.ejecutar();
    }

    /**
     * Carga las incidencias al arrancar.
     *
     * @return true si la carga ha sido correcta;
     *         false si el archivo es inválido o no se puede leer.
     */
    public boolean cargarDatosIniciales() {

        try {

            List<Ticket> cargados =
                    archivo.cargar();

            for (Ticket ticket : cargados) {
                gestor.añadirTicket(ticket);
            }

            if (cargados.isEmpty()) {

                System.out.println(
                        "No hay archivo de datos. "
                                + "Se inicia una colección vacía."
                );

            } else {

                System.out.println(
                        "Incidencias cargadas correctamente: "
                                + cargados.size()
                );
            }

            return true;

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "No se han podido cargar las incidencias: "
                            + e.getMessage()
            );

            System.out.println(
                    "La aplicación se cerrará para no trabajar "
                            + "con una carga parcial."
            );

            return false;
        }
    }

    /**
     * Ejecuta el menú principal hasta que el usuario selecciona salir.
     */
    public void ejecutar() {

        boolean salir = false;

        while (!salir) {

            mostrarMenu();

            int opcion =
                    leerEntero("Operación: ");

            switch (opcion) {

                case 1 -> crearIncidencia();

                case 2 -> listarIncidencias();

                case 3 -> buscarIncidencia();

                case 4 -> cerrarIncidencia();

                case 5 -> mostrarEstadisticas();

                case 6 -> guardarIncidencias();

                case 0 -> salir = true;

                default ->
                        System.out.println(
                                "Opción incorrecta. "
                                        + "Inténtalo de nuevo."
                        );
            }
        }

        System.out.println(
                "Programa finalizado."
        );

        System.out.println(
                "Recuerda guardar las incidencias antes "
                        + "de salir si has realizado cambios."
        );
    }

    /**
     * Muestra el menú principal.
     */
    public void mostrarMenu() {

        System.out.println();
        System.out.println("HELPDESK DEL CENTRO");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia por identificador");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Guardar incidencias");
        System.out.println("0. Salir");
    }

    /**
     * Solicita una descripción y crea una incidencia.
     */
    public void crearIncidencia() {

        System.out.print("Descripción: ");

        String descripcion =
                scanner.nextLine();

        try {

            Ticket ticket =
                    gestor.crearTicket(descripcion);

            System.out.println(
                    "Incidencia creada correctamente "
                            + "con ID "
                            + ticket.getIdentificador()
                            + "."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "No se ha creado la incidencia: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Lista todas las incidencias.
     */
    public void listarIncidencias() {

        List<Ticket> tickets =
                gestor.getTickets();

        if (tickets.isEmpty()) {

            System.out.println(
                    "No hay ninguna incidencia."
            );

            return;
        }

        for (Ticket ticket : tickets) {

            System.out.println(
                    ticket.getIdentificador()
                            + " - "
                            + ticket.getDescripcion()
                            + " - "
                            + ticket.getEstado()
            );
        }
    }

    /**
     * Busca una incidencia por identificador.
     */
    public void buscarIncidencia() {

        int identificador =
                leerIdentificador();

        Ticket ticket =
                gestor.buscarPorId(identificador);

        if (ticket == null) {

            System.out.println(
                    "No existe una incidencia "
                            + "con ese identificador."
            );

            return;
        }

        System.out.println(
                ticket.getIdentificador()
                        + " - "
                        + ticket.getDescripcion()
                        + " - "
                        + ticket.getEstado()
        );
    }

    /**
     * Cierra una incidencia distinguiendo entre inexistente,
     * abierta y ya cerrada.
     */
    public void cerrarIncidencia() {

        int identificador =
                leerIdentificador();

        Ticket ticket =
                gestor.buscarPorId(identificador);

        if (ticket == null) {

            System.out.println(
                    "No existe una incidencia "
                            + "con ese identificador."
            );

        } else if (ticket.estaCerrado()) {

            System.out.println(
                    "La incidencia ya estaba cerrada."
            );

        } else {

            ticket.cerrar();

            System.out.println(
                    "Incidencia cerrada correctamente."
            );
        }
    }

    /**
     * Muestra las estadísticas calculadas por el gestor.
     */
    public void mostrarEstadisticas() {

        System.out.println(
                "Total: " + gestor.getTotal()
        );

        System.out.println(
                "Abiertas: " + gestor.getAbiertas()
        );

        System.out.println(
                "Cerradas: " + gestor.getCerradas()
        );
    }

    /**
     * Guarda la colección completa en disco.
     */
    public void guardarIncidencias() {

        try {

            archivo.guardar(
                    gestor.getTickets()
            );

            System.out.println(
                    "Incidencias guardadas correctamente."
            );

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "No se han podido guardar las incidencias: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Solicita un identificador y repite la petición mientras
     * la entrada no sea numérica.
     *
     * @return identificador introducido por el usuario.
     */
    private int leerIdentificador() {

        System.out.print("Identificador: ");

        while (true) {

            String entrada =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                        entrada.trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Identificador no válido. "
                                + "Introduce un número: "
                );
            }
        }
    }

    /**
     * Lee una opción numérica del menú.
     *
     * @param mensaje mensaje mostrado al usuario.
     * @return entero introducido o -1 si no era numérico.
     */
    private int leerEntero(String mensaje) {

        System.out.print(mensaje);

        String entrada =
                scanner.nextLine();

        try {

            return Integer.parseInt(
                    entrada.trim()
            );

        } catch (NumberFormatException e) {

            return -1;
        }
    }
}