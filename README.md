# HelpDesk — Gestión de Incidencias

Aplicación de consola desarrollada en Java para la gestión de incidencias de un servicio de soporte técnico.

El proyecto permite crear, consultar, cerrar y listar incidencias, consultar estadísticas y almacenar la información de forma persistente mediante un archivo de texto.

El desarrollo se ha realizado siguiendo los requisitos establecidos en la práctica evaluable "HelpDesk: gestión de incidencias con pruebas y persistencia".

---

## 1. Descripción del proyecto

HelpDesk es una aplicación de consola desarrollada en Java que permite gestionar un conjunto de incidencias de soporte técnico.

Cada incidencia dispone de:

- Un identificador numérico único.
- Una descripción.
- Un estado que indica si está abierta o cerrada.

La aplicación proporciona un menú interactivo desde el que se pueden realizar las principales operaciones de gestión.

Además, el proyecto incorpora:

- Validación de datos de entrada.
- Control de errores.
- Encapsulación de los datos.
- Gestión automática de identificadores.
- Persistencia mediante archivo de texto.
- Pruebas automatizadas con JUnit 5.
- Documentación mediante Javadoc.
- Gestión del proyecto mediante Maven.
- Control de versiones mediante Git.

---

## 2. Objetivos

Los principales objetivos del proyecto son:

1. Implementar correctamente una aplicación de gestión de incidencias.
2. Aplicar principios de programación orientada a objetos.
3. Separar correctamente las responsabilidades de las diferentes clases.
4. Validar los datos tanto en la aplicación como en las clases de dominio.
5. Evitar que los errores de entrada provoquen el cierre inesperado del programa.
6. Implementar persistencia de datos mediante un archivo de texto.
7. Recuperar correctamente las incidencias al iniciar la aplicación.
8. Mantener la continuidad de los identificadores después de cargar datos.
9. Implementar pruebas unitarias con JUnit 5.
10. Utilizar Maven para la compilación y ejecución de las pruebas.
11. Documentar el código mediante Javadoc.
12. Mantener una estructura de proyecto clara y mantenible.

---

## 3. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java | Lenguaje principal |
| Maven | Gestión y construcción del proyecto |
| JUnit 5 | Pruebas automatizadas |
| Git | Control de versiones |
| IntelliJ IDEA | Entorno de desarrollo |
| UTF-8 | Codificación del archivo de persistencia |

---

## 4. Requisitos

Para ejecutar el proyecto es necesario disponer de:

- Java JDK 17 o superior.
- Maven.
- Un entorno de desarrollo compatible con Java, como IntelliJ IDEA.

Se recomienda comprobar la instalación mediante:

```bash
java -version
mvn -version
