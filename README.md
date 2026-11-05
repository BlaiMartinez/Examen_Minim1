# Gestión de flota de drones (DSA - EETAC)

Aplicación para gestionar una flota de drones: añadir drones y pilotos,
listar por horas de vuelo, almacén de mantenimiento y planes de vuelo.

## Ejecución

```bash
mvn test             # tests JUnit
mvn spring-boot:run  # servicio REST en http://localhost:8080
```

## Estructura

```
src/main/java/eetac/dsa/flota/
├── api/        IGestorFlota (interfaz) y GestorFlotaFacade (fachada Singleton con Log4J2)
├── modelo/     Dron, Piloto, Posicion, PlanDeVuelo
├── dto/        Objetos de transferencia para el REST
├── excepcion/  GestorException (base), GestorNotFoundException y GestorConflictException
└── web/        Controlador REST y traducción de errores a códigos HTTP
```

Los DTO (Data Transfer Object) son clases planas con solo los datos que viajan
en las peticiones y respuestas: en lugar de objetos de negocio con referencias
entre sí, guardan los identificadores (p. ej. `PlanDeVueloDTO` guarda
`idDron`/`idPiloto` en vez de los objetos `Dron` y `Piloto`). Así se evitan
ciclos al serializar/deserializar JSON, tal como recomienda el enunciado.

## Servicio REST

El REST permite realizar las (4) operaciones del enunciado: añadir
un nuevo dron, añadir un nuevo piloto, guardar un dron en el almacén
y crear un plan de vuelo.

| Operación | Método | Ruta | OK | Errores |
|---|---|---|---|---|
| Añadir dron | POST | /api/drones | 201 | 409 duplicado |
| Añadir piloto | POST | /api/pilotos | 201 | 409 duplicado |
| Guardar en almacén | POST | /api/almacen/{idDron} | 200 | 404 / 409 |
| Crear plan de vuelo | POST | /api/planes | 201 | 404 / 409 |
| Listado de drones (consulta) | GET | /api/drones | 200 | — |

El componente de la PARTE II (interfaz + fachada) implementa además
las demás operaciones del enunciado (listados y mantenimiento),
verificadas con los tests JUnit.

Fechas en formato ISO, p. ej. `"fechaHora": "2026-12-01T10:00:00"`.

Swagger UI disponible en `http://localhost:8080/swagger-ui/index.html`
(herramienta de documentación/prueba del REST, sin añadir operaciones).

## Estado

- PARTE I: dibujo de estructuras de datos y análisis de costes BigO hecho **a papel**.
- PARTE II: completa — interfaz `IGestorFlota`, fachada `GestorFlotaFacade` (Singleton)
  con trazas Log4J2 (INFO/ERROR/FATAL) y 4 tests JUnit verdes.
- PARTE III: completa — servicio REST con las (4) operaciones, DTOs y
  códigos de respuesta 201/200/404/409/400, documentado con Swagger.
