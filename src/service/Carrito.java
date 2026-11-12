package service;
import exception.StockInsuficienteException;
import model.Producto;
import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private int idCarrito;
    private boolean esPickup;
    private boolean pagoEfectivo;
    private HashMap<Producto, Integer> articulos;

    public Carrito(int idCarrito, boolean esPickup, boolean pagoEfectivo) {
        this.idCarrito = idCarrito;
        this.esPickup = esPickup;
        this.pagoEfectivo = pagoEfectivo;
        this.articulos = new HashMap<>();
    }

    public boolean agregarProducto(Producto prod, int cantidad) throws StockInsuficienteException {
        if (prod == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        
        int enCarritoActualmente = articulos.getOrDefault(prod, 0);
        int totalSolicitado = enCarritoActualmente + cantidad;

        if (!prod.disponibilidad(totalSolicitado)) {
            throw new StockInsuficienteException("No hay stock suficiente de '" + prod.getNombre() 
                    + "'. Stock actual: " + prod.getStock() + ", ya en carrito: " + enCarritoActualmente);
        }

        articulos.put(prod, totalSolicitado);
        System.out.println("-> Se agregaron " + cantidad + " unidad(es) de '" + prod.getNombre() + "' al carrito.");
        return true;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Map.Entry<Producto, Integer> entry : articulos.entrySet()) {
            total += entry.getKey().calcularPrecioFinal() * entry.getValue();
        }
        return total;
    }

    public boolean procesarPago() throws StockInsuficienteException {
        if (articulos.isEmpty()) {
            System.out.println("El carrito está vacío. No se puede procesar el pago.");
            return false;
        }

        for (Map.Entry<Producto, Integer> entry : articulos.entrySet()) {
            Producto prod = entry.getKey();
            int cant = entry.getValue();
            if (prod.getStock() < cant) {
                throw new StockInsuficienteException("Error al procesar pago: Stock insuficiente de '" + prod.getNombre() + "'. Disponibles: " + prod.getStock() + ", requeridos: " + cant);
            }
        }

        for (Map.Entry<Producto, Integer> entry : articulos.entrySet()) {
            entry.getKey().decrementoStock(entry.getValue());
        }

        System.out.println("\n--------------------------------");
        System.out.println("Desgloce de la compra");
        System.out.println("Ticket Carrito #" + idCarrito);
        System.out.println("Modalidad: " + (esPickup ? "Recoger en tienda (Pick up)" : "Envío a domicilio"));
        System.out.println("Método de pago: " + (pagoEfectivo ? "Efectivo" : "Tarjeta"));
        System.out.println("----------------------------------------------------");
        for (Map.Entry<Producto, Integer> entry : articulos.entrySet()) {
            Producto p = entry.getKey();
            int cant = entry.getValue();
            System.out.printf("- %s x%d (Base: $%.2f | Final: $%.2f c/u) -> Subtotal: $%.2f\n",
                    p.getNombre(), cant, p.getPrecioBase(), p.calcularPrecioFinal(), p.calcularPrecioFinal() * cant);
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: $%.2f\n", calcularTotal());
        System.out.println("--------------------------------------------------\n");

        articulos.clear();
        return true;
    }

    public int getIdCarrito() { return idCarrito; }
    public boolean isEsPickup() { return esPickup; }
    public boolean isPagoEfectivo() { return pagoEfectivo; }
    public HashMap<Producto, Integer> getArticulos() { return articulos; }
}