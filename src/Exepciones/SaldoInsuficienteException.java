package Exepciones;
import logica.Cliente;

public class SaldoInsuficienteException extends Exception {
	
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
    public SaldoInsuficienteException(Cliente usuario) {
        super("El usuario "+usuario.getLogin()+" tiene solo "+ usuario.getSaldoVirtual() +" pesos en su saldo, es insuficiente");
    }
}
