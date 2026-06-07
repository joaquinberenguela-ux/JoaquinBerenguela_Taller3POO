package JoaquinBerenguela_Taller3POO;

public class HechizoPlanta extends Hechizo {
    private int duracionStun;
    private int cantPlantas;

    public HechizoPlanta(String nombre, int daño, int duracionStun, int cantPlantas) {
        super(nombre, daño);
        this.duracionStun = duracionStun;
        this.cantPlantas = cantPlantas;
    }

    public int getDuracionStun() {
        return duracionStun;
    }

    public void setDuracionStun(int duracionStun) {
        this.duracionStun = duracionStun;
    }

    public int getCantPlantas() {
        return cantPlantas;
    }

    public void setCantPlantas(int cantPlantas) {
        this.cantPlantas = cantPlantas;
    }
}
