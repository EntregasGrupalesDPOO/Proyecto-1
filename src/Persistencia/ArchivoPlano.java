package Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoPlano {

	public void escribir(ArrayList<String> datos, String nombreArchivo) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(nombreArchivo));
			for(String dato : datos) {
				bw.write(dato);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> leer(String nombreArchivo){
		ArrayList<String> datos = new ArrayList<String>();
		BufferedReader br = null;
		try {
			 br = new BufferedReader(new FileReader(nombreArchivo));
			String linea;
			while((linea=br.readLine()) != null) {
				datos.add(linea);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return datos;
	}
}
