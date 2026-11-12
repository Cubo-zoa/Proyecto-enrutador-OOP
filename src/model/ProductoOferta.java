package model;
public class ProductoOferta extends Producto {
    private double porcentajeDescuento;

    public ProductoOferta(int id, String nombre, String marca, int cantidad, double precioBase, double porcentajeDescuento) {
        super(id, nombre, marca, cantidad, precioBase);
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularPrecioFinal() {
        double descuento = getPrecioBase() * (this.porcentajeDescuento / 100.0);
        return getPrecioBase() - descuento;
    }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }

    @Override
    public String toString() {
        return super.toString() + " [OFERTA: " + porcentajeDescuento + "% Off]";
    }
}