package JoaquinBerenguela_Taller3POO;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SistemaImpl implements Sistema, Administrador, Analista {
    private ArrayList<Mago> magos;
    private ArrayList<Hechizo> hechizos;
    private Scanner scanner;

    public SistemaImpl() {
        this.magos = new ArrayList<>();
        this.hechizos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void iniciar() {
        cargarHechizos();
        cargarMagos();
        
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Menú Administrador");
            System.out.println("2. Menú Analista");
            System.out.println("3. Salir");
            System.out.print("Ingrese opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        menuAdministrador();
                        break;
                    case 2:
                        menuAnalista();
                        break;
                    case 3:
                        System.out.println("Cerrando el sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } else {
                System.out.println("Número inválido.");
                scanner.nextLine(); 
            }
        } while (opcion != 3);

    }

    private void cargarHechizos() {
        try {
            Scanner scanner = new Scanner(new File("Hechizos.txt"));

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                String nombreHechizo = partes[0];
                String tipoHechizo = partes[1];
                int danio = Integer.parseInt(partes[2]);

                if (tipoHechizo.equals("Fuego")) {
                    int duracionQuemadura = Integer.parseInt(partes[3]);
                    HechizoFuego hechizoFuego = new HechizoFuego(nombreHechizo, danio, duracionQuemadura);
                    hechizos.add(hechizoFuego);

                } else if (tipoHechizo.equals("Tierra")) {
                    int mejoraDefensa = Integer.parseInt(partes[3]);
                    HechizoTierra hechizoTierra = new HechizoTierra(nombreHechizo, danio, mejoraDefensa);
                    hechizos.add(hechizoTierra);

                } else if (tipoHechizo.equals("Planta")) {
                    String[] efectosPlanta = partes[3].split(",");
                    int duracionStun = Integer.parseInt(efectosPlanta[0]);
                    int cantPlantas = Integer.parseInt(efectosPlanta[1]);
                    HechizoPlanta hechizoPlanta = new HechizoPlanta(nombreHechizo, danio, duracionStun, cantPlantas);
                    hechizos.add(hechizoPlanta);

                } else if (tipoHechizo.equals("Agua")) {
                    String[] efectosAgua = partes[3].split(",");
                    int cantidadHeal = Integer.parseInt(efectosAgua[0]);
                    int presionDelAgua = Integer.parseInt(efectosAgua[1]);
                    HechizoAgua hechizoAgua = new HechizoAgua(nombreHechizo, danio, cantidadHeal, presionDelAgua);
                    hechizos.add(hechizoAgua);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo de hechizos");
        }
    }

    private void cargarMagos() {
        try {
            Scanner scanner = new Scanner(new File("Magos.txt"));

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                String nombreMago = partes[0];
                Mago nuevoMago = new Mago(nombreMago);

                String[] nombresHechizos = partes[1].split("\\|");

                for (String nombreHechizo: nombresHechizos) {
                    Hechizo hechizoEncontrado = buscarHechizo(nombreHechizo);
                        
                    if (hechizoEncontrado != null) {
                        nuevoMago.agregarHechizo(hechizoEncontrado);
                    } else {
                        System.out.println("Hechizo'" + nombreHechizo + "' no encontrado.");
                    }
                }
                
                magos.add(nuevoMago);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo de magos");
        }
    }

    private Hechizo buscarHechizo(String nombre) {
        for (Hechizo h: hechizos) {
            if (h.getNombre().equals(nombre)) {
                return h;
            }
        }
        return null;
    }

    private Mago buscarMago(String nombre) {
        for (Mago m: magos) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    private void guardarMagosTxt() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Magos.txt"))) {
            for (Mago m: magos) {
                String linea = m.getNombre() + ";";
                ArrayList<Hechizo> hechizosMago = m.getHechizos();
                
                for (int i = 0; i < hechizosMago.size(); i++) {
                    linea += hechizosMago.get(i).getNombre();
                    if (i < hechizosMago.size() - 1) {
                        linea += "|";
                    }
                }
                
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar Magos");
        }
    }

    private void guardarHechizosTxt() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Hechizos.txt"))) {
            for (Hechizo h: hechizos) {
                String linea = h.getNombre() + ";";
                
                if (h instanceof HechizoFuego) {
                    HechizoFuego hf = (HechizoFuego) h;
                    linea += "Fuego;" + hf.getDaño() + ";" + hf.getDuracionQuemadura();
                    
                } else if (h instanceof HechizoTierra) {
                    HechizoTierra ht = (HechizoTierra) h;
                    linea += "Tierra;" + ht.getDaño() + ";" + ht.getMejoraDefensa();
                    
                } else if (h instanceof HechizoPlanta) {
                    HechizoPlanta hp = (HechizoPlanta) h;
                    linea += "Planta;" + hp.getDaño() + ";" + hp.getDuracionStun() + "," + hp.getCantPlantas();
                    
                } else if (h instanceof HechizoAgua) {
                    HechizoAgua ha = (HechizoAgua) h;
                    linea += "Agua;" + ha.getDaño() + ";" + ha.getCantidadHeal() + "," + ha.getPresionDelAgua();
                }
                
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar Hechizos");
        }
    }


    @Override
    public void menuAdministrador() {
        int opcion = 0;
        do {
            System.out.println("\n---------------- Panel Administrador ----------------");
            System.out.println("1. Agregar Mago");
            System.out.println("2. Modificar Mago");
            System.out.println("3. Eliminar Mago");
            System.out.println("4. Agregar Hechizo");
            System.out.println("5. Modificar Hechizo");
            System.out.println("6. Eliminar Hechizo");
            System.out.println("7. Salir");
            System.out.print("Ingrese opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        agregarMago();
                        break;
                    case 2:
                        modificarMago();
                        break;
                    case 3:
                        eliminarMago();
                        break;
                    case 4:
                        agregarHechizo();
                        break;
                    case 5:
                        modificarHechizo();
                        break;
                    case 6:
                        eliminarHechizo();
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } else {
                System.out.println("Número inválido.");
                scanner.nextLine(); 
            }
        } while (opcion != 7);
    }

    @Override
    public void agregarMago() {
        System.out.print("Ingrese el nombre del mago: ");
        String nombre = scanner.nextLine();
        
        if (buscarMago(nombre) != null) {
            System.out.println("El mago ya existe.");
        } else {
            Mago nuevoMago = new Mago(nombre);
            magos.add(nuevoMago);
            guardarMagosTxt();
            System.out.println("Mago agregado.");
        }
    }

    @Override
    public void modificarMago() {
        System.out.print("Ingrese el nombre del mago a modificar: ");
        String nombre = scanner.nextLine();
        Mago m = buscarMago(nombre);
        
        if (m != null) {
            System.out.print("Ingrese el nuevo nombre: ");
            String nuevoNombre = scanner.nextLine();
            m.setNombre(nuevoNombre);
            guardarMagosTxt();
            System.out.println("Mago modificado.");
        } else {
            System.out.println("Mago no encontrado.");
        }
    }

    @Override
    public void eliminarMago() {
        System.out.print("Ingrese el nombre del mago a eliminar: ");
        String nombre = scanner.nextLine();
        Mago m = buscarMago(nombre);
        
        if (m != null) {
            magos.remove(m);
            guardarMagosTxt();
            System.out.println("Mago eliminado correctamente.");
        } else {
            System.out.println("Mago no encontrado.");
        }
    }

    @Override
    public void agregarHechizo() {
        System.out.print("Ingrese nombre del hechizo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese tipo (Fuego, Tierra, Planta, Agua): ");
        String tipo = scanner.nextLine();
        
        int danio = 0;
        boolean danioValido = false;
        do {
            System.out.print("Ingrese daño: ");
            if (scanner.hasNextInt()) {
                danio = scanner.nextInt();
                scanner.nextLine();
                danioValido = true;
            } else {
                System.out.println("Número inválido.");
                scanner.nextLine();
            }
        } while (!danioValido);

        if (tipo.equals("Fuego")) {
            int duracion = 0;
            boolean duracionValida = false;
            do {
                System.out.print("Ingrese duración de quemadura: ");
                if (scanner.hasNextInt()) {
                    duracion = scanner.nextInt();
                    scanner.nextLine();
                    duracionValida = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!duracionValida);
            hechizos.add(new HechizoFuego(nombre, danio, duracion));
            System.out.println("Hechizo agregado.");
            guardarHechizosTxt();

        } else if (tipo.equals("Tierra")) {
            int mejora = 0;
            boolean mejoraValida = false;
            do {
                System.out.print("Ingrese mejora de defensa: ");
                if (scanner.hasNextInt()) {
                    mejora = scanner.nextInt();
                    scanner.nextLine();
                    mejoraValida = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!mejoraValida);
            hechizos.add(new HechizoTierra(nombre, danio, mejora));
            System.out.println("Hechizo agregado.");
            guardarHechizosTxt();

        } else if (tipo.equals("Planta")) {
            int stun = 0;
            boolean stunValido = false;
            do {
                System.out.print("Ingrese duración stun: ");
                if (scanner.hasNextInt()) {
                    stun = scanner.nextInt();
                    scanner.nextLine();
                    stunValido = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!stunValido);

            int plantas = 0;
            boolean plantasValido = false;
            do {
                System.out.print("Ingrese cantidad de plantas: ");
                if (scanner.hasNextInt()) {
                    plantas = scanner.nextInt();
                    scanner.nextLine();
                    plantasValido = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!plantasValido);

            hechizos.add(new HechizoPlanta(nombre, danio, stun, plantas));
            System.out.println("Hechizo agregado.");
            guardarHechizosTxt();

        } else if (tipo.equals("Agua")) {
            int heal = 0;
            boolean healValido = false;
            do {
                System.out.print("Ingrese cantidad heal: ");
                if (scanner.hasNextInt()) {
                    heal = scanner.nextInt();
                    scanner.nextLine();
                    healValido = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!healValido);

            int presion = 0;
            boolean presionValida = false;
            do {
                System.out.print("Ingrese presión del agua: ");
                if (scanner.hasNextInt()) {
                    presion = scanner.nextInt();
                    scanner.nextLine();
                    presionValida = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine();
                }
            } while (!presionValida);

            hechizos.add(new HechizoAgua(nombre, danio, heal, presion));
            System.out.println("Hechizo agregado.");
            guardarHechizosTxt();

        } else {
            System.out.println("Tipo no válido.");
        }
    }

    @Override
    public void modificarHechizo() {
        System.out.print("Ingrese el nombre del hechizo a modificar: ");
        String nombre = scanner.nextLine();
        Hechizo h = buscarHechizo(nombre);

        if (h != null) {
            boolean valido = false;
            do {
                System.out.print("Ingrese el nuevo daño: ");
                if (scanner.hasNextInt()) {
                    int nuevoDaño= scanner.nextInt();
                    scanner.nextLine(); 
                    h.setDaño(nuevoDaño);
                    guardarHechizosTxt();
                    System.out.println("Hechizo modificado.");
                    valido = true;
                } else {
                    System.out.println("Número inválido.");
                    scanner.nextLine(); 
                }
            } while (!valido);
        } else {
            System.out.println("Hechizo no encontrado.");
        }
    }

    @Override
    public void eliminarHechizo() {
        System.out.print("Ingrese el nombre del hechizo a eliminar: ");
        String nombre = scanner.nextLine();
        Hechizo h = buscarHechizo(nombre);

        if (h != null) {
            hechizos.remove(h);
            
            for (Mago m: magos) {
                m.getHechizos().remove(h);
            }
            
            guardarHechizosTxt();
            guardarMagosTxt();
            System.out.println("Hechizo eliminado correctamente.");

        } else {
            System.out.println("Hechizo no encontrado.");
        }
    }

    @Override
    public void menuAnalista() {
        int opcion = 0;
        do {
            System.out.println("\n---------------- Panel Analista ----------------");
            System.out.println("1. Top 10 Mejores Hechizos");
            System.out.println("2. Top 3 Mejores Magos");
            System.out.println("3. Mostrar todos los Hechizos");
            System.out.println("4. Mostrar todos los magos");
            System.out.println("5. Mostrar todos los Hechizos junto a su puntuacion");
            System.out.println("6. Mostrar todos los magos junto a su puntuacion");
            System.out.println("7. Volver al menú principal");
            System.out.print("Ingrese opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        mostrarTop10Hechizos();
                        break;
                    case 2:
                        mostrarTop3Magos();
                        break;
                    case 3:
                        mostrarTodosLosHechizos();
                        break;
                    case 4:
                        mostrarTodosLosMagos();
                        break;
                    case 5:
                        mostrarHechizosConPuntuacion();
                        break;
                    case 6:
                        mostrarMagosConPuntuacion();
                        break;
                    case 7:
                        System.out.println("Saliendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }

            } else {
                System.out.println("Número inválido.");
                scanner.nextLine(); 
            }
        } while (opcion != 7);
    }

    @Override
    public void mostrarTop10Hechizos() {
        ArrayList<Hechizo> copia = new ArrayList<>(hechizos);

        for (int i = 0; i < copia.size() - 1; i++) {
            for (int j = i + 1; j < copia.size(); j++) {
                if (copia.get(i).calcularPuntuacion() < copia.get(j).calcularPuntuacion()) {
                    Hechizo aux = copia.get(i);
                    copia.set(i, copia.get(j));
                    copia.set(j, aux);
                }
            }
        }

        System.out.println("\n--- Top 10 Hechizos ---");

        for (int i = 0; i < 10; i++) {
            Hechizo h = copia.get(i);
            System.out.println((i + 1) + ". " + h.getNombre() + " - Puntos: " + h.calcularPuntuacion());
        }
    }

    @Override
    public void mostrarTop3Magos() {
        ArrayList<Mago> copia = new ArrayList<>(magos);

        for (int i = 0; i < copia.size() - 1; i++) {
            for (int j = i + 1; j < copia.size(); j++) {
                if (copia.get(i).calcularPuntuacionTotal() < copia.get(j).calcularPuntuacionTotal()) {
                    Mago aux = copia.get(i);
                    copia.set(i, copia.get(j));
                    copia.set(j, aux);
                }
            }
        }

        System.out.println("\n--- Top 3 Magos ---");
        for (int i = 0; i < 3; i++) {
            Mago m = copia.get(i);
            System.out.println((i + 1) + ". " + m.getNombre() + " - Puntos: " + m.calcularPuntuacionTotal());
        }
    }

    @Override
    public void mostrarTodosLosHechizos() {
        System.out.println("\n--- Lista de Hechizos ---");
        for (Hechizo h: hechizos) {
            System.out.println("- " + h.getNombre());
        }
    }

    @Override
    public void mostrarTodosLosMagos() {
        System.out.println("\n--- Lista de Magos ---");
        for (Mago m: magos) {
            System.out.println("- " + m.getNombre());
        }
    }

    @Override
    public void mostrarHechizosConPuntuacion() {
        System.out.println("\n--- Hechizos y Puntuación ---");
        for (Hechizo h: hechizos) {
            System.out.println("- " + h.getNombre() + " (Puntos: " + h.calcularPuntuacion() + ")");
        }
    }

    @Override
    public void mostrarMagosConPuntuacion() {
        System.out.println("\n--- Magos y Puntuación ---");
        for (Mago m: magos) {
            System.out.println("- " + m.getNombre() + " (Puntos: " + m.calcularPuntuacionTotal() + ")");
        }
    }
}
