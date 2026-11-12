package service;
import model.Producto;
import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> listaProductos;

    public Inventario() {
        this.listaProductos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        if (p == null) {
            throw new IllegalArgumentException("No se puede agregar un producto nulo.");
        }
        listaProductos.add(p);
        System.out.println("Producto '" + p.getNombre() + "' registrado en inventario.");
    }

    public Producto buscarPorId(int id) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe ningún producto registrado con el ID: " + id);
    }

    public void mostrarDisponibles() {
        System.out.println("\nInventario de productos disponibles");
        if (listaProductos.isEmpty()) {
            System.out.println("(No hay productos registrados)");
            return;
        }
        for (Producto p : listaProductos) {
            System.out.println(p);
        }
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public int getCantidadProductos() {
        return listaProductos.size();
    }
}