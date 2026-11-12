package app;
import exception.StockInsuficienteException;
import model.*;
import service.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventario inventario = new Inventario();
        Producto p1 = new Producto(101, "Refresco Cola 600ml", "Coca-Cola", 20, 18.50);
        ProductoOferta p2 = new ProductoOferta(102, "Papas Fritas Sal 170g", "Sabritas", 10, 45.00, 15.0); // 15% descuento
        ProductoPerecedero p3 = new ProductoPerecedero(103, "Leche Entera 1L", "Lala", 15, 28.00, 2); // 2 días = 50% descuento

        inventario.agregarProducto(p1);
        inventario.agregarProducto(p2);
        inventario.agregarProducto(p3);

        Carrito carrito = new Carrito(501, true, true);

        System.out.println("\n-------------------------------------------------");
        System.out.println("  SISTEMA DE TIENDA ONLINE | TERCER AVANCE POO    ");
        System.out.println("---------------------------------------------------");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Principla ---");
            System.out.println("1. Consultar Inventario");
            System.out.println("2. Agregar Producto al Carrito");
            System.out.println("3. Procesar Pago de Carrito");
            System.out.println("4. Ejecutar Pruebas Automáticas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                String entradaMenu = scanner.nextLine();
                int opcion = Integer.parseInt(entradaMenu);
                switch (opcion) {
                    case 1:
                        inventario.mostrarDisponibles();
                        break;
                    case 2:
                        try {
                            inventario.mostrarDisponibles();
                            System.out.print("\nIngrese ID del producto: ");
                            int idProd = Integer.parseInt(scanner.nextLine());
                            Producto prodEncontrado = inventario.buscarPorId(idProd);
                            System.out.print("Ingrese cantidad deseada: ");
                            int cant = Integer.parseInt(scanner.nextLine());
                            carrito.agregarProducto(prodEncontrado, cant);
                        } catch (NumberFormatException e) {
                            System.out.println("[ERROR DE FORMATO]: Debe ingresar un número entero válido.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("[ERROR DE VALIDACIÓN]: " + e.getMessage());
                        } catch (StockInsuficienteException e) {
                            System.out.println("[ERROR DE NEGOCIO | STOCK INSUFICIENTE]: " + e.getMessage());
                        } finally {
                            System.out.println("[SISTEMA]: Intento de agregar al carrito finalizado.");
                        }
                        break;

                    case 3:
                        try {
                            carrito.procesarPago();
                        } catch (StockInsuficienteException e) {
                            System.out.println("[ERROR DE PAGO]: " + e.getMessage());
                        } finally {
                            System.out.println("[SISTEMA]: Intento de procesamiento de pago finalizado.");
                        }
                        break;
                    case 4:
                        ejecutarCasosDePrueba(inventario);
                        break;
                    case 5:
                        salir = true;
                        System.out.println("Gracias por comprar con nosotros");
                        break;

                    default:
                        System.out.println("Opción no válida. Elija un número entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR DE FORMATO]: Ingrese únicamente números enteros para las opciones del menú.");
            }
        }
        scanner.close();
    }

    /*
     * Ejecución de los casos de prueba
     */
    private static void ejecutarCasosDePrueba(Inventario inventario) {
        System.out.println("\n-------------------------------------------");
        System.out.println("Casos de prueba (P1 - P7)");
        System.out.println("----------------------------------------------\n");
        Producto pOferta = inventario.buscarPorId(102);     
        Producto pPerecedero = inventario.buscarPorId(103);

        System.out.println("--- CASO P1: Llamada a calcularPrecioFinal() en ProductoOferta ---");
        System.out.println("Referencia: Producto | Objeto Real: ProductoOferta");
        System.out.println("Precio Base: $" + pOferta.getPrecioBase());
        System.out.println("Precio Final calculado (@Override): $" + pOferta.calcularPrecioFinal());

        System.out.println("\n--- CASO P2: Llamada a calcularPrecioFinal() en ProductoPerecedero ---");
        System.out.println("Referencia: Producto | Objeto Real: ProductoPerecedero (2 días para caducar)");
        System.out.println("Precio Base: $" + pPerecedero.getPrecioBase());
        System.out.println("Precio Final calculado (@Override): $" + pPerecedero.calcularPrecioFinal());

        System.out.println("\n--- CASO P3: Entrada de texto donde se esperaba un número ---");
        try {
            String entradaInvalida = "diez";
            System.out.println("Parseando valor: '" + entradaInvalida);
            int num = Integer.parseInt(entradaInvalida);
        } catch (NumberFormatException e) {
            System.out.println("Capturada NumberFormatException correctamente: " + e.getMessage());
            System.out.println("Indicación al usuario: Ingrese solo dígitos numéricos sin letras.");
        }

        System.out.println("\n--- CASO P4: Creación/Asignación de dato inválido (Precio Negativo) ---");
        try {
            System.out.println("Intentando crear producto con precio -$50.00");
            Producto pInvalido = new Producto(999, "Producto Fallido", "Marca", 5, -50.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Capturada IllegalArgumentException correctamente: " + e.getMessage());
        }

        System.out.println("\n--- CASO P5: Activar excepción propia y verificar estado previo/posterior ---");
        Producto pTest = inventario.buscarPorId(101); // Refresco Cola - Stock: 20
        int stockAntes = pTest.getStock();
        System.out.println("Estado Inicial -> Stock de '" + pTest.getNombre() + "': " + stockAntes);

        try {
            System.out.println("Intentando retirar 50 unidades (Solicitado > Disponible)");
            pTest.decrementoStock(50);
        } catch (StockInsuficienteException e) {
            System.out.println("Capturada StockInsuficienteException correctamente: " + e.getMessage());
        }
        int stockDespues = pTest.getStock();
        System.out.println("Estado Posterior -> Stock de '" + pTest.getNombre() + "': " + stockDespues);
        System.out.println("¿El estado se conservó íntegro?: " + (stockAntes == stockDespues ? "SÍ (Correcto)" : "NO (Error)"));

        System.out.println("\n--- CASO P6: Operación válida inmediatamente después de la falla ---");
        try {
            System.out.println("Intentando retirar 5 unidades de '" + pTest.getNombre());
            pTest.decrementoStock(5);
            System.out.println("Operación exitosa! Nuevo Stock: " + pTest.getStock());
        } catch (StockInsuficienteException e) {
            System.out.println("Error no esperado: " + e.getMessage());
        }

        System.out.println("\n--- CASO P7: Demostración del bloque try-catch-finally ---");
        System.out.println("> Intento A (Válido):");
        ejecutarIntentoFinally(pTest, 2);

        System.out.println("> Intento B (Fallido):");
        ejecutarIntentoFinally(pTest, 100);

        System.out.println("\n------------------------------");
        System.out.println("Fin de las pruebas");
        System.out.println("-------------------------------\n");
    }

    private static void ejecutarIntentoFinally(Producto p, int cantidad) {
        try {
            p.decrementoStock(cantidad);
            System.out.println("  [ÉXITO]: Retiro exitoso de " + cantidad + " unidades.");
        } catch (StockInsuficienteException e) {
            System.out.println("  [FALLO]: Excepción capturada: " + e.getMessage());
        } finally {
            System.out.println("  [FINALLY]: Action 'Intento finalizado' ejecutada siempre.");
        }
    }
}