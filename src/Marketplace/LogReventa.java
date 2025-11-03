package Marketplace;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogReventa implements Serializable {
    private List<String> eventos;

    public LogReventa() {
        this.eventos = new ArrayList<>();
    }

    public void registrarEvento(String descripcion) {
        eventos.add(LocalDateTime.now() + " - " + descripcion);
    }

    public List<String> getEventos() {
        return eventos;
    }
    
    public void mostrarLog() {
        for (String evento : eventos) {
            System.out.println(evento);
        }
    }
}
