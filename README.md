# TP8 - Inventario de productos

Requiere Java 25 o superior y Maven.

Copiar `.env.example` como `.env` y ejecutar desde la carpeta del proyecto:

```powershell
Copy-Item .env.example .env
mvn spring-boot:run
```

La API usa `http://localhost:8080/productos`.

| Método | Ruta | Resultado |
| --- | --- | --- |
| POST | /productos | Crea un producto (201) |
| GET | /productos | Lista todos los productos (200) |
| GET | /productos/{id} | Obtiene un producto (200 o 404) |
| PUT | /productos/{id} | Actualiza un producto (200, 400 o 404) |
| DELETE | /productos/{id} | Elimina un producto (204 o 404) |
| GET | /productos/buscar?nombre=Teclado%20mecánico | Busca por nombre exacto (200 o 404) |

Para crear o actualizar:

```json
{
  "nombre": "Teclado mecánico",
  "precio": 45000.50,
  "stock": 25
}
```

La respuesta incluye `id` y `disponible`, que es `true` cuando el stock es mayor a cero.

Importar `postman/tp8armanascos.postman_collection.json` y ejecutar la colección en orden. Usa un producto de prueba que elimina al terminar.

La consola H2 está en `http://localhost:8080/h2-console`. La URL JDBC es `jdbc:h2:file:./data/tp8`, el usuario es `sa` y la contraseña está vacía según `.env.example`.

```sql
SELECT * FROM producto;
SELECT * FROM "flyway_schema_history";
```

Flyway aplica las tres migraciones al iniciar. La base se guarda en `data/`. `.env`, `data/` y `target/` están excluidos de Git.
