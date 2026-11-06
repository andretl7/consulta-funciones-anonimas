import java.util.List;
import java.util.stream.Collectors;

record Producto(String nombre, String categoria, int precio, int stock) {}

var productos = List.of(
    new Producto("Arroz", "Alimentos", 4, 40),
    new Producto("Cafe", "Alimentos", 8, 15),
    new Producto("Pasta", "Alimentos", 2, 0),
    new Producto("Jabon", "Hogar", 3, 22),
    new Producto("Toalla", "Hogar", 12, 8),
    new Producto("Lampara", "Hogar", 20, 4),
    new Producto("Mouse", "Tecnologia", 18, 12),
    new Producto("Teclado", "Tecnologia", 32, 0),
    new Producto("Monitor", "Tecnologia", 150, 3),
    new Producto("Cuaderno", "Papeleria", 5, 30),
    new Producto("Lapiz", "Papeleria", 1, 60),
    new Producto("Mochila", "Papeleria", 25, 6)
);

// 1. Selección: obtener los nombres de los productos agotados.
var agotados = productos.stream().filter(p -> p.stock() == 0).map(p -> p.nombre()).collect(Collectors.joining(", "));
System.out.println("1. Agotados = " + agotados);

// 2. Transformación: calcular el valor del stock de cada producto.
var valores = productos.stream().map(p -> Integer.toString(p.precio() * p.stock())).collect(Collectors.joining(", "));
System.out.println("2. Valor por producto (USD) = " + valores);

// 3. Validación: comprobar si hay agotados y si todos los precios son positivos.
var existeAgotado = productos.stream().anyMatch(p -> p.stock() == 0);
var preciosValidos = productos.stream().allMatch(p -> p.precio() > 0);
System.out.println("3. Existe agotado = " + existeAgotado);
System.out.println("3. Todos los precios son positivos = " + preciosValidos);

// 4. Acumulación: sumar el valor de todas las existencias.
var total = productos.stream().mapToInt(p -> p.precio() * p.stock()).reduce(0, (subtotal, importe) -> subtotal + importe);
System.out.println("4. Valor total (USD) = " + total);

// Ejecutar desde la raíz del repositorio:
// jshell java/Problemas.java
// Escribe /exit en JShell para volver a la terminal.