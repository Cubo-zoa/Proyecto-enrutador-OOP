package model;
public class Usuario {
    private int id;
    private String nombre;
    private String rol;
    private String pswd;

    public Usuario(int id, String nombre, String rol, String pswd) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.pswd = pswd;
    }

    public boolean login(String pswdIngresada) {
        return this.pswd != null && this.pswd.equals(pswdIngresada);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPswd(String pswd) { this.pswd = pswd; }
}