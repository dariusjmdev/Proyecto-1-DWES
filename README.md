## Instrucciones de ejecución

### Requisitos previos

- Tener instalado **Java**.
- Tener instalado **Maven**.
- Tener el proyecto descargado o clonado desde GitHub.

### Ejecutar el proyecto

Desde la carpeta raíz del proyecto, ejecutar:

```bash
mvn compile
mvn exec:java
```

También es posible ejecutar la aplicación directamente desde el IDE ejecutando la clase `AplicacionHelpDesk`.

Al iniciar la aplicación, se cargan automáticamente las incidencias almacenadas en `tickets.txt`, si el archivo existe.

### Ejecutar las pruebas

Para ejecutar todas las pruebas unitarias:

```bash
mvn test
```

Las pruebas verifican el funcionamiento de las clases principales, la validación de datos, la gestión de incidencias, las estadísticas y la persistencia.

### Persistencia

El archivo `tickets.txt` debe encontrarse en el directorio de trabajo del proyecto.

Si el archivo no existe, la aplicación comienza con una colección vacía de incidencias.

Para guardar los cambios realizados durante la ejecución, se debe seleccionar la opción **6. Guardar incidencias** del menú.
## Responsabilidades de las clases

### `Ticket`

Representa una incidencia individual.

Sus responsabilidades son:

- Almacenar el identificador de la incidencia.
- Almacenar la descripción.
- Mantener el estado de la incidencia.
- Validar los datos necesarios para crear una incidencia válida.
- Permitir cerrar una incidencia mediante el método `cerrar()`.
- Impedir modificaciones del identificador y la descripción después de la creación.

### `GestorTickets`

Se encarga de gestionar la colección de incidencias.

Sus responsabilidades son:

- Almacenar las incidencias.
- Generar identificadores positivos y únicos.
- Crear nuevas incidencias.
- Buscar incidencias por identificador.
- Añadir incidencias recuperadas desde el archivo.
- Calcular las estadísticas de incidencias abiertas y cerradas.
- Devolver una copia de la colección para proteger su estructura interna.

### `ArchivoTickets`

Se encarga de la persistencia de las incidencias.

Sus responsabilidades son:

- Leer las incidencias desde `tickets.txt`.
- Reconstruir las incidencias almacenadas.
- Guardar las incidencias en el archivo.
- Validar los datos recuperados.
- Detectar identificadores duplicados o datos inválidos.
- Gestionar el caso en el que el archivo no exista.

### `AplicacionHelpDesk`

Es la clase encargada de la interacción con el usuario.

Sus responsabilidades son:

- Mostrar el menú principal.
- Leer las entradas mediante `Scanner`.
- Mostrar los mensajes por consola.
- Gestionar las diferentes opciones del menú.
- Coordinar las operaciones entre `GestorTickets` y `ArchivoTickets`.
- Cargar los datos al iniciar la aplicación.
- Controlar la salida del programa.
- ## Limitaciones conocidas

- La aplicación funciona mediante una **interfaz de consola**, por lo que no dispone de interfaz gráfica.
- La persistencia se realiza mediante un archivo de texto (`tickets.txt`), no mediante una base de datos.
- El guardado de las incidencias es **manual** mediante la opción correspondiente del menú; no existe guardado automático al cerrar el programa.
- La aplicación está diseñada para ejecutarse de forma local y no permite gestionar incidencias de forma simultánea entre varios usuarios.
- No existe un sistema de usuarios, autenticación o permisos.
- Las incidencias únicamente pueden encontrarse mediante su identificador y no mediante búsquedas avanzadas por descripción.
