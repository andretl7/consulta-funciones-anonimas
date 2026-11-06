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

## Parte 3 — Aplicar

## Parte 4 — Ampliar

## Parte 5 — Comparar y concluir
