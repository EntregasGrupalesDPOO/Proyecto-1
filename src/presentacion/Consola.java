package presentacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import Exepciones.VenueNoDisponibleException;
import logica.*;

public class Consola {

    private BoletasMaster sistema;

    public Consola() {
        this.sistema = new BoletasMaster();
        //this.inicializarDatos();
        this.leerDatos();
        this.simularEscenario();
    }

    private void leerDatos() {
        System.out.println("LEYENDO DATOS EXISTENTES...");

        sistema.leerUsuarios();
        sistema.leerEventos();
        sistema.leerTiquetes();
        sistema.leerAdministrador();
        sistema.leerVenues();


        if (sistema.getClientes() == null) {
            sistema.setClientes(new HashMap<>());
        }
        if (sistema.getOrganizadores() == null) {
            sistema.setOrganizadores(new HashMap<>());
        }
        if (sistema.getUsuarios() != null) {
            for (Usuario u : sistema.getUsuarios().values()) {
                if (u instanceof Cliente) {
                    sistema.getClientes().put(u.getLogin(), (Cliente) u);
                } else if (u instanceof Organizador) {
                    sistema.getOrganizadores().put(u.getLogin(), (Organizador) u);
                }
            }
        }
        sistema.fijarComisionPorTipoEvento (0.15,0.2,0.16,0.18);

        System.out.println("Usuarios cargados: " + sistema.getUsuarios().size());
        System.out.println("Clientes reconstruidos: " + sistema.getClientes().keySet());
        System.out.println("Organizadores reconstruidos: " + sistema.getOrganizadores().keySet());
        System.out.println("Eventos cargados: " + sistema.getEventos().size());
        System.out.println("Tiquetes cargados: " + sistema.getTiquetes().size());
        System.out.println("Venues cargados: " + sistema.getVenues().size());
        System.out.println("Administrador cargado: " + (sistema.getAdministrador() != null));
        System.out.println("==============================\n");
    }

    
    private void inicializarDatos() {
        System.out.println(" INICIALIZANDO SISTEMA ");

        // Crear roles
        sistema.agregarAdministrador("admin", "1234");
        sistema.agregarOrganizador("juan", "abcd");
        sistema.agregarCliente("maria", "pass");
        sistema.agregarCliente("carlos", "qwerty");

        // Crear venue
        Venue venue = new Venue(500, "Coliseo Nacional", "Bogotá");
        sistema.agregarVenue(venue);
        
        
        // Crear localidades
        HashMap<Integer, Localidad> localidades = new HashMap<>();
        localidades.put(1, new Localidad("VIP", 200000, 50, true, venue));
        localidades.put(2, new Localidad("General", 80000, 450, false, venue));

        // Crear evento de ejemplo
        Organizador juan = sistema.getOrganizadores().get("juan");
        Evento concierto;
		try {
			concierto = juan.crearEvento(
			        "Rock en Vivo",
			        LocalDate.of(2025, 12, 10),
			        LocalTime.of(19, 30),
			        venue,
			        localidades,
			        "MUSICAL");
			sistema.agregarEvento(concierto);
	        sistema.fijarComisionPorTipoEvento (0.15,0.2,0.16,0.18);

	        System.out.println("Evento creado: " + concierto.getNombre());
	        System.out.println("Localidades: " + concierto.getLocalidades().keySet());
	        System.out.println("==============================\n");
		} catch (VenueNoDisponibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }

    private void simularEscenario() {
        try {
            // LOGIN CLIENTE MARIA 
            System.out.println("→ Login como cliente: Maria");
            sistema.loginCliente("maria", "pass");

            // Añadir saldo a Maria
            sistema.getUsuarioActual().setSaldoVirtual(500000);
            System.out.println("Saldo inicial de Maria: " + sistema.getUsuarioActual().getSaldoVirtual());

            // Comprar tiquetes
            Evento evento = sistema.getEventos().get(0);
            System.out.println("→ Comprando 2 tiquetes VIP para " + evento.getNombre());
            sistema.comprarTiquetes(2, evento, 1);

            System.out.println("Tiquetes de Maria:");
            sistema.getUsuarioActual().getTiquetes().forEach((e, lista) -> {
                System.out.println("  Evento: " + e.getNombre() + " - Cantidad: " + lista.size());
            });

            // LOGIN CLIENTE CARLOS 
            System.out.println("\n→ Login como cliente: Carlos");
            sistema.loginCliente("carlos", "qwerty");
            sistema.getUsuarioActual().setSaldoVirtual(100000);

            // Intentar comprar más tiquetes que los permitidos
            System.out.println("→ Intentando comprar 15 tiquetes (debería lanzar excepción)");
            try {
                sistema.comprarTiquetes(15, evento, 2);
            } catch (Exception ex) {
                System.out.println("Excepción capturada: " + ex.getMessage());
            }

            // LOGIN ORGANIZADOR JUAN 
            System.out.println("\n→ Login como organizador: Juan");
            sistema.loginOrganizador("juan", "abcd");

            // Crear un nuevo evento
            System.out.println("→ Creando evento 'Pop Fest'");
            Venue v = sistema.getVenues().get(0);
            sistema.agendarEvento("Pop Fest", "Festival de música pop", LocalDate.of(2026, 1, 15),
                    LocalTime.of(18, 0), v, "Festival");

            // Ver ganancias globales
            Organizador o = (Organizador) sistema.getUsuarioActual();
            double ganancias = o.obtenerGananciasGlobales();
            System.out.println("Ganancias globales del organizador: " + ganancias);

            // --- LOGIN ADMINISTRADOR ---
            System.out.println("\n→ Login como administrador: admin");
            sistema.loginAdministrador("admin", "1234");

            System.out.println("→ Viendo solicitudes pendientes (si existen)");
            sistema.verSolicitudesPendientes();
            
            /*Escritura de los datos
          
            sistema.escribirUsuarios();
            sistema.escribirEventos();
            sistema.escribirTiquetes();
            sistema.escribirAdministrador();
            sistema.escribirVenues();
			*/
            System.out.println("\n=== FIN DE LA SIMULACIÓN ===");

        } catch (Exception e) {
            System.out.println("Error durante la simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Consola();
    }
}
