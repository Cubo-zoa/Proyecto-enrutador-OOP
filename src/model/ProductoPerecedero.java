package model;
public class ProductoPerecedero extends Producto {
    private int diasParaCaducar;

    public ProductoPerecedero(int id, String nombre, String marca, int cantidad, double precioBase, int diasParaCaducar) {
        super(id, nombre, marca, cantidad, precioBase);
        if (diasParaCaducar < 0) {
            throw new IllegalArgumentException("Los días para caducar no pueden ser negativos.");
        }
        this.diasParaCaducar = diasParaCaducar;
    }

    @Override
    public double calcularPrecioFinal() {
        
        if (diasParaCaducar <= 2) {
            return getPrecioBase() * 0.50;
        } else if (diasParaCaducar <= 5) {
            return getPrecioBase() * 0.80;
        }
        return getPrecioBase();
    }

    public int getDiasParaCaducar() { return diasParaCaducar; }

    @Override
    public String toString() {
        return super.toString() + " [Caduca en: " + diasParaCaducar + " días]";
    }
}