package JoaquinBerenguela_Taller3POO;

public class HechizoFuego extends Hechizo {
    private int duracionQuemadura;

    public HechizoFuego(String nombre, int daño, int duracionQuemadura) {
        super(nombre, daño);
        this.duracionQuemadura = duracionQuemadura;
    }

    public int getDuracionQuemadura() {
        return duracionQuemadura;
    }

    public void setDuracionQuemadura(int duracionQuemadura) {
        this.duracionQuemadura = duracionQuemadura;
    }
}
