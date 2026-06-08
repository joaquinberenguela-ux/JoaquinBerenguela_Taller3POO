package JoaquinBerenguela_Taller3POO;

import java.util.ArrayList;

public class Mago implements PuntuacionMago {
    private String nombre;
    private ArrayList<Hechizo> hechizos;

    public Mago(String nombre) {
        this.nombre = nombre;
        this.hechizos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Hechizo> getHechizos() {
        return hechizos;
    }

    public void agregarHechizo(Hechizo hechizo) {
        this.hechizos.add(hechizo);
    }

    @Override
    public double calcularPuntuacionTotal() {
        double puntuacionTotal = 0;

        for (Hechizo h: hechizos) {
            puntuacionTotal += h.calcularPuntuacion(); 
        }
        
        return puntuacionTotal;
    }
}