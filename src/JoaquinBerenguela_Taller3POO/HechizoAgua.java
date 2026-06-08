package JoaquinBerenguela_Taller3POO;

public class HechizoAgua extends Hechizo {
    private int cantidadHeal;
    private int presionDelAgua;

    public HechizoAgua(String nombre, int danio, int cantidadHeal, int presionDelAgua) {
        super(nombre, danio);
        this.cantidadHeal = cantidadHeal;
        this.presionDelAgua = presionDelAgua;
    }

    public int getCantidadHeal() {
        return cantidadHeal;
    }

    public void setCantidadHeal(int cantidadHeal) {
        this.cantidadHeal = cantidadHeal;
    }

    public int getPresionDelAgua() {
        return presionDelAgua;
    }

    public void setPresionDelAgua(int presionDelAgua) {
        this.presionDelAgua = presionDelAgua;
    }

	@Override
	public double calcularPuntuacion() {
		return (getDaño() + this.cantidadHeal + this.presionDelAgua) * 2;
	}
}
