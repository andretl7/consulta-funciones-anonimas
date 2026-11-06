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

## Parte 2 — Entender

## Parte 3 — Aplicar

## Parte 4 — Ampliar

## Parte 5 — Comparar y concluir
