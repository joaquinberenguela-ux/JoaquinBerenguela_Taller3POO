package JoaquinBerenguela_Taller3POO;

public class HechizoTierra extends Hechizo {
    private int mejoraDefensa;

    public HechizoTierra(String nombre, int danio, int mejoraDefensa) {
        super(nombre, danio);
        this.mejoraDefensa = mejoraDefensa;
    }

    public int getMejoraDefensa() {
        return mejoraDefensa;
    }

    public void setMejoraDefensa(int mejoraDefensa) {
        this.mejoraDefensa = mejoraDefensa;
    }

    @Override
    public double calcularPuntuacion() {
        return (getDaño() * this.mejoraDefensa) / 2; 
    }
}
