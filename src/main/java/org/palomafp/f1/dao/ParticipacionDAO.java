package org.palomafp.f1.dao;

import java.util.ArrayList;

import org.palomafp.f1.model.Participacion;

public class ParticipacionDAO {

	private PilotosDAO pilotosDAO;
	private GranPremioDAO granPremioDAO;

	public ParticipacionDAO(String url, String usuario, String contrasena) {
		// Constructor adaptado para modo mock - no se conecta a BD
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		this.granPremioDAO = new GranPremioDAO(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las participaciones (DATOS MOCK)
	 */
	public ArrayList<Participacion> obtenerTodasParticipaciones() {
		ArrayList<Participacion> participaciones = new ArrayList<>();

		// Bahrain - Participaciones
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));

		// Saudi Arabia - Participaciones
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));

		// Australia - Participaciones
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));

		return participaciones;
	}

	/**
	 * Obtiene participaciones de un piloto (DATOS MOCK)
	 */
	public ArrayList<Participacion> obtenerParticipacionesPiloto(int numeroPiloto) {
		ArrayList<Participacion> resultado = new ArrayList<>();
		ArrayList<Participacion> todas = obtenerTodasParticipaciones();

		for (Participacion p : todas) {
			if (p.getPiloto().getNumero() == numeroPiloto) {
				resultado.add(p);
			}
		}

		return resultado;
	}

	/**
	 * Obtiene participaciones de un gran premio (DATOS MOCK)
	 */
	public ArrayList<Participacion> obtenerParticipacionesGranPremio(int idGp) {
		ArrayList<Participacion> participaciones = new ArrayList<>();

		// Retornar participaciones específicas del GP
		if (idGp == 1) { // Bahrain
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));
		} else if (idGp == 2) { // Saudi Arabia
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));
		} else if (idGp == 3) { // Australia
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));
		}

		return participaciones;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
	}
}
