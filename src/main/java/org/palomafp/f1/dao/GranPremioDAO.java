package org.palomafp.f1.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.GranPremio;
import org.palomafp.f1.model.Participacion;
import org.palomafp.f1.model.Piloto;

public class GranPremioDAO {

	private PilotosDAO pilotosDAO;

	public GranPremioDAO(String url, String usuario, String contrasena) {
		// Constructor adaptado para modo mock - no se conecta a BD
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los grandes premios (DATOS MOCK)
	 */
	public ArrayList<GranPremio> obtenerTodosGrandesPremios() {
		ArrayList<GranPremio> granPremios = new ArrayList<>();

		// Bahrain
		ArrayList<Participacion> part1 = new ArrayList<>();
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
		part1.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));
		GranPremio bahrain = new GranPremio(1, "Bahrain Grand Prix", "Bahréin", 5.412, 57, "01:31:12", 
			parseDate("2004-01-01"), part1, null);
		granPremios.add(bahrain);

		// Saudi Arabia
		ArrayList<Participacion> part2 = new ArrayList<>();
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
		part2.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));
		GranPremio saudiArabia = new GranPremio(2, "Saudi Arabian Grand Prix", "Arabia Saudita", 6.174, 50, "01:55:23", 
			parseDate("2021-01-01"), part2, null);
		granPremios.add(saudiArabia);

		// Australia
		ArrayList<Participacion> part3 = new ArrayList<>();
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
		part3.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));
		GranPremio australia = new GranPremio(3, "Australian Grand Prix", "Australia", 5.278, 58, "02:29:45", 
			parseDate("1985-01-01"), part3, null);
		granPremios.add(australia);

		// Japón
		ArrayList<Participacion> part4 = new ArrayList<>();
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "02:00:12", 1));
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "02:00:45", 2));
		part4.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:01:20", 3));
		GranPremio japon = new GranPremio(4, "Japanese Grand Prix", "Japón", 5.807, 53, "02:02:15", 
			parseDate("1976-01-01"), part4, null);
		granPremios.add(japon);

		// Mónaco
		ArrayList<Participacion> part5 = new ArrayList<>();
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:58:32", 1));
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:59:10", 2));
		part5.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "01:59:45", 3));
		GranPremio monaco = new GranPremio(5, "Monaco Grand Prix", "Mónaco", 3.337, 78, "01:55:23", 
			parseDate("1950-01-01"), part5, null);
		granPremios.add(monaco);

		return granPremios;
	}

	/**
	 * Obtiene un gran premio por su ID (DATOS MOCK)
	 */
	public GranPremio obtenerGranPremioPorId(int idGp) {
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getIdGp() == idGp) {
				return gp;
			}
		}
		return null;
	}

	/**
	 * Obtiene un gran premio por su nombre (DATOS MOCK)
	 */
	public GranPremio obtenerGranPremioPorNombre(String nombre) {
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getNombre().equalsIgnoreCase(nombre)) {
				return gp;
			}
		}
		return null;
	}

	/**
	 * Obtiene GPs por ubicación (DATOS MOCK)
	 */
	public ArrayList<GranPremio> obtenerGrandesPremiosPorUbicacion(String ubicacion) {
		ArrayList<GranPremio> resultado = new ArrayList<>();
		ArrayList<GranPremio> todos = obtenerTodosGrandesPremios();
		for (GranPremio gp : todos) {
			if (gp.getUbicacion().equalsIgnoreCase(ubicacion)) {
				resultado.add(gp);
			}
		}
		return resultado;
	}

	/**
	 * Método auxiliar para parsear fechas
	 */
	private Date parseDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (Exception e) {
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
