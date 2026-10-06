package DWES;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación principal del sistema HelpDesk.
 *
 * <p>Gestiona la interacción con el usuario mediante consola y coordina
 * las operaciones realizadas por {@link GestorTickets} y
 * {@link ArchivoTicket}.</p>
 *
 * <p>El menú permite crear, listar, buscar y cerrar incidencias,
 * consultar estadísticas y guardar los datos.</p>
 *
 * @version 1.0
 */
public class AplicacionHelpDesk {

    /**
     * Gestor de las incidencias.
     */
    private final GestorTickets gestor;

    /**
     * Gestor de persistencia de las incidencias.
     */
    private final ArchivoTicket archivo;

    /**
     * Scanner utilizado para leer la entrada del usuario.
     */
    private final Scanner scanner;

    /**
     * Crea una nueva aplicación HelpDesk.
     */
    public AplicacionHelpDesk() {
        gestor = new GestorTickets();
        archivo = new ArchivoTicket();
        scanner = new Scanner(System.in);
    }

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos recibidos desde la línea de comandos.
     */
    public static void main(String[] args) {

        AplicacionHelpDesk aplicacion =
                new AplicacionHelpDesk();

        aplicacion.cargarDatosIniciales();
        aplicacion.ejecutar();
    }

    /**
     * Carga las incidencias almacenadas al iniciar la aplicación.
     *
     * <p>Si el fichero no existe, la aplicación comienza con una colección
     * vacía. Si el fichero contiene datos inválidos, se informa del error
     * y no se incorporan esos datos al gestor.</p>
     */
    private void cargarDatosIniciales() {

        try {

            List<Ticket> tickets = archivo.cargar();

            for (Ticket ticket : tickets) {
                gestor.añadirTicket(ticket);
            }

            if (tickets.isEmpty()) {

                System.out.println(
                        "No hay incidencias guardadas."
                );

            } else {

                System.out.println(
                        "Se han cargado "
                                + tickets.size()
                                + " incidencias."
                );
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "ERROR: no se han podido cargar "
                            + "las incidencias."
            );

            System.out.println(
                    "Motivo: " + e.getMessage()
            );

            System.out.println(
                    "La aplicación comenzará con "
                            + "una colección vacía."
            );
        }
    }

    /**
     * Ejecuta el menú principal.
     *
     * <p>El menú continúa ejecutándose hasta que el usuario selecciona
     * la opción 0.</p>
     */
    private void ejecutar() {

        boolean salir = false;

        while (!salir) {

            mostrarMenu();

            int opcion = leerEntero(
                    "Selecciona una opción: "
            );

            switch (opcion) {

                case 1:
                    crearIncidencia();
                    break;

                case 2:
                    listarIncidencias();
                    break;

                case 3:
                    buscarIncidencia();
                    break;

                case 4:
                    cerrarIncidencia();
                    break;

                case 5:
                    mostrarEstadisticas();
                    break;

                case 6:
                    guardarIncidencias();
                    break;

                case 0:
                    salir = true;
                    System.out.println(
                            "Programa finalizado."
                    );
                    break;

                default:
                    System.out.println(
                            "ERROR: opción no válida."
                    );
            }
        }

        scanner.close();
    }

    /**
     * Muestra el menú principal de la aplicación.
     */
    private void mostrarMenu() {

        System.out.println();
        System.out.println("==============================");
        System.out.println("          HELP DESK");
        System.out.println("==============================");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia por identificador");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Guardar incidencias");
        System.out.println("0. Salir");
        System.out.println("==============================");
    }

    /**
     * Crea una incidencia solicitando su descripción al usuario.
     *
     * <p>Si la descripción no es válida, se informa del error y no
     * se crea ninguna incidencia.</p>
     */
    private void crearIncidencia() {

        System.out.println();
        System.out.println("--- CREAR INCIDENCIA ---");

        System.out.print(
                "Introduce la descripción: "
        );

        String descripcion = scanner.nextLine();

        try {

            Ticket ticket = gestor.crearTicket(
                    descripcion
            );

            System.out.println(
                    "Incidencia creada correctamente."
            );

            System.out.println(
                    "Identificador: "
                            + ticket.getIdentificador()
            );

            System.out.println(
                    "Estado: "
                            + ticket.getEstado()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "No se ha podido crear la incidencia."
            );

            System.out.println(
                    "Motivo: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Muestra todas las incidencias registradas.
     *
     * <p>Si no existen incidencias, se informa al usuario.</p>
     */
    private void listarIncidencias() {

        System.out.println();
        System.out.println(
                "--- LISTA DE INCIDENCIAS ---"
        );

        List<Ticket> tickets = gestor.getTickets();

        if (tickets.isEmpty()) {

            System.out.println(
                    "No hay incidencias registradas."
            );

            return;
        }

        for (Ticket ticket : tickets) {

            System.out.println(
                    "ID: "
                            + ticket.getIdentificador()
                            + " | Descripción: "
                            + ticket.getDescripcion()
                            + " | Estado: "
                            + ticket.getEstado()
            );
        }
    }

    /**
     * Busca y muestra una incidencia mediante su identificador.
     */
    private void buscarIncidencia() {

        System.out.println();
        System.out.println(
                "--- BUSCAR INCIDENCIA ---"
        );

        int identificador = leerEntero(
                "Introduce el identificador: "
        );

        if (identificador <= 0) {

            System.out.println(
                    "El identificador debe ser positivo."
            );

            return;
        }

        Ticket ticket = gestor.buscarPorId(
                identificador
        );

        if (ticket == null) {

            System.out.println(
                    "No existe ninguna incidencia "
                            + "con ese identificador."
            );

            return;
        }

        System.out.println(
                "Incidencia encontrada:"
        );

        System.out.println(
                "ID: "
                        + ticket.getIdentificador()
        );

        System.out.println(
                "Descripción: "
                        + ticket.getDescripcion()
        );

        System.out.println(
                "Estado: "
                        + ticket.getEstado()
        );
    }

    /**
     * Cierra una incidencia mediante su identificador.
     *
     * <p>Se informa de forma diferente si la incidencia no existe,
     * ya estaba cerrada o se ha cerrado correctamente.</p>
     */
    private void cerrarIncidencia() {

        System.out.println();
        System.out.println(
                "--- CERRAR INCIDENCIA ---"
        );

        int identificador = leerEntero(
                "Introduce el identificador: "
        );

        if (identificador <= 0) {

            System.out.println(
                    "El identificador debe ser positivo."
            );

            return;
        }

        Ticket ticket = gestor.buscarPorId(
                identificador
        );

        if (ticket == null) {

            System.out.println(
                    "No existe ninguna incidencia "
                            + "con ese identificador."
            );

            return;
        }

        if (ticket.estaCerrado()) {

            System.out.println(
                    "La incidencia ya estaba cerrada."
            );

            return;
        }

        boolean cerrada = gestor.cerrarTicket(
                identificador
        );

        if (cerrada) {

            System.out.println(
                    "Incidencia cerrada correctamente."
            );

        } else {

            System.out.println(
                    "No se ha podido cerrar la incidencia."
            );
        }
    }

    /**
     * Muestra las estadísticas de las incidencias.
     *
     * <p>El cálculo de las estadísticas corresponde al gestor,
     * mientras que esta clase únicamente muestra los resultados.</p>
     */
    private void mostrarEstadisticas() {

        System.out.println();
        System.out.println(
                "--- ESTADÍSTICAS ---"
        );

        int total = gestor.getTotalTickets();
        int abiertos = gestor.getTicketsAbiertos();
        int cerrados = gestor.getTicketsCerrados();

        System.out.println(
                "Total: " + total
        );

        System.out.println(
                "Abiertas: " + abiertos
        );

        System.out.println(
                "Cerradas: " + cerrados
        );
    }

    /**
     * Guarda las incidencias actuales en el fichero.
     *
     * <p>El usuario debe utilizar esta opción antes de salir si desea
     * conservar los cambios realizados durante la ejecución.</p>
     */
    private void guardarIncidencias() {

        System.out.println();
        System.out.println(
                "--- GUARDAR INCIDENCIAS ---"
        );

        try {

            archivo.guardar(
                    gestor.getTickets()
            );

            System.out.println(
                    "Incidencias guardadas correctamente."
            );

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "ERROR: no se han podido guardar "
                            + "las incidencias."
            );

            System.out.println(
                    "Motivo: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Lee un número entero introducido por el usuario.
     *
     * <p>Si la entrada no es numérica, se solicita nuevamente
     * sin finalizar la aplicación.</p>
     *
     * @param mensaje mensaje mostrado antes de solicitar el número.
     * @return número entero introducido por el usuario.
     */
    private int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String entrada = scanner.nextLine().trim();

            try {

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Entrada no válida. "
                                + "Debes introducir un número entero."
                );
            }
        }
    }
}