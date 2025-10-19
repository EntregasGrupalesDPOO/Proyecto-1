package Persistencia;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileWriter;

public class ArchivoSerializable {
	
	public void escribir(Object obj, String nombreArchivo) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
			oos.writeObject(obj);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Object leer(String nombreArchivo) {
	    ObjectInputStream ois = null;
	    try {
	        ois = new ObjectInputStream(new FileInputStream(nombreArchivo)); // ← on utilise bien le paramètre
	        Object obj = ois.readObject();
	        ois.close();  
	        return obj;   
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + nombreArchivo);
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        System.err.println("Error: clase no encontrada al leer el archivo: " + nombreArchivo);
	        e.printStackTrace();
	    }
	    return null;
	}

}
