package org.palomafp.f1.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Piloto;

public class PilotosDAO {

	public PilotosDAO(String url, String usuario, String contrasena) {
		// Constructor adaptado para modo mock - no se conecta a BD
	}

	/**
	 * Obtiene todos los pilotos (DATOS MOCK)
	 */
	public ArrayList<Piloto> obtenerTodosPilotos() {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		
		pilotos.add(crearPiloto(1, "Lando", "Norris", "Británica", "1999-11-13", 42, 11, 1, 15));
		pilotos.add(crearPiloto(81, "Oscar", "Piastri", "Australiana", "2001-04-06", 28, 8, 0, 6));
		pilotos.add(crearPiloto(63, "George", "Russell", "Británica", "1998-02-15", 18, 4, 0, 5));
		pilotos.add(crearPiloto(12, "Kimi", "Antonelli", "Italiana", "2006-08-25", 2, 0, 0, 1));
		pilotos.add(crearPiloto(16, "Charles", "Leclerc", "Monegasca", "1997-10-16", 39, 8, 0, 26));
		pilotos.add(crearPiloto(44, "Lewis", "Hamilton", "Británica", "1985-01-07", 201, 105, 7, 104));
		pilotos.add(crearPiloto(33, "Max", "Verstappen", "Neerlandesa", "1997-09-30", 115, 67, 4, 44));
		pilotos.add(crearPiloto(6, "Isack", "Hadjar", "Francesa", "2004-09-28", 0, 0, 0, 0));
		pilotos.add(crearPiloto(14, "Fernando", "Alonso", "Española", "1981-07-29", 106, 32, 2, 22));
		pilotos.add(crearPiloto(18, "Lance", "Stroll", "Canadiense", "1998-10-29", 3, 0, 0, 1));
		
		return pilotos;
	}

	/**
	 * Obtiene un piloto por su número (DATOS MOCK)
	 */
	public Piloto obtenerPilotoPorNumero(int numero) {
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getNumero() == numero) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Obtiene pilotos por nacionalidad (DATOS MOCK)
	 */
	public ArrayList<Piloto> obtenerPilotosPorNacionalidad(String nacionalidad) {
		ArrayList<Piloto> resultado = new ArrayList<>();
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getNacionalidad().equalsIgnoreCase(nacionalidad)) {
				resultado.add(p);
			}
		}
		return resultado;
	}

	/**
	 * Obtiene pilotos por apellido (DATOS MOCK)
	 */
	public ArrayList<Piloto> obtenerPilotosPorApellido(String apellido) {
		ArrayList<Piloto> resultado = new ArrayList<>();
		ArrayList<Piloto> todos = obtenerTodosPilotos();
		for (Piloto p : todos) {
			if (p.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
				resultado.add(p);
			}
		}
		return resultado;
	}

	/**
	 * Método auxiliar para crear pilotos
	 */
	private Piloto crearPiloto(int numero, String nombre, String apellido, String nacionalidad,
			String fechaNacimiento, int podios, int victorias, int campeonatos, int poles) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha = sdf.parse(fechaNacimiento);
			return new Piloto(numero, nombre, apellido, nacionalidad, fecha, podios, victorias, campeonatos, poles, null);
		} catch (Exception e) {
			System.err.println("Error al crear piloto: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
	}
}
