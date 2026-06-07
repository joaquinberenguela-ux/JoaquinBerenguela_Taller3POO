package JoaquinBerenguela_Taller3POO;

public abstract class Hechizo {
    private String nombre;
    private int daño;

    public Hechizo(String nombre, int daño) {
        this.nombre = nombre;
        this.daño = daño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }
}