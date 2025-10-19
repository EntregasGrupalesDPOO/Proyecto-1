package Exepciones;
import logica.Usuario;

public class SaldoInsuficienteException extends Exception {
	
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
    public SaldoInsuficienteException(Usuario usuario) {
        super("El usuario "+usuario.getLogin()+" tiene solo "+ usuario.getSaldoVirtual() +" pesos en su saldo, es insuficiente");
    }
}
