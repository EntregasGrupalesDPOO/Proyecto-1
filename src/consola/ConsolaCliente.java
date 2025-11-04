package consola;

import java.util.*;
import logica.*;
import Marketplace.*;

public class ConsolaCliente {

    private BoletasMaster sistema;
    private Scanner scanner;

    public ConsolaCliente(BoletasMaster sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    public void ejecutarMenuCliente() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== MENÚ CLIENTE =====");
            System.out.println("1. Ver eventos disponibles");
            System.out.println("2. Comprar tiquete");
            System.out.println("3. Comprar tiquete enumerado");
            System.out.println("4. Comprar tiquete múltiple (UE o VE)");
            System.out.println("5. Comprar paquete deluxe");
            System.out.println("6. Solicitar reembolso");
            System.out.println("7. Publicar oferta en Marketplace");
            System.out.println("8. Ver ofertas del Marketplace");
            System.out.println("9. Hacer contraoferta");
            System.out.println("10. Revisar mis contraofertas y aceptarlas/rechazarlas");
            System.out.println("11. Eliminar una de mis ofertas");
            System.out.println("12. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> mostrarEventos();
                case 2 -> comprarTiquete();
                case 3 -> comprarTiqueteEnumerado();
                case 4 -> comprarTiqueteMultiple();
                case 5 -> comprarPaqueteDeluxe();
                case 6 -> solicitarReembolso();
                case 7 -> publicarOferta();
                case 8 -> verOfertasMarketplace();
                case 9 -> hacerContraOferta();
                case 10 -> gestionarMisContraOfertas();
                case 11 -> eliminarMiOferta();
                case 12 -> {
                    System.out.println("Cerrando sesión...");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    // === MÉTODOS DEL MENÚ ===

    private void mostrarEventos() {
        System.out.println("\nEventos disponibles:");
        for (Evento e : sistema.getEventos()) {
            System.out.println("• " + e.getNombre() + " - " + e.getFecha() + " - " + e.getVenue().getNombre());
        }
    }

    private void comprarTiquete() {
        System.out.print("Ingrese nombre del evento: ");
        String nombre = scanner.nextLine();
        Evento evento = sistema.getEventos().stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return;
        }

        System.out.print("Ingrese localidad: ");
        String Localidad = scanner.nextLine();
        System.out.print("Cantidad de tiquetes: ");
        int cantidad = leerEntero();

        try {
            sistema.comprarTiquetes(cantidad, evento, Localidad);
            System.out.println("Compra realizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al comprar: " + e.getMessage());
        }
    }
    
    private void comprarTiqueteEnumerado() {
        System.out.print("Ingrese nombre del evento: ");
        String nombre = scanner.nextLine();
        Evento evento = sistema.getEventos().stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return;
        }

        System.out.print("Ingrese ID de localidad: ");
        String idLocalidad = scanner.nextLine();
        System.out.print("Ingrese número de silla inicial: ");
        int idSilla = leerEntero();
        System.out.print("Cantidad de tiquetes: ");
        int cantidad = leerEntero();

        try {
            sistema.comprarTiquetesEnumerados(cantidad, evento, idLocalidad, idSilla);
            System.out.println("Compra enumerada realizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al comprar enumerado: " + e.getMessage());
        }
    }

    private void comprarPaqueteDeluxe() {
        System.out.print("Ingrese nombre del evento: ");
        String nombre = scanner.nextLine();
        Evento evento = sistema.getEventos().stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return;
        }

        System.out.print("Ingrese ID de localidad: ");
        String idLocalidad = scanner.nextLine();

        try {
            sistema.comprarPaqueteDeluxe(evento, idLocalidad);
            System.out.println("Paquete Deluxe comprado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al comprar paquete deluxe: " + e.getMessage());
        }
    }

    private void comprarTiqueteMultiple() {
        System.out.println("\n=== Compra de Tiquete Múltiple (Multi-Evento) ===");
        System.out.println("Podrás comprar varios eventos con descuento según la cantidad.");
        System.out.println("Escribe 'fin' cuando termines de agregar eventos.\n");

        HashMap<Evento, String> mapaEventos = new HashMap<>();

        while (true) {
            System.out.print("Nombre del evento (o 'fin' para terminar): ");
            String nombre = scanner.nextLine().trim();
            if (nombre.equalsIgnoreCase("fin")) break;

            Evento evento = sistema.getEventos().stream()
                    .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                    .findFirst().orElse(null);

            if (evento == null) {
                System.out.println("Evento no encontrado. Intenta nuevamente.");
                continue;
            }

            System.out.print("Nombre de la localidad para '" + evento.getNombre() + "': ");
            String nombreLocalidad = scanner.nextLine().trim();

            Localidad localidad = evento.getLocalidadPorNombre(nombreLocalidad);
            if (localidad == null) {
                System.out.println("Localidad no encontrada. Intenta nuevamente.");
                continue;
            }

            mapaEventos.put(evento, nombreLocalidad);
            System.out.println("Evento agregado: " + evento.getNombre() + " - " + nombreLocalidad);
        }

        if (mapaEventos.isEmpty()) {
            System.out.println("No se seleccionaron eventos.");
            return;
        }

        try {
            sistema.comprarTiquetesMultiplesMultiEvento(mapaEventos);
            System.out.println("\n Compra múltiple realizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al comprar: " + e.getMessage());
        }
    }


    private void solicitarReembolso() {
        System.out.print("Ingrese el ID del tiquete: ");
        int id = leerEntero();
        System.out.print("Ingrese la razón del reembolso: ");
        String razon = scanner.nextLine();
        try {
            sistema.solicitarReembolso(id, razon);
            System.out.println("Solicitud enviada al administrador.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void publicarOferta() {
        System.out.print("Ingrese el ID del tiquete o tiquete múltiple: ");
        int id = leerEntero();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = leerDouble();

        Tiquete t = sistema.getTiquetes().get(id);

        if (t == null) {
            System.out.println("No se encontró ese tiquete.");
            return;
        }
        sistema.publicarOferta(t, desc, precio);
    }

    private void verOfertasMarketplace() {
        System.out.println("=== Ofertas Disponibles ===");
        for (Oferta o : sistema.verOfertas()) {
            System.out.println(o);
        }
    }

    private void hacerContraOferta() {
        System.out.print("Ingrese ID del tiquete ofertado: ");
        int id = leerEntero();
        Oferta oferta = sistema.verOfertas().stream()
                .filter(o -> o.getTiquete() != null && o.getTiquete().getId() == id)
                .findFirst().orElse(null);
        if (oferta == null) {
            System.out.println("Oferta no encontrada.");
            return;
        }
        System.out.print("Ingrese su nuevo precio: ");
        double precio = leerDouble();
        boolean usarSaldo = false;
        System.out.print("¿Desea usar su saldo virtual para esta operación? (si/no): ");
        String resp = scanner.nextLine().trim();
        if (resp.equalsIgnoreCase("si")) {
            usarSaldo = true;
        }
        sistema.hacerContraOferta(oferta, precio, usarSaldo);
        System.out.println("Contraoferta enviada.");
    }

    private void gestionarMisContraOfertas() {
        for (Oferta oferta : sistema.verOfertas()) {
            if (oferta.getVendedor().equals(sistema.getUsuarioActual())) {
                for (ContraOferta c : oferta.getContraOfertas()) {
                    System.out.println("Contraoferta: " + c);
                    System.out.print("¿Aceptar? (S/N): ");
                    String resp = scanner.nextLine().toUpperCase();
                    if (resp.equals("S")) sistema.aceptarContraOferta(c);
                    else sistema.rechazarContraOferta(c);
                }
            }
        }
    }

    private void eliminarMiOferta() {
        System.out.print("Ingrese el ID del tiquete que desea retirar: ");
        int id = leerEntero();
        Oferta oferta = sistema.verOfertas().stream()
                .filter(o -> (o.getTiquete() != null && o.getTiquete().getId() == id))
                .findFirst().orElse(null);

        if (oferta == null) {
            System.out.println("Oferta no encontrada.");
            return;
        }
        sistema.eliminarOferta(oferta);
        System.out.println("Oferta eliminada.");
    }

    // === MÉTODOS AUXILIARES ===

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
