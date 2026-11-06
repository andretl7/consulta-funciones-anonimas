case class Producto(nombre: String, categoria: String, precio: Int, stock: Int)

val productos = List(
  Producto("Arroz", "Alimentos", 4, 40),
  Producto("Cafe", "Alimentos", 8, 15),
  Producto("Pasta", "Alimentos", 2, 0),
  Producto("Jabon", "Hogar", 3, 22),
  Producto("Toalla", "Hogar", 12, 8),
  Producto("Lampara", "Hogar", 20, 4),
  Producto("Mouse", "Tecnologia", 18, 12),
  Producto("Teclado", "Tecnologia", 32, 0),
  Producto("Monitor", "Tecnologia", 150, 3),
  Producto("Cuaderno", "Papeleria", 5, 30),
  Producto("Lapiz", "Papeleria", 1, 60),
  Producto("Mochila", "Papeleria", 25, 6)
)

// 1. Ordenar por precio unitario.
val ordenados = productos.sortBy(p => p.precio)
println("1. sortBy = " + ordenados.map(p => p.nombre).mkString(", "))

// 2. Agrupar por categoría. Ordenamos las claves para mostrar la salida.
val grupos = productos.groupBy(p => p.categoria)
println("2. groupBy")
grupos.toList.sortBy(grupo => grupo._1).foreach { case (categoria, lista) =>
  println("   " + categoria + " = " + lista.map(p => p.nombre).mkString(", "))
}

// 3. Separar los productos con menos de cinco unidades.
val (criticos, resto) = productos.partition(p => p.stock < 5)
println("3. partition (stock < 5) = " + criticos.map(p => p.nombre).mkString(", "))
println("   Resto = " + resto.map(p => p.nombre).mkString(", "))

// 4. Buscar el primer agotado en el orden del catálogo.
val primerAgotado = productos.find(p => p.stock == 0)
println("4. find = " + primerAgotado.map(p => p.nombre))

// 5. Buscar el producto tecnológico más caro con stock bajo.
val prioridad = productos
  .filter(p => p.stock < 5)
  .sortBy(p => -p.precio)
  .find(p => p.categoria == "Tecnologia")

println("5. Combinacion = " + prioridad.map(p => p.nombre))

// Ejecutar desde la raíz del repositorio:
// scala-cli run operaciones/Operaciones.sc --server=false