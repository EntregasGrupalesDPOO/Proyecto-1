package consola;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Exepciones.OperacionNoAutorizadaException;
import Exepciones.VenueNoDisponibleException;
import logica.*;

public class ConsolaOrganizador {

    private final BoletasMaster sistema;
    private final Scanner scanner;

    public ConsolaOrganizador(BoletasMaster sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n=== MENÚ ORGANIZADOR ===");
            System.out.println("1. Crear evento");
            System.out.println("2. Crear localidad para un evento propio");
            System.out.println("3. Proponer nuevo venue");
            System.out.println("4. Solicitar cancelación de evento");
            System.out.println("5. Ver mis eventos");
            System.out.println("6. Ver ganancias (globales, por evento o por localidad)");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> crearEvento();
                case 2 -> crearLocalidad();
                case 3 -> proponerVenue();
                case 4 -> solicitarCancelacion();
                case 5 -> verMisEventos();
                case 6 -> verGanancias();
                case 0 -> System.out.println("Saliendo del menú Organizador...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    //CREAR EVENTO
    private void crearEvento() {
        System.out.println("\n=== Crear evento ===");

        System.out.print("Nombre del evento: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());

        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        if (sistema.getVenues().isEmpty()) {
            System.out.println("No hay venues registrados. Proponga uno primero.");
            return;
        }

        System.out.println("Seleccione un venue:");
        for (int i = 0; i < sistema.getVenues().size(); i++) {
            Venue v = sistema.getVenues().get(i);
            System.out.println((i + 1) + ". " + v.getNombre() + " (" + v.getUbicacion() + ")");
        }

        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= sistema.getVenues().size()) {
            System.out.println("Venue inválido.");
            return;
        }
        Venue venue = sistema.getVenues().get(idx);

        System.out.print("Tipo de evento (CULTURAL / MUSICAL / DEPORTIVO / RELIGIOSO): ");
        String tipo = scanner.nextLine().toUpperCase();

        Organizador orga= (Organizador) sistema.getUsuarioActual();
        try {
            sistema.agendarEvento(nombre,descripcion,venue, orga,tipo, fecha, hora);
            System.out.println("Evento creado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear evento: " + e.getMessage());
        }
    }

    //CREAR LOCALIDAD
    private void crearLocalidad() {
        System.out.println("\n=== Crear localidad ===");
        Organizador organizador = (Organizador) sistema.getUsuarioActual();

        ArrayList<Evento> eventosCreados = organizador.getEventosCreados();
        if (eventosCreados.isEmpty()) {
            System.out.println("No tienes eventos creados todavía.");
            return;
        }

        System.out.println("Seleccione un evento propio:");
        for (int i = 0; i < eventosCreados.size(); i++) {
            System.out.println((i + 1) + ". " + eventosCreados.get(i).getNombre());
        }

        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= eventosCreados.size()) {
            System.out.println("Evento inválido.");
            return;
        }

        Evento evento = eventosCreados.get(idx);

        System.out.print("Nombre de la localidad: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio del tiquete: ");
        double precio = leerDouble();

        System.out.print("Capacidad: ");
        int capacidad = leerEntero();

        System.out.print("tipo de tiquete: ");
        String tipo = scanner.nextLine();

        Localidad localidad;
		try {
			localidad = sistema.crearLocalidadEvento(nombre, capacidad, precio, tipo, evento);
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("Localidad '" + nombre + "' creada exitosamente para " + evento.getNombre());
    }

    //PROPONER NUEVO VENUE
    private void proponerVenue() {
        System.out.println("\n=== Proponer nuevo venue ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        System.out.print("Capacidad: ");
        int capacidad = leerEntero();

        sistema.proponerVenue(capacidad, nombre, ubicacion);
        System.out.println("Propuesta de venue enviada al administrador.");
    }

    //SOLICITAR CANCELACIÓN
    private void solicitarCancelacion() {
        System.out.println("\n=== Solicitar cancelación ===");
        Organizador organizador = (Organizador) sistema.getUsuarioActual();

        ArrayList<Evento> eventosCreados = organizador.getEventosCreados();
        if (eventosCreados.isEmpty()) {
            System.out.println("No tienes eventos que cancelar.");
            return;
        }

        System.out.println("Seleccione el evento que desea cancelar:");
        for (int i = 0; i < eventosCreados.size(); i++) {
            System.out.println((i + 1) + ". " + eventosCreados.get(i).getNombre());
        }

        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= eventosCreados.size()) {
            System.out.println("Evento inválido.");
            return;
        }

        Evento evento = eventosCreados.get(idx);
        System.out.print("Motivo de cancelación: ");
        String razon = scanner.nextLine();

        try {
			sistema.solicitarCancelacionEvento(evento, razon);
		} catch (OperacionNoAutorizadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Solicitud enviada correctamente al administrador.");
    }


    //VER EVENTOS DEL ORGANIZADOR
    private void verMisEventos() {
        System.out.println("\n=== Mis eventos ===");
        Organizador organizador = (Organizador) sistema.getUsuarioActual();

        ArrayList<Evento> eventosCreados = organizador.getEventosCreados();

        if (eventosCreados.isEmpty()) {
            System.out.println("No tienes eventos registrados.");
            return;
        }

        for (Evento e : eventosCreados) {
            System.out.println("- " + e.getNombre() + " (" + e.getFecha() + ")");
        }
    }


    // === VER GANANCIAS ===
    private void verGanancias() {
        System.out.println("\n=== Ver ganancias ===");
        Organizador org = (Organizador) sistema.getUsuarioActual();

        System.out.println("1. Globales");
        System.out.println("2. Por evento");
        System.out.println("3. Por localidad");
        System.out.print("Seleccione una opción: ");
        int opcion = leerEntero();

        switch (opcion) {
            case 1 -> System.out.println("Ganancias globales: $" + org.consultarGananciasGlobales());
            case 2 -> {
                ArrayList<Evento> propios = new ArrayList<>(org.getEventosCreados());
                if (propios.isEmpty()) {
                    System.out.println("No hay eventos.");
                    return;
                }
                mostrarEventos(propios);
                int idx = leerEntero() - 1;
                if (idx < 0 || idx >= propios.size()) return;
                System.out.println("Ganancia: $" + org.consultarGananciasEvento(propios.get(idx)));
            }
            case 3 -> {
                ArrayList<Evento> propios = new ArrayList<>(org.getEventosCreados());
                if (propios.isEmpty()) {
                    System.out.println("No hay eventos.");
                    return;
                }
                mostrarEventos(propios);
                int eIdx = leerEntero() - 1;
                if (eIdx < 0 || eIdx >= propios.size()) return;

                Evento evento = propios.get(eIdx);
                ArrayList<Localidad> locs = new ArrayList<>(evento.getLocalidades());
                if (locs.isEmpty()) {
                    System.out.println("No hay localidades.");
                    return;
                }
                for (int i = 0; i < locs.size(); i++) {
                    System.out.println((i + 1) + ". " + locs.get(i).getNombre());
                }
                int lIdx = leerEntero() - 1;
                if (lIdx < 0 || lIdx >= locs.size()) return;

                System.out.println("Ganancia: $" + org.consultarGananciasLocalidad(locs.get(lIdx)));
            }
            default -> System.out.println("Opción inválida.");
        }
    }


    //MÉTODOS AUXILIARES

    private void mostrarEventos(ArrayList<Evento> eventos) {
        for (int i = 0; i < eventos.size(); i++)
            System.out.println((i + 1) + ". " + eventos.get(i).getNombre());
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
