from dataclasses import dataclass
from functools import reduce


@dataclass
class Producto:
    nombre: str
    categoria: str
    precio: int
    stock: int


productos = [
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
    Producto("Mochila", "Papeleria", 25, 6),
]

# 1. Selección: obtener los nombres de los productos agotados.
agotados = filter(lambda p: p.stock == 0, productos)
nombres = ", ".join(map(lambda p: p.nombre, agotados))
print("1. Agotados = " + nombres)

# 2. Transformación: calcular el valor del stock de cada producto.
valores = map(lambda p: p.precio * p.stock, productos)
print("2. Valor por producto (USD) = " + ", ".join(map(str, valores)))

# 3. Validación: comprobar si hay agotados y si los precios son positivos.
existe_agotado = any(map(lambda p: p.stock == 0, productos))
precios_validos = all(map(lambda p: p.precio > 0, productos))
print("3. Existe agotado = " + str(existe_agotado).lower())
print("3. Todos los precios son positivos = " + str(precios_validos).lower())

# 4. Acumulación: sumar el valor de todas las existencias.
total = reduce(lambda subtotal, p: subtotal + p.precio * p.stock, productos, 0)
print(f"4. Valor total (USD) = {total}")

# Ejecutar desde la raíz del repositorio:
# python3 python/problemas.py