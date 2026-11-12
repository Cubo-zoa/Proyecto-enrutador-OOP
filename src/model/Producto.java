package model;
import exception.StockInsuficienteException;
public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private int cantidad;
    private double precioBase;

    public Producto(int id, String nombre, String marca, int cantidad, double precioBase) {
        if (precioBase < 0 || cantidad < 0) {
            throw new IllegalArgumentException("El precio y la cantidad inicial no pueden ser negativos.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precioBase = precioBase;
    }

    public double calcularPrecioFinal() {
        return this.precioBase;
    }

    public boolean disponibilidad(int cantRequerida) {
        return this.cantidad >= cantRequerida && cantRequerida > 0;
    }

    public void aumentoStock(int diferenciaStock) {
        if (diferenciaStock <= 0) {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser mayor a cero.");
        }
        this.cantidad += diferenciaStock;
    }

    public void decrementoStock(int diferenciaStock) throws StockInsuficienteException {
        if (diferenciaStock <= 0) {
            throw new IllegalArgumentException("La cantidad a retirar debe ser mayor a cero.");
        }
        if (this.cantidad < diferenciaStock) {
            throw new StockInsuficienteException("Stock insuficiente para '" + nombre + "'. Disponible: " 
                    + cantidad + ", Solicitado: " + diferenciaStock);
        }
        this.cantidad -= diferenciaStock;
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public double getPrecioBase() { return precioBase; }
    
    public void setPrecioBase(double precioBase) {
        if (precioBase < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        this.precioBase = precioBase;
    }

    public int getStock() { return cantidad; }
    public void setStock(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ID: [" + id + "], Nombre: " + nombre + ", Marca: (" + marca + "), Precio Final: $" 
                + String.format("%.2f", calcularPrecioFinal()) + " | Stock: " + cantidad;
    }
}