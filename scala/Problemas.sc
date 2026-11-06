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

// 1. Selección: obtener los nombres de los productos agotados.
val agotados = productos.filter(p => p.stock == 0).map(p => p.nombre)
println("1. Agotados = " + agotados.mkString(", "))

// 2. Transformación: calcular el valor del stock de cada producto.
val valores = productos.map(p => p.precio * p.stock)
println("2. Valor por producto (USD) = " + valores.mkString(", "))

// 3. Validación: comprobar si hay agotados y si todos los precios son positivos.
val existeAgotado = productos.exists(p => p.stock == 0)
val preciosValidos = productos.forall(p => p.precio > 0)
println("3. Existe agotado = " + existeAgotado)
println("3. Todos los precios son positivos = " + preciosValidos)

// 4. Acumulación: sumar el valor de todas las existencias.
val total = productos.foldLeft(0)((subtotal, p) => subtotal + p.precio * p.stock)
println("4. Valor total (USD) = " + total)