package consola;

import java.util.Scanner;
import logica.BoletasMaster;
import logica.Cliente;
import logica.Organizador;
import logica.Administrador;
import Exepciones.UsuarioNoEncontradoException;

public class ConsolaPrincipal {

    private BoletasMaster sistema;
    private Scanner scanner;

    public ConsolaPrincipal() {
        this.sistema = new BoletasMaster();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== Bienvenido a BoletasMaster ===");
        boolean salir = false;

        while (!salir) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("→ ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    iniciarSesion();
                    break;
                case "2":
                    registrarUsuario();
                    break;
                case "3":
                    salir = true;
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    private void iniciarSesion() {
        System.out.println("\nSeleccione el tipo de usuario:");
        System.out.println("1. Cliente");
        System.out.println("2. Organizador");
        System.out.println("3. Administrador");
        System.out.print("→ ");

        String tipo = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        try {
            switch (tipo) {
                case "1":
                    sistema.loginCliente(login, contrasena);
                    System.out.println("Sesión iniciada como Cliente.");
                    new ConsolaCliente(sistema).ejecutarMenuCliente();
                    break;
                case "2":
                    sistema.loginOrganizador(login, contrasena);
                    System.out.println("Sesión iniciada como Organizador.");
                    //new ConsolaOrganizador(sistema, scanner).mostrarMenuOrganizador();
                    break;
                case "3":
                    sistema.loginAdministrador(login, contrasena);
                    System.out.println("Sesión iniciada como Administrador.");
                    //new ConsolaAdministrador(sistema, scanner).mostrarMenuAdministrador();
                    break;
                default:
                    System.out.println("Tipo de usuario inválido.");
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Usuario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void registrarUsuario() {
        System.out.println("\nSeleccione el tipo de usuario a registrar:");
        System.out.println("1. Cliente");
        System.out.println("2. Organizador");
        System.out.print("→ ");

        String tipo = scanner.nextLine();
        System.out.print("Elija su login: ");
        String login = scanner.nextLine();
        System.out.print("Elija su contraseña: ");
        String contrasena = scanner.nextLine();

        switch (tipo) {
            case "1":
                sistema.agregarCliente(login, contrasena);
                System.out.println("✅ Cliente registrado con éxito.");
                break;
            case "2":
                sistema.agregarOrganizador(login, contrasena);
                System.out.println("✅ Organizador registrado con éxito.");
                break;
            default:
                System.out.println("❌ Tipo inválido. Solo puede registrar Cliente u Organizador.");
        }

        // Persistir automáticamente
        sistema.escribirCliente();
    }

    public static void main(String[] args) {
        ConsolaPrincipal consola = new ConsolaPrincipal();
        consola.iniciar();
    }
}
