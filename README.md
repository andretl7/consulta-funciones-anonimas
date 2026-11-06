# Trabajo de consulta — Funciones anónimas

## Parte 1 — Explorar la programación funcional en el mundo real
Antes de programar, investiga dónde y para qué se usa la programación funcional fuera del aula.
1. Busca dos casos reales de empresas, productos o proyectos de código abierto que usen programación funcional o funciones anónimas.
2. Para cada caso, explica qué problema resuelve, qué lenguaje o herramienta utiliza y qué ventaja le da el enfoque funcional. Incluye la fuente consultada.
3. Si encuentras un fragmento de código real que use una función anónima, inclúyelo y explica qué hace.

### Caso 1 — Apache Spark

**Problema que resuelve.** Apache Spark permite procesar grandes volúmenes de datos repartidos entre varias máquinas. Un ejemplo es analizar un archivo de registros y calcular cuántos caracteres tiene cada línea.

**Lenguaje y herramienta.** El ejemplo usa Python con PySpark, la API de Spark para ese lenguaje. La variable `lines` representa una colección distribuida de líneas, llamada RDD.

**Fragmento de la documentación**

```python
lineLengths = lines.map(lambda s: len(s))
```

La función anónima `lambda s: len(s)` recibe una línea `s` y devuelve su longitud. `map` genera un nuevo RDD que representa las longitudes. Spark ejecuta la transformación cuando una acción necesita el resultado.

**Ventaja del enfoque funcional.** La persona que programa escribe la regla para una sola línea. Spark se encarga de aplicarla a las distintas particiones del archivo, lo que permite procesarlas en paralelo.

[Fuente del caso y del fragmento — Guía de RDD de Apache Spark](https://spark.apache.org/docs/latest/rdd-programming-guide.html#rdd-operations)

### Caso 2 — pandas

**Problema que resuelve.** pandas permite analizar y transformar datos tabulares. Por ejemplo, en un informe con temperaturas en grados Celsius se puede agregar una columna con sus equivalentes en Fahrenheit.

**Lenguaje y herramienta.** El ejemplo usa Python, un `DataFrame` de pandas y su método `assign`.

**Fragmento de la documentación**

```python
df.assign(temp_f=lambda x: x.temp_c * 9 / 5 + 32)
```

La función anónima recibe la tabla `x`, toma la columna `temp_c` y aplica la fórmula de conversión a toda la columna. `assign` devuelve una tabla que conserva las columnas originales e incluye la nueva columna `temp_f`.

**Ventaja del enfoque funcional.** La fórmula queda junto al nombre de la columna que produce. pandas aplica la operación a la serie completa, y la tabla resultante se puede usar directamente en el siguiente paso del análisis.

[Fuente del caso y del fragmento — Documentación de pandas sobre `DataFrame.assign`](https://pandas.pydata.org/pandas-docs/version/1.5.3/reference/api/pandas.DataFrame.assign.html)


## Parte 2 — Entender: ¿qué es una función anónima?

### Instrucciones de la actividad

Responde con tus palabras y apoya cada respuesta con un fragmento corto de código. Explica qué es una función anónima, de dónde viene el nombre *lambda*, cómo se compara con una función nombrada, qué significa que las funciones sean de primera clase y qué es un *closure*.

### 1. ¿Qué es una función anónima y de dónde viene el nombre *lambda*?

Una función anónima es una función que se escribe sin darle un nombre al momento de definirla. Se puede usar directamente donde hace falta. Por ejemplo, en Scala, `(numero: Int) => numero * 2` recibe un entero y devuelve su doble.

```scala
println(((numero: Int) => numero * 2)(5))
// Salida: 10
```

El nombre *lambda* viene del cálculo lambda de Alonzo Church, que usa la letra griega `λ` para representar funciones. En el ejemplo de Scala, `=>` separa el parámetro de la operación que se realiza con él.

### 2. ¿En qué se diferencia de una función nombrada?

Una función nombrada se define con un nombre, como `esPar`, y después se puede usar mediante ese nombre. Una función anónima se puede escribir directamente dentro de `filter`. En este ejemplo, las dos formas usan la misma regla y dan el mismo resultado.

```scala
def esPar(numero: Int): Boolean = numero % 2 == 0

val numeros = List(1, 2, 3, 4, 5)

println(numeros.filter(esPar))                      // List(2, 4)
println(numeros.filter(numero => numero % 2 == 0))   // List(2, 4)
```

### 3. ¿Qué significa que las funciones sean de primera clase?

Significa que podemos tratar una función como un valor. Podemos guardarla en una variable, pasarla como argumento o devolverla como resultado de otra función. Por eso `map` y `filter` pueden recibir lambdas. `map` usa la función para transformar cada elemento y `filter` la usa para decidir cuáles conservar.

```scala
val sumarUno: Int => Int = numero => numero + 1
val numeros = List(1, 2, 3)

println(numeros.map(sumarUno))                       // List(2, 3, 4)
println(numeros.filter(numero => numero > 1))        // List(2, 3)
```

### 4. ¿Qué es un *closure*?

Un *closure*, o clausura, es una función que conserva acceso a variables del lugar donde fue creada. Aquí la lambda recibe `nota`, pero también usa `minimo`, que se definió fuera de la lambda como parámetro de `crearFiltro`.

```scala
def crearFiltro(minimo: Int): Int => Boolean =
  nota => nota >= minimo

val aprobar = crearFiltro(7)
val notas = List(5, 7, 9)

println(notas.filter(aprobar)) // List(7, 9)
```

Al llamar a `crearFiltro(7)`, la función que se devuelve conserva acceso a ese `7`. Así puede comparar cada nota con el mínimo aunque `crearFiltro` ya haya terminado.

## Parte 3 — Aplicar funciones anónimas

### Instrucciones de la actividad

Elige al menos cuatro tipos de problemas. Para cada uno, describe una situación real, muestra cómo resolverla con funciones anónimas en Scala, Java con Streams y Python con `lambda`, y presenta la salida de las tres versiones.

### 3.1 Catálogo de tipos de problemas

Elegí selección, transformación, validación y acumulación. Para los cuatro ejercicios uso un catálogo de 12 productos. Cada producto tiene nombre, categoría, precio unitario en dólares y cantidad en stock.

En Scala defino una `case class Producto` y creo la lista directamente con `List(Producto(...), ...)`. En Java uso un `record` y en Python una `dataclass`. Los tres programas tienen los mismos productos en el mismo orden.

Los programas completos están en [Scala](scala/Problemas.scala), [Java](java/Problemas.java) y [Python](python/problemas.py).

#### 1. Selección — Identificar productos agotados

**Situación real.** Antes de hacer un pedido al proveedor, el encargado de compras necesita saber qué productos ya tienen cero unidades en stock.

**Scala**

```scala
val agotados = productos.filter(p => p.stock == 0).map(p => p.nombre)
```

**Java**

```java
var agotados = productos.stream()
    .filter(p -> p.stock() == 0)
    .map(p -> p.nombre())
    .collect(Collectors.joining(", "));
```

**Python**

```python
agotados = filter(lambda p: p.stock == 0, productos)
nombres = ", ".join(map(lambda p: p.nombre, agotados))
```

`filter` conserva los productos con stock cero y `map` obtiene sus nombres.

**Salida de cada versión**

| Scala | Java | Python |
| --- | --- | --- |
| Pasta, Teclado | Pasta, Teclado | Pasta, Teclado |

#### 2. Transformación — Calcular el valor del stock por producto

**Situación real.** Contabilidad necesita registrar cuánto valen las existencias de cada producto. Por ejemplo, si hay 40 unidades de arroz a 4 dólares cada una, esa línea del inventario vale 160 dólares.

**Scala**

```scala
val valores = productos.map(p => p.precio * p.stock)
```

**Java**

```java
var valores = productos.stream()
    .map(p -> Integer.toString(p.precio() * p.stock()))
    .collect(Collectors.joining(", "));
```

**Python**

```python
valores = map(lambda p: p.precio * p.stock, productos)
```

La lambda transforma cada producto en un importe al multiplicar su precio por su stock. En Java, el importe se convierte a texto para mostrar los resultados separados por comas.

**Salida de cada versión, en USD**

| Lenguaje | Valor por producto, en el orden de la lista |
| --- | --- |
| Scala | 160, 120, 0, 66, 96, 80, 216, 0, 450, 150, 60, 150 |
| Java | 160, 120, 0, 66, 96, 80, 216, 0, 450, 150, 60, 150 |
| Python | 160, 120, 0, 66, 96, 80, 216, 0, 450, 150, 60, 150 |

#### 3. Validación — Revisar el catálogo antes de publicarlo

**Situación real.** La tienda necesita verificar que todos los precios sean mayores que cero antes de publicar su catálogo en línea. También necesita saber si hay al menos un producto agotado para mostrar un aviso.

**Scala**

```scala
val existeAgotado = productos.exists(p => p.stock == 0)
val preciosValidos = productos.forall(p => p.precio > 0)
```

**Java**

```java
var existeAgotado = productos.stream().anyMatch(p -> p.stock() == 0);
var preciosValidos = productos.stream().allMatch(p -> p.precio() > 0);
```

**Python**

```python
existe_agotado = any(map(lambda p: p.stock == 0, productos))
precios_validos = all(map(lambda p: p.precio > 0, productos))
```

Para activar el aviso basta encontrar un producto agotado. Para validar los precios, todos los productos deben cumplir la condición.

**Salida de cada versión**

| Lenguaje | ¿Hay algún agotado? | ¿Todos los precios son positivos? |
| --- | --- | --- |
| Scala | Sí | Sí |
| Java | Sí | Sí |
| Python | Sí | Sí |

#### 4. Acumulación — Calcular el valor total del inventario

**Situación real.** Al cierre del mes, el administrador necesita un solo importe que represente el valor de todas las existencias de la tienda.

**Scala**

```scala
val total = productos.foldLeft(0)((subtotal, p) => subtotal + p.precio * p.stock)
```

**Java**

```java
var total = productos.stream()
    .mapToInt(p -> p.precio() * p.stock())
    .reduce(0, (a, b) -> a + b);
```

**Python**

```python
from functools import reduce

total = reduce(lambda subtotal, p: subtotal + p.precio * p.stock, productos, 0)
```

La suma empieza en cero. En Scala y Python, la lambda agrega al subtotal el importe del producto actual. En Java, `mapToInt` obtiene los importes y `reduce` los suma.

**Salida de cada versión**

| Scala | Java | Python |
| --- | --- | --- |
| 1548 USD | 1548 USD | 1548 USD |

### Capturas de ejecución

- [Captura de Scala](img/scala.png)
- [Captura de Java](img/java.png)
- [Captura de Python](img/python.png)

## Parte 4 — Ampliar: cuatro operaciones nuevas

1. Elige cuatro operaciones de colecciones distintas de las vistas en clase.
2. Para cada operación, explica qué hace, qué recibe y qué devuelve su lambda. Incluye un ejemplo en Scala con la lista de la Parte 3, su salida y una situación real donde la usarías.
3. Plantea una pregunta sobre la lista y respóndela con una cadena que combine dos operaciones nuevas y una vista en clase. Explica cada paso.

### 4.1 Operaciones elegidas

Elegí `sortBy` para ordenar, `groupBy` para agrupar, `partition` para dividir la lista y `find` para buscar. Los ejemplos usan los mismos 12 productos de la Parte 3. El programa completo está en [Operaciones.sc](operaciones/Operaciones.sc).

### 4.2 Fichas de las operaciones

#### 1. `sortBy` — Ordenar por precio

**Qué hace.** Ordena los productos usando el valor que devuelve la lambda como clave.

**Lambda.** `p => p.precio` recibe un `Producto` y devuelve un `Int` con su precio. Scala usa ese número para ordenar de menor a mayor.

**Situación real.** Una tienda en línea quiere mostrar los productos más baratos primero. Es un tipo nuevo de problema, porque se quiere ordenar toda la lista.

```scala
val ordenados = productos.sortBy(p => p.precio)
println(ordenados.map(p => p.nombre).mkString(", "))
```

**Salida.** Lapiz, Pasta, Jabon, Arroz, Cuaderno, Cafe, Toalla, Mouse, Lampara, Mochila, Teclado, Monitor.

#### 2. `groupBy` — Agrupar por categoría

**Qué hace.** Crea un mapa donde cada categoría tiene su propia lista de productos.

**Lambda.** `p => p.categoria` recibe un `Producto` y devuelve un `String` con la categoría que se usará como clave.

**Situación real.** El encargado de la tienda quiere organizar el catálogo en secciones, como Alimentos y Hogar. Es un tipo nuevo de problema, porque cada elemento se coloca en el grupo que le corresponde.

```scala
val grupos = productos.groupBy(p => p.categoria)
```

**Salida al mostrar los nombres de cada grupo**

| Categoría | Productos |
| --- | --- |
| Alimentos | Arroz, Cafe, Pasta |
| Hogar | Jabon, Toalla, Lampara |
| Papeleria | Cuaderno, Lapiz, Mochila |
| Tecnologia | Mouse, Teclado, Monitor |

#### 3. `partition` — Separar los productos con stock bajo

**Qué hace.** Divide la lista en dos. La primera contiene los productos que cumplen la condición y la segunda contiene el resto.

**Lambda.** `p => p.stock < 5` recibe un `Producto` y devuelve un `Boolean` que indica si tiene menos de cinco unidades.

**Situación real.** El encargado de bodega necesita separar los artículos con stock bajo para revisarlos primero. Se parece a la selección de la Parte 3, con la diferencia de que aquí se conservan ambos grupos.

```scala
val (criticos, resto) = productos.partition(p => p.stock < 5)
```

**Salida al mostrar los nombres**

- **Stock menor a 5:** Pasta, Lampara, Teclado, Monitor.
- **Resto:** Arroz, Cafe, Jabon, Toalla, Mouse, Cuaderno, Lapiz, Mochila.

#### 4. `find` — Buscar el primer producto agotado

**Qué hace.** Devuelve la primera coincidencia como `Some(producto)`. Si no encuentra ninguna, devuelve `None`.

**Lambda.** `p => p.stock == 0` recibe un `Producto` y devuelve un `Boolean` que indica si está agotado.

**Situación real.** Un empleado quiere revisar el primer faltante según el orden del catálogo. Se parece a la selección de la Parte 3, pero aquí interesa una sola coincidencia.

```scala
val primerAgotado = productos.find(p => p.stock == 0)
println(primerAgotado.map(p => p.nombre))
```

**Salida.** `Some(Pasta)`.

### 4.3 Combinar operaciones

**Pregunta.** ¿Cuál es el producto de tecnología más caro entre los que tienen menos de cinco unidades en stock?

```scala
val prioridad = productos
  .filter(p => p.stock < 5)
  .sortBy(p => -p.precio)
  .find(p => p.categoria == "Tecnologia")

println(prioridad.map(p => p.nombre))
```

1. `filter`, vista en clase, conserva los productos que tienen menos de cinco unidades.
2. `sortBy`, una operación nueva, usa el precio con signo negativo para ordenar del más caro al más barato.
3. `find`, otra operación nueva, toma el primer producto de tecnología en ese orden.

**Salida.** `Some(Monitor)`. El monitor cuesta 150 USD y tiene 3 unidades en stock.

[Captura de ejecución de la Parte 4](img/operaciones.png)

## Parte 5 — Comparar y concluir

### Instrucciones de la actividad

Compara Scala, Java y Python a partir de las soluciones de la Parte 3. Explica en qué lenguaje te resultó más natural resolver los ejercicios y en qué tipo de problema encontraste más diferencias. Para concluir, explica la importancia de las funciones anónimas al trabajar con listas y relaciona tu respuesta con un caso de la Parte 1.

### 5.1 Scala, Java y Python frente a frente

| Criterio | Scala | Java | Python |
| --- | --- | --- | --- |
| Sintaxis de la lambda | `p => p.stock == 0` | `p -> p.stock() == 0` | `lambda p: p.stock == 0` |
| Cómo se declara el tipo | Scala deduce que `p` es un `Producto` porque la lista contiene productos. También puedo declarar el tipo de la función como `Producto => Boolean`. | Java deduce el tipo de `p` a partir del Stream. Para `filter`, la lambda tiene como tipo objetivo `Predicate<Producto>`. | `lambda` se escribe sin tipos en los parámetros. Python comprueba los tipos al ejecutar el código. |
| Operaciones usadas con la lista | `filter`, `map`, `exists`, `forall` y `foldLeft`. | `filter`, `map`, `anyMatch`, `allMatch`, `mapToInt` y `reduce` mediante Streams. | `filter`, `map`, `any`, `all` y `reduce` de `functools`. |
| Restricción del lenguaje | Si Scala no puede deducir el tipo del parámetro por el contexto, debo indicarlo, por ejemplo con `(p: Producto) => p.stock == 0`. | Una lambda necesita una interfaz funcional como tipo objetivo. Por ejemplo, `filter` espera un predicado que devuelva un booleano. | El cuerpo de una lambda se limita a una expresión. |
| Legibilidad según mi experiencia | Me resultó el más claro porque puedo aplicar las operaciones directamente sobre `productos` y leer los pasos en orden. | Entendí los nombres de las operaciones, pero tuve que seguir más llamadas, como `stream()` y `collect()`, para mostrar el resultado de la selección. | La sintaxis me pareció corta, aunque en la validación tuve que leer expresiones anidadas como `any(map(...))`. |

**¿En qué lenguaje me resultó más natural resolver los problemas?**

En lo personal, me gustó más Scala. En el ejercicio de selección pude escribir `productos.filter(p => p.stock == 0).map(p => p.nombre)` y leerlo en el mismo orden en que pensé la solución. Primero filtro los productos agotados y después obtengo sus nombres. También me parecieron claros `exists` y `forall`, porque se parecen a las preguntas que quería responder sobre el inventario.

**¿En qué tipo de problema encontré más diferencias?**

Encontré más diferencias en la acumulación. En Scala usé `foldLeft` y en Python usé `reduce`. En ambos casos, la lambda recibe el subtotal y el producto actual, calcula el valor de ese producto y lo suma. En Java lo hice en dos pasos. Primero usé `mapToInt` para convertir cada producto en su importe y después `reduce` para sumar esos importes. Los tres programas dieron el mismo total de 1548 USD.

### 5.2 Conclusión

Las funciones anónimas me permitieron escribir la regla de cada ejercicio justo donde la necesitaba. Por ejemplo, en la selección usé `p => p.stock == 0` para indicar qué productos debía conservar `filter`. En la transformación cambié la regla para que `map` calculara el valor de las existencias de cada producto. Así pude concentrarme en la condición o el cálculo de cada ejercicio mientras la operación se encargaba de recorrer la lista.

Esto se relaciona con el caso de Apache Spark de la Parte 1. Allí, la lambda indica cómo calcular la longitud de una línea y Spark aplica esa regla a los datos repartidos entre varias máquinas. En mi ejercicio el catálogo era pequeño, pero la idea es la misma. La lambda describe qué hacer con un elemento y la herramienta se encarga de aplicar esa regla al conjunto de datos.

