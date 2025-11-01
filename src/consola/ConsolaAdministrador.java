package consola;

import java.util.ArrayList;
import java.util.Scanner;

import logica.Administrador;
import logica.BoletasMaster;
import logica.Evento;
import logica.Solicitud;

public class ConsolaAdministrador {

    private BoletasMaster sistema;
    private Scanner scanner;

    public ConsolaAdministrador(BoletasMaster sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver solicitudes pendientes");
            System.out.println("2. Atender solicitud");
            System.out.println("3. Ver ganancias generales");
            System.out.println("4. Ver ganancias por evento");
            System.out.println("5. Ver ganancias por organizador");
            System.out.println("6. Ver log del Marketplace");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> verSolicitudesPendientes();
                case 2 -> atenderSolicitud();
                case 3 -> verGananciasGenerales();
                case 4 -> verGananciasPorEvento();
                case 5 -> verGananciasPorOrganizador();
                case 6 -> sistema.verLogMarketplace();
                case 7 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    //MÉTODOS

    private void verSolicitudesPendientes() {
        System.out.println("\n=== Solicitudes Pendientes ===");
        sistema.verSolicitudesPendientes();
    }

    private void atenderSolicitud() {
        System.out.println("\n=== Atender Solicitud ===");
        Administrador admin = sistema.getAdministrador();
        ArrayList<Solicitud> solicitudes = admin.getSolicitudes();

        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes.");
            return;
        }

        for (int i = 0; i < solicitudes.size(); i++) {
            Solicitud s = solicitudes.get(i);
            System.out.println((i + 1) + ". " + s.getTipo() + " - Solicitante: " 
                + s.getSolicitante().getLogin() + " - " + s.getDescripcion());
        }

        System.out.print("Seleccione el número de solicitud a atender: ");
        int indice = leerEntero() - 1;

        if (indice < 0 || indice >= solicitudes.size()) {
            System.out.println("Número inválido.");
            return;
        }

        System.out.print("¿Aceptar solicitud? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        boolean aceptar = resp.equals("s");

        try {
            sistema.atenderSolicitud(indice, aceptar);
            System.out.println("Solicitud " + (aceptar ? "aceptada" : "rechazada") + " correctamente.");
        } catch (Exception e) {
            System.out.println("Error al atender la solicitud: " + e.getMessage());
        }
    }

    private void verGananciasGenerales() {
        System.out.println("\n=== Ganancias Generales ===");
        sistema.obtenerGananciasGenerales();
    }

    private void verGananciasPorEvento() {
        System.out.println("\n=== Ganancias por Evento ===");
        ArrayList<Evento> eventos = sistema.getEventos();

        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados.");
            return;
        }

        for (int i = 0; i < eventos.size(); i++) {
            System.out.println((i + 1) + ". " + eventos.get(i).getNombre());
        }

        System.out.print("Seleccione un evento: ");
        int idx = leerEntero() - 1;

        if (idx < 0 || idx >= eventos.size()) {
            System.out.println("Evento inválido.");
            return;
        }

        Evento evento = eventos.get(idx);
        double ganancias = sistema.getAdministrador().obtenerGananciasEvento(evento);
        System.out.println("Ganancias del evento '" + evento.getNombre() + "': $" + ganancias);
    }

    private void verGananciasPorOrganizador() {
        System.out.println("\n=== Ganancias por Organizador ===");
        var organizadores = sistema.getOrganizadores();

        if (organizadores.isEmpty()) {
            System.out.println("No hay organizadores registrados.");
            return;
        }

        int i = 1;
        for (String login : organizadores.keySet()) {
            System.out.println(i++ + ". " + login);
        }

        System.out.print("Seleccione un organizador: ");
        int idx = leerEntero() - 1;
        if (idx < 0 || idx >= organizadores.size()) {
            System.out.println("Organizador inválido.");
            return;
        }

        String login = (String) organizadores.keySet().toArray()[idx];
        double ganancias = sistema.getAdministrador()
                .obtenerGananciasOrganizador(organizadores.get(login));
        System.out.println("Ganancias del organizador '" + login + "': $" + ganancias);
    }

    // === UTILIDADES ===
    private int leerEntero() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
